package ua.pp.juna.vacanciesservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.pp.juna.vacanciesservice.controller.candidates.models.ChangePasswordRequest;
import ua.pp.juna.vacanciesservice.domain.UserDetails;
import ua.pp.juna.vacanciesservice.domain.candidates.Candidate;
import ua.pp.juna.vacanciesservice.service.UserDetailsService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/userDetails")
public class UserDetailsController {
    private final UserDetailsService userDetailsService;

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDetails> getUserDetailsByEmail(@PathVariable String email) {
        return ResponseEntity.ok().body(userDetailsService.getUserDetailsByEmail(email));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDetails> updateUserDetails(@RequestBody UserDetails userDetails, @PathVariable Long id) {
        return ResponseEntity.ok().body(userDetailsService.updateUserDetails(userDetails, id));
    }

    @PatchMapping
    public ResponseEntity<UserDetails> patchUserDetails(@RequestBody ChangePasswordRequest changePasswordRequest) {
        return ResponseEntity.ok(userDetailsService.patchUserDetails(changePasswordRequest.getEmail(),
                changePasswordRequest.getNewPassword()));
    }
}
