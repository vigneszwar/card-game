package cardgame.service;

import cardgame.schema.Player;
import cardgame.schema.Table;
import cardgame.schema.User;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
@Getter
public class GameEventHandler {
    Logger logger = LoggerFactory.getLogger(GameEventHandler.class);
    @Value("${player.card.limit}")
    private int cardLimit;

    @Autowired
    private Dealer dealer;

    private boolean gameOver = false;

    @Resource(name = "playerList")
    private List<Player> players;

    public boolean isGameOver() {
        return dealer.getTable().getShortlistedPlayers().size() == 1 || gameOver;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void addPlayer(User user) {
        logger.info("Adding Player " + user);
        players.add(new Player(user, cardLimit));
    }

    public void removePlayer(User user) {
        players.remove(user);
    }

    public void initializeGame() {
        logger.info("Initializing Game");
        dealer.assignTable(new Table(players));
        dealer.flushAllPlayerCards();
    }

    public void startGame() {
        dealer.distributeCardForAllPlayers();
    }

    public void assignRankForEachPlayerAndShortList() {
        logger.info("Assigning Rank for Each Player and Shortlisting");
        dealer.assignRanksForEachPlayer();
        dealer.shortListPlayers();
    }

    public void playTieBreakerRound() {
        logger.info("Playing Tie Breaker Round");
        logger.debug(dealer.getTable().getShortlistedPlayers().toString());

        if(dealer.isDeckEmpty()) {
            logger.debug("This is impossible. Deck is empty!!! All shortlisted players are Winners");
            gameOver = true;
            return;
        }
        dealer.flushShortListedPlayerCards();
        dealer.distributeCardForShortListedPlayers();

    }
    public void shortlistPlayersAgain() {
        logger.info("Shortlisting Players again");
        dealer.shortListPlayersAgain();
    }


}
