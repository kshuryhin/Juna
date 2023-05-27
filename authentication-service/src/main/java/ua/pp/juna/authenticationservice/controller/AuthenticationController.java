package ua.pp.juna.authenticationservice.controller;

import jakarta.ws.rs.Path;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ua.pp.juna.authenticationservice.controller.models.*;
import ua.pp.juna.authenticationservice.service.AuthenticationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService service;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/authenticate/mentor")
    public ResponseEntity<AuthenticationResponse> authenticateForMentors(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok().body(service.authenticateMentor(request));
    }

    @PutMapping("/exchange/{email}")
    public ResponseEntity<AuthenticationResponse> updateToken(@PathVariable String email) {
        return ResponseEntity.ok().body(service.updateToken(email));
    }

    @PutMapping("/logout/{email}")
    public ResponseEntity<Void> logout(@PathVariable String email) {
        service.logout(email);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/change/password/{email}")
    public ResponseEntity<ChangePasswordResponse> changePassword(@PathVariable String email,
                                                                 @RequestBody ChangePasswordRequest changePasswordRequest) {
        return ResponseEntity.ok(service.changePassword(changePasswordRequest, email));
    }
}