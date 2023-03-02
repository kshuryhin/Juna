package ua.pp.juna.vacanciesservice.service;

import ua.pp.juna.vacanciesservice.domain.UserDetails;

import java.util.List;

public interface UserDetailsService {
    UserDetails saveUserDetails(UserDetails userDetails);

    UserDetails getUserDetailsById(Long id);

    List<UserDetails> getAllUserDetails();

    UserDetails getUserDetailsByEmail(String email);

    UserDetails updateUserDetails(UserDetails userDetails, Long id);

    void deleteUserDetails(Long id);
}
