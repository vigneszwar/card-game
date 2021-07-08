package cardgame.controllers.frontend;

import cardgame.demo.Config;
import cardgame.schemas.logical.UserView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
public class GameViewController {
    Logger logger = LoggerFactory.getLogger(GameViewController.class);
    @Autowired
    RestTemplate restTemplate;


    @RequestMapping("/game")
    public String getGameViewPage(@RequestParam(name = "userId") long userId, Model model){
        logger.info("Fetching User");
        String url = "http://"+ Config.HOST +":"+Config.PORT+"/user-view/"+userId;
        UserView userView = restTemplate.getForObject(url, UserView.class);
        logger.info("userView" + userView);
        model.addAttribute("userView", userView);
        model.addAttribute("cardLimit", Config.CARD_LIMIT);
        return "usergamepage";
    }
}
