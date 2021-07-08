package cardgame.demo.backgroundprocesses;

import cardgame.exceptions.PlayerFullException;
import cardgame.schemas.User;
import cardgame.services.frontendservices.UserPoolHandler;
import cardgame.services.backendervices.UserDealerMap;
import cardgame.services.dealerservices.Dealer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
public class GameSchedulerService implements CommandLineRunner {
    Logger logger = LoggerFactory.getLogger(GameSchedulerService.class);
    final UserPoolHandler userPoolHandler;
    final UserDealerMap userDealerMap;
    @Autowired
    public GameSchedulerService(UserPoolHandler userPoolHandler, UserDealerMap userDealerMap) {
        this.userPoolHandler = userPoolHandler;
        this.userDealerMap = userDealerMap;
    }

    private void checkAndScheduleGame(User... users) {
        logger.debug("scheduling game");
        Dealer dealer = new Dealer();
        for (User user :
                users) {
            try {
                userDealerMap.mapUserDealer(user.getId(), dealer);
                dealer.addPlayer(user);
            } catch (PlayerFullException e) {
                e.printStackTrace();
            }
        }
        dealer.playGame();
    }

    @Override
    public void run(String... args) throws Exception {
        while(true) {
            Thread.sleep(2000);
            logger.debug("Checking User pool Size"+ userPoolHandler.userWaitPool.size());
            if(userPoolHandler.userWaitPool.size() > 3) {
                checkAndScheduleGame(userPoolHandler.userWaitPool.remove(0),
                        userPoolHandler.userWaitPool.remove(0),
                        userPoolHandler.userWaitPool.remove(0),
                        userPoolHandler.userWaitPool.remove(0));
            }
        }

    }
}
