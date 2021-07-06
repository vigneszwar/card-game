package cardgame.service;

import cardgame.schema.Player;
import cardgame.schema.Rank;
import cardgame.schema.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

@Component
public class HtmlWrapper {
    @Autowired
    GameEventHandler gameEventHandler;

    @Value("${table.player.limit}")
    private int playerLimit;

    public String startGameAndFetchGameResult() {
        if(gameEventHandler.getPlayers().size() != playerLimit) {

            User u1 = new User(1,  "p1");
            User u2 = new User(2, "p2");
            User u3 = new User( 3, "p3");
            User u4 = new User(4, "p4");
            gameEventHandler.addPlayer(u1);
            gameEventHandler.addPlayer(u2);
            gameEventHandler.addPlayer(u3);
            gameEventHandler.addPlayer(u4);
        }

        StringBuilder html = new StringBuilder();
        html.append("<h1> Simple Card game </h1>");

        html.append(" <h3> Users added </h3>");
        html.append(" <ul>");
        gameEventHandler.getPlayers().stream().forEach(player -> html.append("<li>" + player.getUser().getName() +"</li>"));
        html.append(" </ul>");
        gameEventHandler.initializeGame();
        gameEventHandler.startGame();
        html.append(" <h3> Cards Assigned For Each Player</h3>");
        html.append(" <ul>");
        Iterator<Player> it = gameEventHandler.getDealer().getTable().getPlayers().iterator();
        while(it.hasNext()) {
            Player p = it.next();
            html.append("<li>"+p.getUser().getName() + " " + p.getCards() +"</li>");
        }
        html.append("</ul>");
        gameEventHandler.assignRankForEachPlayerAndShortList();
        Iterator<Rank> rankIterator = gameEventHandler.getDealer().getRanks().iterator();
        html.append("<h3> Best Combination for each Player (In Order)</h3> <ul>");
        while(rankIterator.hasNext()) {
            Rank rank = rankIterator.next();
            html.append("<li>" + rank.getPlayer().getUser().getName() + " - " + rank.getRankValueEnum().name() + "</li>");
        }
        html.append("</ul>");

        while(!gameEventHandler.isGameOver()) {
            html.append("<h3> Tie between these players </h3><ul>");
            Iterator<Player> playerIterator = gameEventHandler.getDealer().getTable().getShortlistedPlayers().iterator();
            while(playerIterator.hasNext()) {
                html.append("<li> " + playerIterator.next().getUser().getName() + "</li>");
            }
            html.append("</ul>");
            html.append("<h3> Deciding Tie Breaker Round </h3>");
            html.append("<p><i>Drawing Random card from deck for each Shortlisted Player</i></p>");
            gameEventHandler.playTieBreakerRound();
            html.append("<p> cards pulled for each shortlisted player are </p>");
            gameEventHandler.getDealer().getTable().getShortlistedPlayers().stream().forEach(player -> html.append("<p>" + player.getUser().getName() + " -> " + player.getCards().get(0) + "</p>"));
            gameEventHandler.shortlistPlayersAgain();
        }
        List<Player> winners = gameEventHandler.getDealer().getTable().getShortlistedPlayers();
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
