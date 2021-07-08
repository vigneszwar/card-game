package cardgame.services.dealerservices;

import cardgame.schemas.Player;

import java.util.List;

public interface WinnerStrategy {
    void computeBestCombination(Player player);

    void calculateRanks(List<Player> players);

    List<Player> getWinners(List<Player> players);
}
