package cardgame.controllers.backend;

import cardgame.schemas.logical.UserView;
import cardgame.services.backendervices.UserViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user-view")
public class UserViewController { //backend service

    @Autowired
    UserViewService userViewService;

    @GetMapping("/{userId}")
    public UserView getUserView(@PathVariable("userId") long userId){
        return userViewService.buildUserView(userId);
    }

}
