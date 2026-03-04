package fr.imt.gatcha.gatcha_api_auth;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;

    public AuthController(AuthService authService, UserRepository userRepository) {
        this.authService = authService;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
    @PostMapping("/verifyToken")
    public boolean verifyToken(@RequestParam String token) {
        return authService.verifyToken(token);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getProfile(@RequestHeader("Authorization") String token) {
        return userRepository.findByCurrentToken(token)
                .map(user -> ResponseEntity.ok("Salut " + user.getUsername()))
                .orElse(ResponseEntity.status(401).build());
    }
}