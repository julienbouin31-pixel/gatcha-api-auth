package fr.imt.gatcha.gatcha_api_auth;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatchaApiAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatchaApiAuthApplication.class, args);
	}
    @Bean
    CommandLineRunner init(UserRepository userRepository) {
        return args -> {
            userRepository.deleteAll();
            System.out.println("Base de données vidée !");
            UserEntity user = new UserEntity();
            user.setUsername("pablo");
            user.setPassword("1234");

            userRepository.save(user);
            System.out.println("Utilisateur de test 'pablo' inséré en base !");
        };
    }

}
