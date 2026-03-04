package fr.imt.gatcha.gatcha_api_auth;


import fr.imt.gatcha.gatcha_api_auth.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface UserRepository extends MongoRepository<UserEntity, String> {
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByCurrentToken(String token);
}