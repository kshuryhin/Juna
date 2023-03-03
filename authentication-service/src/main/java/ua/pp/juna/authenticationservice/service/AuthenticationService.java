package ua.pp.juna.authenticationservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.pp.juna.authenticationservice.config.JwtService;
import ua.pp.juna.authenticationservice.controller.models.AuthenticationRequest;
import ua.pp.juna.authenticationservice.controller.models.AuthenticationResponse;
import ua.pp.juna.authenticationservice.controller.models.ExchangeRequest;
import ua.pp.juna.authenticationservice.controller.models.RegisterRequest;

import ua.pp.juna.authenticationservice.model.User;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(final RegisterRequest request) {
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .isLoggedIn(true)
                .build();
        userDetailsService.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .role(user.getRole())
                .build();
    }

    public AuthenticationResponse authenticate(final AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = (User)userDetailsService.loadUserByUsername(request.getEmail());
        var jwtToken = jwtService.generateToken(user);
        user = userDetailsService.updateUser(user.withLoggedIn(true),jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .role(user.getRole())
                .build();
    }

    public AuthenticationResponse updateToken(final ExchangeRequest exchangeRequest) {
        var user = (User)userDetailsService.loadUserByUsername(exchangeRequest.getEmail());
        if (!user.isLoggedIn()) throw new AuthorizationServiceException("User is logged out!");
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .role(user.getRole())
                .build();
    }

    public void logout(final String email) {
        var user = (User)userDetailsService.loadUserByUsername(email);
        var jwtToken = jwtService.generateToken(user);
        userDetailsService.updateUser(user.withLoggedIn(false), jwtToken);
    }
}
