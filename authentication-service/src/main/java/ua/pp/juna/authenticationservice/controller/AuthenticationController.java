package ua.pp.juna.authenticationservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @PutMapping("/exchange")
    public ResponseEntity<AuthenticationResponse> updateToken(@RequestBody ExchangeRequest exchangeRequest) {
        return ResponseEntity.ok().body(service.updateToken(exchangeRequest));
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
