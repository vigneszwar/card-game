package cardgame.services.dealerservices;

import cardgame.schemas.logical.Table;


public interface DealerHelper {
    public Table getTable();
    public void assignTable(Table table);
    public void distributeCardForAllPlayers();
    public void distributeCardForShortListedPlayers();
    public void flushAllPlayerCards();
    public void assignRanksForEachPlayer();
    public void shortListPlayers();
    public void shortListPlayersAgain();

    public boolean isDeckEmpty();

}
