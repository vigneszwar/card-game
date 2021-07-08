package cardgame.repositories;

import cardgame.schemas.Card;
import javafx.util.Pair;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class UserCardCombinationsRepository {
    public HashMap<Long, List<Card>> userCardCombMappings;

    public UserCardCombinationsRepository() {
        this.userCardCombMappings = new HashMap<>();
    }

    public void addCombination(long id, Card card) {
        this.userCardCombMappings.computeIfAbsent(id, key -> new ArrayList<>()).add(card);
    }

    public List<Card> getCombination(long id) {
        return this.userCardCombMappings.get(id);
    }
}
