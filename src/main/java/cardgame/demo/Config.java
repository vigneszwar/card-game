package cardgame.demo;

import cardgame.schemas.Player;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class Config {
    public static int PLAYER_LIMIT;

    public static int CARD_LIMIT;

    public static String HOST;
    public static int PORT;
    @Value("${table.player.limit}")
    public void setPlayerLimit(int playerLimit) {
        Config.PLAYER_LIMIT = playerLimit;
    }
    @Value("${player.card.limit}")
    public void setCardLimit(int cardLimit) {
        Config.CARD_LIMIT = cardLimit;
    }
    @Value("${serverhost.name}")
    public void setHostName(String hostName) {
        Config.HOST = hostName;
    }
    @Value("${server.port}")
    public void setPortNumber(int portNumber) {
        Config.PORT = portNumber;
    }
    @Value("${serverhost.name}")
    String domain;


    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
//
//    @Bean
//    @Scope(value="prototype")
//    public HashMap<Long, Player> playerMap() {
//        return new HashMap<Long, Player>();
//    }
}
