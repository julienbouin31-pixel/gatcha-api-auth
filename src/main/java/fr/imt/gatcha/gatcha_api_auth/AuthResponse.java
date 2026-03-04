package fr.imt.gatcha.gatcha_api_auth;

import java.time.LocalDateTime;

public record AuthResponse(String token, String username, LocalDateTime tokenExpirationDate) {}
