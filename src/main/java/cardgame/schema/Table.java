package cardgame.schema;
;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


@NoArgsConstructor
@Getter
@Setter
@ToString
public class Table {
    private List<Player> players;
    private HashMap<Long, Player> playerMap;
    private List<Player> shortlistedPlayers;
    private Player Winner;

    public void setShortlistedPlayers(List<Player> shortlistedPlayers) {
        this.shortlistedPlayers = shortlistedPlayers;
    }

    private Deck deck;

    public Table(List<Player> players) {
        this.deck = new Deck();
        this.playerMap = new HashMap<>();
        this.players = players;
        Iterator<Player> it = players.iterator();
        while(it.hasNext()) {
            Player player = it.next();
            playerMap.put(player.getUser().getId(), player);
        }

    }



}
