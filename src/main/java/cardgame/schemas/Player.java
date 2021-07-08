package cardgame.schemas;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class Player {
    private User user;
    private LinkedList<Card> cards;
    private int cardLimit;
    private int matchId;
    private Rank rank;

    public Player(User user, int cardLimit) {
        this.user = user;
        this.cardLimit = cardLimit;
        this.cards = new LinkedList<>();
        this.rank = new Rank();
    }

    public Player(User user, int cardLimit, int matchId) {
        this.user = user;
        this.cardLimit = cardLimit;
        this.cards = new LinkedList<>();
        this.matchId = matchId;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public void dropCard(Card card) {
        cards.remove(card);
    }
}
