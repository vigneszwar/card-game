package cardgame.services.frontendservices;

import cardgame.schemas.User;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Component
public class UserPoolHandler {
    Logger logger = LoggerFactory.getLogger(UserPoolHandler.class);

    public List<User> userWaitPool;

    public UserPoolHandler() {
        this.userWaitPool = new ArrayList<>();
    }

    public void addUserToPool(User user) {
        logger.debug("Adding user " + user);
        userWaitPool.add(user);
    }



}
