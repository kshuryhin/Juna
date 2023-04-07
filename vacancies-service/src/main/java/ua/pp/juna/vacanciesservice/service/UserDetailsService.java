package ua.pp.juna.vacanciesservice.service;

import org.apache.catalina.User;
import ua.pp.juna.vacanciesservice.domain.UserDetails;

import java.util.List;

public interface UserDetailsService {
    UserDetails saveUserDetails(UserDetails userDetails);

    UserDetails getUserDetailsById(Long id);

    List<UserDetails> getAllUserDetails();

    UserDetails getUserDetailsByEmail(String email);

    UserDetails updateUserDetails(UserDetails userDetails, Long id);

    UserDetails patchUserDetails(String email, String newPassword);

    void deleteUserDetails(Long id);
}
