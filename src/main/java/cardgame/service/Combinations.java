package cardgame.service;

import cardgame.schema.Card;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Combinations {
    Logger logger = LoggerFactory.getLogger(Combinations.class);
    public boolean isTrail(List<Card> cards) {
        boolean isTrail = true;
        for(int i = 1; i < cards.size(); i++) {
            Card curr = cards.get(i);
            Card prev = cards.get(i-1);
            if(curr.getNumber() != prev.getNumber()) {
                isTrail = false;
                break;
            }
        }
        logger.debug("isTrail " + isTrail);
        return isTrail;
    }

    public boolean isSequence(List<Card> cards) {
        boolean isSeq = true;
        Collections.sort(cards, (a, b) -> Integer.compare(a.getNumber().getValue(), b.getNumber().getValue()));
        Iterator<Card> it = cards.iterator();
        int seqNum = it.next().getNumber().getValue();

        while(it.hasNext()) {
            if(seqNum+1 != it.next().getNumber().getValue()) {
                isSeq = false;
                break;
            }
            seqNum ++;
        }
        logger.debug("isSeq " + isSeq);
        return isSeq;
    }

    public Pair<Boolean, Card> getPair(List<Card> cards) {
        boolean isPair = false;
        Card pairCard = null;
        Iterator<Card> it = cards.iterator();
        HashSet<Integer> mem = new HashSet<>();
        int value = it.next().getNumber().getValue();
        logger.debug(" first card value " + value);
        mem.add(value);
        while(it.hasNext()) {
            Card card = it.next();
            logger.debug(" next card value " + card.getNumber().getValue());
            if(mem.contains(card.getNumber().getValue())) {
                isPair = true;
                pairCard = card;
                break;
            }
            mem.add(card.getNumber().getValue());
        }
        logger.debug("isPair " + isPair);

        return new Pair<>(isPair, pairCard);
    }

    public Card getTopCard(List<Card> cards) {
        Iterator<Card> it = cards.iterator();
        Card topCard = it.next();
        while(it.hasNext()) {
            Card card = it.next();
            if(card.getNumber().getValue() > topCard.getNumber().getValue()) {
                topCard = card;
            }
        }
        logger.debug("topCard " + topCard);
        return topCard;
    }
}
