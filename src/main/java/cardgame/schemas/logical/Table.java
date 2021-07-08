package cardgame.schemas.logical;
;
import cardgame.schemas.Deck;
import cardgame.schemas.Player;
import cardgame.schemas.State;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


@NoArgsConstructor
@Getter
@Setter
@ToString
public class Table {
    private List<Player> players;
    private HashMap<Long, Player> playerMap;
    private LinkedList<List<Player>> shortlistedPlayers = new LinkedList<>();
    private Player Winner;
    private int playerLimit;
    private State gameStatus;

    public void addShortlistedPlayers(List<Player> shortlistedPlayers) {
        this.shortlistedPlayers.add(shortlistedPlayers);
    }

    private Deck deck;

    public Table(int playerLimit) {
        this.playerLimit = playerLimit;
        this.deck = new Deck();
        this.gameStatus = State.PENDING;
    }

    public void assignPlayers(List<Player> players) {

        this.playerMap = new HashMap<>();
        this.players = players;
        Iterator<Player> it = players.iterator();
        while(it.hasNext()) {
            Player player = it.next();
            playerMap.put(player.getUser().getId(), player);
        }

    }



}
