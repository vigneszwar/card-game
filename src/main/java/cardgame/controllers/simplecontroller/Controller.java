package cardgame.controllers.simplecontroller;

import cardgame.services.frontendservices.HtmlWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/simple-card-game/v1")
public class Controller {

    @Autowired
    HtmlWrapper htmlWrapper;

    @GetMapping
    public String joinMatch() {
        return htmlWrapper.startGameAndFetchGameResult();
    }

}
