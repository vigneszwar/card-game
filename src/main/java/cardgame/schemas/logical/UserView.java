package cardgame.schemas.logical;

import cardgame.schemas.Card;
import cardgame.schemas.Player;
import cardgame.schemas.Rank;
import cardgame.schemas.User;
import lombok.*;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserView {
    private User user;
    private List<Card> cards;
    private Rank userRank;
    private List<List<Player>> allShortListedPlayers;
//    private int position;
    private List<Player> opponents;
    private List<Player> winners;
    private String matchStatus;
}
