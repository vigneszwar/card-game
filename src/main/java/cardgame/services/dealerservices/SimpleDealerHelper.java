package cardgame.services.dealerservices;

import cardgame.schemas.Player;
import cardgame.schemas.logical.Table;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

@Getter
public class SimpleDealerHelper implements DealerHelper {

    Logger logger = LoggerFactory.getLogger(SimpleDealerHelper.class);

    private Table table;
    private WinnerStrategy winnerStrategy = new SimpleWinnerStrategy();

    public SimpleDealerHelper() {
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
        table.getShortlistedPlayers().getLast().stream().forEach( player -> {
            player.addCard(table.getDeck().drawCard());
        });
        logger.debug(table.getShortlistedPlayers().getLast().toString());
    }

    @Override
    public void flushAllPlayerCards() {
        logger.debug("Flushing Cards for all players");
        table.getPlayers().stream().forEach( player -> player.getCards().clear());
    }



    @Override
    public void assignRanksForEachPlayer() {
        logger.debug("Finding Best Combination for each player");
        winnerStrategy.calculateRanks(table.getPlayers());
    }

    public void shortListPlayers() {
        table.addShortlistedPlayers(winnerStrategy.getWinners(table.getPlayers()));
    }


    @Override
    public void shortListPlayersAgain() {
        logger.debug("Playing tie breaker round for short listed Players");
        Collections.sort(table.getShortlistedPlayers().getLast(),
                Comparator.comparing(player -> player.getCards().getLast().getNumber())); // No Ranks, just sort based on card value.
        List<Player> newShortListedPlayers = table.getShortlistedPlayers().getLast().stream().filter( player -> !table.getShortlistedPlayers().getLast()
                .get(table.getShortlistedPlayers().getLast().size()-1)
                .getCards().getLast().getNumber()
                .equals(player.getCards().getLast().getNumber())).collect(Collectors.toCollection(LinkedList::new));
        table.getShortlistedPlayers().add(newShortListedPlayers);
        logger.debug("Resulting Players " + table.getShortlistedPlayers().getLast());

    }

    @Override
    public boolean isDeckEmpty() {
        return table.getDeck().getCards().size() == 0;
    }
}
