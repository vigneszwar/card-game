package cardgame.schema;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Card {
    private Suit suit;
    private Number number;
    @Override
    public String toString() {
        if(number.getValue() > 1 && number.getValue() < 11)
            return suit.name() + " " + number.getValue();
        else {
            return suit.name() + " " + number.name();
        }
    }
}
