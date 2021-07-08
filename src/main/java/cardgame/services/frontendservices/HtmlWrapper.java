package cardgame.services.frontendservices;

import cardgame.demo.Config;
import cardgame.schemas.Player;
import cardgame.schemas.User;
import cardgame.services.dealerservices.Dealer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

@Component
public class HtmlWrapper {
    Logger logger = LoggerFactory.getLogger(HtmlWrapper.class);

    Dealer dealer;



    @Value("${table.player.limit}")
    private int playerLimit;



    public String startGameAndFetchGameResult() {
        this.dealer = new Dealer();
        if(dealer.getPlayers().size() != playerLimit) {
            logger.debug("Adding players");
            User u1 = new User(1,  "p1");
            User u2 = new User(2, "p2");
            User u3 = new User( 3, "p3");
            User u4 = new User(4, "p4");
            dealer.getDealerHelper().getTable().setPlayerLimit(Config.PLAYER_LIMIT);
            try {
                dealer.addPlayer(u1);
                dealer.addPlayer(u2);
                dealer.addPlayer(u3);
                dealer.addPlayer(u4);
            } catch (Exception e) {
                logger.error("Exception while adding players", e);
            }
        }

        StringBuilder html = new StringBuilder();
        html.append("<h1> Simple Card game </h1>");

        html.append(" <h3> Users added </h3>");
        html.append(" <ul>");
        dealer.getPlayers().stream().forEach(player -> html.append("<li>" + player.getUser().getName() +"</li>"));
        html.append(" </ul>");
        dealer.initializeGame();
        dealer.startGame();
        html.append(" <h3> Cards Assigned For Each Player</h3>");
        html.append(" <ul>");
        Iterator<Player> it = dealer.getDealerHelper().getTable().getPlayers().iterator();
        while(it.hasNext()) {
            Player p = it.next();
            html.append("<li>"+p.getUser().getName() + " " + p.getCards() +"</li>");
        }
        html.append("</ul>");
        dealer.assignRankForEachPlayerAndShortList();
        dealer.getDealerHelper().assignRanksForEachPlayer();
        it =  dealer.getDealerHelper().getTable().getPlayers().iterator();
        html.append("<h3> Best Combination for each Player (In Order)</h3> <ul>");
        while(it.hasNext()) {
            Player player = it.next();
            html.append("<li>" + player.getUser().getName() + " - " + player.getRank().getRankValueEnum().name() + " of  " + player.getRank().getCards()[0].getNumber()+"</li>");
        }
        html.append("</ul>");

        while(!dealer.isGameOver()) {
            html.append("<h3> Tie between these players </h3><ul>");
            Iterator<Player> playerIterator = dealer.getDealerHelper().getTable().getShortlistedPlayers().getLast().iterator();
            while(playerIterator.hasNext()) {
                html.append("<li> " + playerIterator.next().getUser().getName() + "</li>");
            }
            html.append("</ul>");
            html.append("<h3> Deciding Tie Breaker Round </h3>");
            html.append("<p><i>Drawing Random card from deck for each Shortlisted Player</i></p>");
            dealer.playTieBreakerRound();
            html.append("<p> cards pulled for each shortlisted player are </p>");
            dealer.getDealerHelper().getTable().getShortlistedPlayers().getLast().stream().forEach(player -> html.append("<p>" + player.getUser().getName() + " -> " + player.getCards().get(player.getCards().size()-1) + "</p>"));
            dealer.shortlistPlayersAgain();
        }
        List<Player> winners = dealer.getDealerHelper().getTable().getShortlistedPlayers().getLast();
        if(winners.size() == 1) {
            html.append("<h2> Winner is " + winners.get(0).getUser().getName()+"</h2>");
        } else {
            html.append("<h2> Deck is Empty. Winners are </h2><ul>");
            winners.stream().forEach( winner -> html.append("<li>" + winner + "</li>"));
            html.append("</ul>");
        }
        return html.toString();

    }
}
