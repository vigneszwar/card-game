package cardgame.repositories;

import cardgame.schemas.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
@Setter
@Component
public class UserRepository {
    private HashMap<Long, User> users;
    private long nextId = 1;

    public UserRepository() {
        users = new HashMap<>();
    }

    public User addUser(User user) {
        user.setId(nextId++);
        users.put(user.getId(), user);
        return user;
    }
    public User getUser(long id) {
        return users.get(id);
    }

    public List<User> getAllUsers() {
        return new ArrayList<User>(users.values());
    }
    public void updateUser(User modifiedUser) {
        users.put(modifiedUser.getId(), modifiedUser);
    }
    public User deleteUser(long id) {
        return users.remove(id);
    }
}
