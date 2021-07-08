package cardgame.controllers.backend;

import cardgame.schemas.User;
import cardgame.services.backendervices.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    final UserService userService;

    @Autowired
    public UserController( UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUser() {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") long userId){
        return ResponseEntity.ok(userService.getUser(userId));
    }

    @PostMapping
    public ResponseEntity<User> createUser(HttpServletRequest request, @RequestBody User user) {
        User createdUser = userService.addUser(user);
        return ResponseEntity.created(URI.create(request.getRequestURI().concat("/"+user.getId()))).body(createdUser);
    }
}
