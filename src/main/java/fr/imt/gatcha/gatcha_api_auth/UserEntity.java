package fr.imt.gatcha.gatcha_api_auth;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
import java.util.Date;

@Document(collection = "users")
public class UserEntity {
    @MongoId
    private String id;
    private String username;
    private String password;
    private String currentToken;
    private LocalDateTime tokenExpirationDate;

    public UserEntity() {}

    public LocalDateTime getTokenExpirationDate() {
        return tokenExpirationDate;
    }

    public void setTokenExpirationDate(LocalDateTime tokenExpirationDate) {
        this.tokenExpirationDate = tokenExpirationDate;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getCurrentToken() { return currentToken; }
    public void setCurrentToken(String currentToken) { this.currentToken = currentToken; }



}