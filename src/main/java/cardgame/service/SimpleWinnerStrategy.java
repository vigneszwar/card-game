package cardgame.service;

import cardgame.schema.Card;
import cardgame.schema.Player;
import cardgame.schema.Rank;
import cardgame.schema.RankValue;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SimpleWinnerStrategy implements WinnerStrategy{
    Logger logger = LoggerFactory.getLogger(SimpleWinnerStrategy.class);
    @Autowired
    Combinations combinations;


    @Override
    public Rank getBestCombination(Player player) {

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
        rank.setPlayer(player);
        logger.debug("Player Rank" + rank);
        return rank;
    }

    public static int compareRankByLevelThenTopCard(Rank a, Rank b) {
        int compareValue = b.getRankValueEnum().compareTo(a.getRankValueEnum());
        if(compareValue != 0) {
            return compareValue;
        } else if(b.getRankValueEnum().equals(RankValue.TRAIL) || b.getRankValueEnum().equals(RankValue.SEQUENCE) || b.getRankValueEnum().equals(RankValue.PAIR)) {
            return b.getCards()[0].getNumber().compareTo(a.getCards()[0].getNumber());
        } else { //top card
            return Integer.compare(b.getCards()[0].getNumber().getValue(), a.getCards()[0].getNumber().getValue());
        }
    }

    /**
     * Returns a list with one player who is the winner
     * if there is tie, then all tied players are returned.
     */
    @Override
    public List<Rank> getRanks(List<Player> players) {
        List<Rank> ranks = new ArrayList<>();
        for(Player player: players) {
            logger.debug("getting Best Combination For Player " + player.getUser().getName() + player.getCards());
            Rank rank = getBestCombination(player);
            logger.debug("Rank for Player " + player.getUser().getName() + " " + rank);
            ranks.add(rank);
            logger.debug("Total Ranks " + ranks);
        }
        logger.debug(ranks.toString());
        Collections.sort(ranks, SimpleWinnerStrategy::compareRankByLevelThenTopCard);
        logger.debug(String.valueOf(ranks));
        return ranks;
    }

    @Override
    public List<Player> getWinners(List<Rank> ranks) {
        Rank highestRankValue = ranks.get(0);
        return ranks.stream().filter(rank -> rank.getRankValueEnum().equals(highestRankValue.getRankValueEnum())).map(rank -> rank.getPlayer()).collect(Collectors.toList());
    }



}
