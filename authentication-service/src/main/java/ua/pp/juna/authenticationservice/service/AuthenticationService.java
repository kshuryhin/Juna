package ua.pp.juna.authenticationservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ua.pp.juna.authenticationservice.config.JwtService;
import ua.pp.juna.authenticationservice.controller.models.*;

import ua.pp.juna.authenticationservice.model.Role;
import ua.pp.juna.authenticationservice.model.User;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userDetailsService;
    private final MentorService mentorService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RestTemplate restTemplate;

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

    public AuthenticationResponse authenticateMentor(final AuthenticationRequest request) {

        var fetched = restTemplate.getForObject("http://gateway-service/students/email/{email}",
                User.class, request.getEmail());

        if (fetched != null) {
            return authenticateStudent(request, fetched);
        }

        var user = (User)mentorService.loadUserByUsername(request.getEmail());
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Bad credentials!");
        }

        var jwtToken = jwtService.generateToken(user.withRole(Role.MENTORS));
        user = mentorService.updateMentor(user.withLoggedIn(true).withRole(Role.MENTORS), jwtToken);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .role(Role.MENTORS)
                .build();

    }

    private AuthenticationResponse authenticateStudent(final AuthenticationRequest request, User user) {
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Bad credentials!");
        }

        var jwtToken = jwtService.generateToken(user.withRole(Role.STUDENTS));
        user = mentorService.updateMentor(user.withLoggedIn(true).withRole(Role.STUDENTS), jwtToken);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .role(Role.STUDENTS)
                .build();

    }

    public AuthenticationResponse updateToken(final String email) {
        var user = (User)userDetailsService.loadUserByUsername(email);
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

    public ChangePasswordResponse changePassword(final ChangePasswordRequest changePasswordRequest, final String email) {
        final var user = (User)userDetailsService.loadUserByUsername(email);
        final var jwtToken = jwtService.generateToken(user);
        if (!passwordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPassword()))
            return ChangePasswordResponse.builder().success(false).build();
        userDetailsService.patchUser(email, passwordEncoder.encode(changePasswordRequest.getNewPassword()), jwtToken);
        return ChangePasswordResponse.builder().success(true).build();
    }
}
