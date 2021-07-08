package cardgame.services.backendervices;

import cardgame.schemas.Player;
import cardgame.schemas.State;
import cardgame.schemas.User;
import cardgame.schemas.logical.UserView;
import cardgame.services.dealerservices.Dealer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserViewService {
    Logger logger = LoggerFactory.getLogger(UserViewService.class);
    @Autowired
    UserDealerMap userDealerMap;
    @Autowired
    UserService userService;

   public UserView buildUserView(long userId) {
       Dealer dealer = userDealerMap.getDealer(userId);
       logger.info("Dealer for User " + userId + " is "+dealer);
       List<Player> playerList = null;
       UserView userView = null;
       User user = userService.getUser(userId);
       if(dealer != null) {
           playerList = dealer.getPlayers();
           Player currentPlayer = playerList.stream()
                   .filter( player ->
                           player.getUser().getId() == userId)
                   .findFirst()
                   .orElse(null);
           List<Player> opponents = playerList.stream().filter( player -> player.getUser().getId() != userId).collect(Collectors.toList());
           List<Player> winners = new ArrayList<>();
           Player winner = dealer.getDealerHelper().getTable().getWinner();
           if(winner != null) {
               winners.add(winner);
           } else {
               winners.addAll(dealer.getDealerHelper().getTable().getShortlistedPlayers().getLast());
           }
           userView = UserView.builder()
                   .user(user)
                   .cards(currentPlayer.getCards())
                   .allShortListedPlayers(dealer.getDealerHelper().getTable().getShortlistedPlayers())
                   .userRank(currentPlayer.getRank())
                   .opponents(opponents)
                   .matchStatus(dealer.getDealerHelper().getTable().getGameStatus().toString())
                   .winners(winners)
                   .build();
       } else {
           userView = UserView.builder().user(user).matchStatus(State.PENDING.toString()).build();
       }
       return userView;

   }
}
