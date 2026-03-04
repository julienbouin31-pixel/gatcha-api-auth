package fr.imt.gatcha.gatcha_api_auth;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.Map;

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
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try{
            AuthResponse authResponse =  authService.login(request);
            return ResponseEntity.ok(authResponse);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(Map.of(
                    "code", 401,
                    "message", e.getMessage()
            ));
        }
    }

    @PostMapping("/verifyToken")
    public ResponseEntity<?> verify(@RequestParam String token) {
        try {
            String username = authService.verifyToken(token);
            return ResponseEntity.ok(Map.of("username", username));
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(Map.of(
                    "code", 401,
                    "message", e.getMessage()
            ));
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> getProfile(@RequestHeader("Authorization") String token) {
        return userRepository.findByCurrentToken(token)
                .map(user -> ResponseEntity.ok("Salut " + user.getUsername()))
                .orElse(ResponseEntity.status(401).build());
    }
}