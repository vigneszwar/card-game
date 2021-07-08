package cardgame.controllers.backend;

import cardgame.schemas.User;
import cardgame.services.frontendservices.UserPoolHandler;
import cardgame.services.backendervices.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/join")
public class UserPoolController {
    @Autowired
    UserPoolHandler userPoolHandler;

    @Autowired
    UserService userService;

    @PostMapping("{userId}")
    public void addUserToPool(@PathVariable("userId") long id, HttpServletResponse response) {
        User user = userService.getUser(id);
        userPoolHandler.addUserToPool(user);
        try {
            response.sendRedirect("/game?userId="+id);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @GetMapping()
    public Integer getUserPool() {
        return userPoolHandler.userWaitPool.size();
    }
}
