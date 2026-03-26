package mg.restaurant.restapi.controller;

import mg.restaurant.restapi.dto.LoginDTO;
import mg.restaurant.restapi.dto.RegisterDTO;
import mg.restaurant.restapi.model.User;
import mg.restaurant.restapi.service.AuthService;
import mg.restaurant.restapi.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @Autowired
    JwtService jwtService;

    @PostMapping("/login")
    public Map login(@RequestBody LoginDTO dto) {
        String token = authService.login(dto.getEmail(), dto.getPassword());

        if (token == null) {
            return Map.of("message", "invalid login");
        }

        return Map.of("token", token);
    }

    @PostMapping("/register")
    public Map register(@RequestBody RegisterDTO dto) {
        User user = authService.register(dto.getEmail(), dto.getPassword(), dto.getRole());
        String token = jwtService.generateToken(user.getEmail());
        return Map.of("token", token, "email", user.getEmail(), "role", user.getRole());
    }
}
