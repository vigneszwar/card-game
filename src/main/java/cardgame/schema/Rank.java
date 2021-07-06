package cardgame.schema;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Rank {
    RankValue rankValueEnum;
    private Card[] cards;
    private Player player;

    public void setCards(Card... cards) {
        this.cards = cards;
    }
}
