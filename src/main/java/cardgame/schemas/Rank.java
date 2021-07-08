package cardgame.schemas;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Rank {
    RankValue rankValueEnum;
    private Card[] cards;

    public void setCards(Card... cards) {
        this.cards = cards;
    }
}
