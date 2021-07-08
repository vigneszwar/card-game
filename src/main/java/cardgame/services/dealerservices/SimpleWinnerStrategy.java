package cardgame.services.dealerservices;

import cardgame.schemas.Card;
import cardgame.schemas.Player;
import cardgame.schemas.Rank;
import cardgame.schemas.RankValue;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SimpleWinnerStrategy implements WinnerStrategy{
    Logger logger = LoggerFactory.getLogger(SimpleWinnerStrategy.class);

    Combinations combinations = new Combinations();


    @Override
    public void computeBestCombination(Player player) {

        Rank rank = new Rank();
        if(combinations.isTrail(player.getCards())){ // check for trail
            rank.setRankValueEnum(RankValue.TRAIL);
            rank.setCards(player.getCards().get(0));

        } else if(combinations.isSequence(player.getCards())) {
            rank.setRankValueEnum(RankValue.SEQUENCE);
            rank.setCards(player.getCards().stream().max( (a, b) -> a.getNumber().getValue() - b.getNumber().getValue()).get());
        } else {
            Pair<Boolean, Card> result = combinations.getPair(player.getCards());
            if(result.getKey()) { // if pair exist
                rank.setRankValueEnum(RankValue.PAIR);
                rank.setCards(result.getValue());
            } else {
                Card highCard = combinations.getTopCard(player.getCards());
                rank.setRankValueEnum(RankValue.TOPCARD);
                rank.setCards(highCard);
            }
        }
        player.setRank(rank);
        logger.debug("Player Rank" + rank);
    }

    public static int compareRankByLevelThenTopCard(Player a, Player b) {
        int compareValue = b.getRank().getRankValueEnum().compareTo(a.getRank().getRankValueEnum());
        if(compareValue != 0) {
            return compareValue;
        } else if(b.getRank().getRankValueEnum().equals(RankValue.TRAIL) || b.getRank().getRankValueEnum().equals(RankValue.SEQUENCE) || b.getRank().getRankValueEnum().equals(RankValue.PAIR)) {
            return b.getRank().getCards()[0].getNumber().compareTo(a.getRank().getCards()[0].getNumber());
        } else { //top card
            return Integer.compare(b.getRank().getCards()[0].getNumber().getValue(), a.getRank().getCards()[0].getNumber().getValue());
        }
    }

    /**
     * Returns a list with one player who is the winner
     * if there is tie, then all tied players are returned.
     * @return
     */
    @Override
    public void calculateRanks(List<Player> players) {
        for(Player player: players) {
            logger.debug("getting Best Combination For Player " + player.getUser().getName() + player.getCards());
            computeBestCombination(player);
            logger.debug("Rank for Player " + player.getUser().getName() + " " + player.getRank());
        }
        Collections.sort(players, SimpleWinnerStrategy::compareRankByLevelThenTopCard);
        logger.debug(String.valueOf(players));

    }

    @Override
    public List<Player> getWinners(List<Player> players) {
        Player topPlayer = players.get(0);
        return players.stream().filter(player ->
                player.getRank().getRankValueEnum().equals(topPlayer.getRank().getRankValueEnum())
                && player.getRank().getCards()[0].getNumber().equals(topPlayer.getRank().getCards()[0].getNumber()))
                .collect(Collectors.toList());
    }



}
