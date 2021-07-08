package cardgame.schemas;

import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Getter
@ToString
public class Deck {
    List<Card> cards;

    public Deck() {
        this.cards = Arrays.stream(Suit.values()).flatMap(suit -> Arrays.stream(Number.values()).map(number -> new Card(suit, number))).collect(Collectors.toList());
    }

    public Card drawCard() {
        return cards.remove(new Random().nextInt(cards.size()));
    }

}
