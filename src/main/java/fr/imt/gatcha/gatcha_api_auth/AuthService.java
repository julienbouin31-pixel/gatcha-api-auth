package fr.imt.gatcha.gatcha_api_auth;

import org.springframework.stereotype.Service;
import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final ZoneId parisZone = ZoneId.of("Europe/Paris");

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AuthResponse login(LoginRequest request) {
        UserEntity user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        if (!user.getPassword().equals(request.password())) {
            throw new RuntimeException("Mot de passe incorrect");
        }

        ZonedDateTime now = ZonedDateTime.now(parisZone);

        String timestamp = now.format(DateTimeFormatter.ofPattern("yyyy/MM/dd-HH:mm:ss"));
        String nouveauToken = user.getUsername() + "-" + timestamp;

        user.setCurrentToken(nouveauToken);
        user.setTokenExpirationDate(now.plusHours(1).toLocalDateTime());

        userRepository.save(user);

        return new AuthResponse(nouveauToken, user.getUsername(), user.getTokenExpirationDate());
    }

    public boolean verifyToken(String token) {
        Optional<UserEntity> userOpt = userRepository.findByCurrentToken(token);

        if (userOpt.isPresent()) {
            UserEntity user = userOpt.get();
            ZonedDateTime now = ZonedDateTime.now(parisZone);

            if (now.toLocalDateTime().isAfter(user.getTokenExpirationDate())) {
                System.out.println("Token expiré pour : " + user.getUsername());
                return false;
            }

            user.setTokenExpirationDate(now.plusHours(1).toLocalDateTime());
            userRepository.save(user);
            return true;
        }

        return false;
    }
}