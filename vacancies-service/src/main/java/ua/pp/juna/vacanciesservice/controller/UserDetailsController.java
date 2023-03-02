package ua.pp.juna.vacanciesservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.pp.juna.vacanciesservice.domain.UserDetails;
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
}
