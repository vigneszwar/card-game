package cardgame.service;

import cardgame.schema.Player;
import cardgame.schema.Rank;

import java.util.List;

public interface WinnerStrategy {
    Rank getBestCombination(Player player);

    List<Rank> getRanks(List<Player> players);

    List<Player> getWinners(List<Rank> ranks);
}
