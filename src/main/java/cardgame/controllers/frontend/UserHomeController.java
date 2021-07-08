package cardgame.controllers.frontend;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class UserHomeController {
    @RequestMapping("/home")
    public String loadHomePage(@RequestParam(name = "userId") long userId, Model model){
        model.addAttribute("userid", userId);
        return "userhomepage"; //loads the userhomepage.html

    }
}
