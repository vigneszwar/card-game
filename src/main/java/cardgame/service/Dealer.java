package cardgame.service;

import cardgame.schema.Player;
import cardgame.schema.Rank;
import cardgame.schema.Table;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;


public interface Dealer {
    public Table getTable();
    public List<Rank> getRanks();
    public void assignTable(Table table);
    public void distributeCardForAllPlayers();
    public void distributeCardForShortListedPlayers();
    public void flushAllPlayerCards();
    public void flushShortListedPlayerCards();
    public void assignRanksForEachPlayer();
    public void shortListPlayers();
    public void shortListPlayersAgain();

    public boolean isDeckEmpty();

}
