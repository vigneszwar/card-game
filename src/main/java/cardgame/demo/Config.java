package cardgame.demo;

import cardgame.schema.Player;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Configuration
public class Config {
    @Bean
    @Scope(value="prototype")
    public List<Player> playerList() {
        return new ArrayList<>();
    }
//
//    @Bean
//    @Scope(value="prototype")
//    public HashMap<Long, Player> playerMap() {
//        return new HashMap<Long, Player>();
//    }
}
