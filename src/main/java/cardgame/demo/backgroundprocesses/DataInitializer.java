package cardgame.demo.backgroundprocesses;

import cardgame.demo.Config;
import cardgame.schemas.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Profile("!test")
public class DataInitializer implements CommandLineRunner {
    Logger logger = LoggerFactory.getLogger(DataInitializer.class);
    @Autowired
    RestTemplate restTemplate;

    @Override
    public void run(String... args) throws Exception {
        String url = "http://"+ Config.HOST +":"+Config.PORT+"/users";
        logger.info(url);

        User user1 = new User();
        HttpEntity<User> request =
                new HttpEntity<>(user1);
        user1.setName("p1");
        user1 = restTemplate.postForObject(url, request, User.class);
        logger.info("User 1 created" + user1);
        User user2 = new User();
        request = new HttpEntity<>(user2);
        user2.setName("p2");
        user2 = restTemplate.postForObject(url, request, User.class);
        logger.info("User 2 created" + user2);
        User user3 = new User();
        request = new HttpEntity<>(user3);
        user3.setName("p3");
        user3 = restTemplate.postForObject(url, request, User.class);
        logger.info("User 3 created" + user3);
        User user4 = new User();
        request = new HttpEntity<>(user4);
        user4.setName("p4");
        user4 = restTemplate.postForObject(url, request, User.class);
        logger.info("User 4 created" + user4);


    }
}
