package cardgame.schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class Player {
    private User user;
    private List<Card> cards;
    private int cardLimit;

    public Player(User user, int cardLimit) {
        this.user = user;
        this.cardLimit = cardLimit;
        this.cards = new ArrayList<>(cardLimit);
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public void dropCard(Card card) {
        cards.remove(card);
    }
}
