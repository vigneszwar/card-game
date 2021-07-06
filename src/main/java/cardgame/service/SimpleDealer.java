package cardgame.service;

import cardgame.schema.Player;
import cardgame.schema.Rank;
import cardgame.schema.Table;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Scope("prototype")
@Getter
public class SimpleDealer implements Dealer{

    Logger logger = LoggerFactory.getLogger(SimpleDealer.class);

    private Table table;
    private List<Rank> ranks;
    @Autowired
    private WinnerStrategy winnerStrategy;


    public SimpleDealer() {
    }


    @Override
    public void assignTable(Table table) {
        logger.debug("Assigning Table" + table);
        this.table = table;
    }

    @Override
    public void distributeCardForAllPlayers() {
        logger.debug("Distributing Card For All Players");

        table.getPlayers().stream().forEach( player -> {
            for(int i = 0; i < player.getCardLimit(); i++)
                player.addCard(table.getDeck().drawCard());
        });
        logger.debug(table.getPlayers().toString());
    }

    @Override
    public void distributeCardForShortListedPlayers() {
        logger.debug("Distributing Card For Shortlisted Players");
        table.getShortlistedPlayers().stream().forEach( player -> {
            player.addCard(table.getDeck().drawCard());
        });
        logger.debug(table.getShortlistedPlayers().toString());
    }

    @Override
    public void flushAllPlayerCards() {
        logger.debug("Flushing Cards for all players");
        table.getPlayers().stream().forEach( player -> player.getCards().clear());
    }

    @Override
    public void flushShortListedPlayerCards(){
        logger.debug("Flushing Cards for Shortlisted players");
        table.getShortlistedPlayers().stream().forEach( player -> player.getCards().clear());
    }

    @Override
    public void assignRanksForEachPlayer() {
        logger.debug("Finding Best Combination for each player");
        ranks = winnerStrategy.getRanks(table.getPlayers());
    }

    public void shortListPlayers() {
        table.setShortlistedPlayers(winnerStrategy.getWinners(ranks));
    }


    @Override
    public void shortListPlayersAgain() {
        logger.debug("Playing tie breaker round for short listed Players");
        Collections.sort(table.getShortlistedPlayers(), Comparator.comparing(player -> player.getCards().get(0).getNumber()));
        table.getShortlistedPlayers().removeIf( player -> !table.getShortlistedPlayers()
                .get(table.getShortlistedPlayers().size()-1)
                .getCards().get(0).getNumber()
                .equals(player.getCards().get(0).getNumber()));
        logger.debug("Resulting Players " + table.getShortlistedPlayers());

    }

    @Override
    public boolean isDeckEmpty() {
        return table.getDeck().getCards().size() == 0;
    }
}
