package cardgame.services.dealerservices;

import cardgame.demo.Config;
import cardgame.exceptions.PlayerFullException;
import cardgame.schemas.*;
import cardgame.schemas.logical.Table;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


@Getter
public class Dealer {
    Logger logger = LoggerFactory.getLogger(Dealer.class);

    private DealerHelper dealerHelper = new SimpleDealerHelper();

    private boolean gameOver = false;

    private List<Player> players = new ArrayList<>();

    public Dealer() {
        logger.debug("Player limit is "+Config.PLAYER_LIMIT);
        dealerHelper.assignTable(new Table(Config.PLAYER_LIMIT));
    }

    public boolean isGameOver() {
        return dealerHelper.getTable().getShortlistedPlayers().getLast().size() == 1 || gameOver;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void addPlayer(User user) throws PlayerFullException {
        logger.info("Adding Player " + user);
        logger.debug("Card limit for each Player "+ Config.CARD_LIMIT);
        if(players.size() == dealerHelper.getTable().getPlayerLimit()) {
            throw new PlayerFullException(dealerHelper.getTable().getPlayerLimit());
        }
        players.add(new Player(user, Config.CARD_LIMIT));
    }

    public void removePlayer(User user) {
        players.remove(user);
    }

    public void initializeGame() {
        logger.info("Initializing Game");
        dealerHelper.getTable().setPlayers(players);
        dealerHelper.flushAllPlayerCards();
    }

    public void startGame() {
        dealerHelper.distributeCardForAllPlayers();
    }

    public void assignRankForEachPlayerAndShortList() {
        logger.info("Assigning Rank for Each Player and Shortlisting");
        dealerHelper.assignRanksForEachPlayer();
        dealerHelper.shortListPlayers();
    }

    public void playTieBreakerRound() {
        logger.info("Playing Tie Breaker Round");
        logger.debug(dealerHelper.getTable().getShortlistedPlayers().getLast().toString());

        if(dealerHelper.isDeckEmpty()) {
            logger.debug("This is impossible. Deck is empty!!! All shortlisted players are Winners");
            gameOver = true;
            return;
        }
        //dealerHelper.flushShortListedPlayerCards();
        dealerHelper.distributeCardForShortListedPlayers();

    }
    public void shortlistPlayersAgain() {
        logger.info("Shortlisting Players again");
        dealerHelper.shortListPlayersAgain();
    }

    /**
     * Dealer itself manages the entire game.
     */
    public void playGame() {
        logger.info("Starting Game");
        dealerHelper.getTable().setGameStatus(State.IN_PROGRESS);
        initializeGame();
        startGame();
        assignRankForEachPlayerAndShortList();

        while(!isGameOver()) {
            dealerHelper.getTable().setGameStatus(State.TIE_BREAKER);
            playTieBreakerRound();
            shortlistPlayersAgain();
        }
        List<Player> winners = dealerHelper.getTable().getShortlistedPlayers().getLast();
        if(winners.size() == 1) {
            dealerHelper.getTable().setWinner(winners.get(0));
        }
        dealerHelper.getTable().setGameStatus(State.OVER);
        logger.info("Game Over");
    }

}
