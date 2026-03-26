package mg.restaurant.restapi.service;

import mg.restaurant.restapi.model.User;
import mg.restaurant.restapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    UserRepository repo;

    @Autowired
    JwtService jwtService;

    public String login(String email, String password) {
        Optional<User> user = repo.findByEmailAndPassword(email, password);

        if (user.isEmpty()) {
            return null;
        }

        return jwtService.generateToken(user.get().getEmail());
    }

    public User register(String email, String password, String role) {
        User user = new User(email, password, role);
        return repo.save(user);
    }
}
