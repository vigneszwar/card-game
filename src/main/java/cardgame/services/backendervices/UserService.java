package cardgame.services.backendervices;

import cardgame.repositories.UserRepository;
import cardgame.schemas.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User addUser(User user) {
        return userRepository.addUser(user);
    }

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public User getUser(long id) {
        return userRepository.getUser(id);
    }
}
