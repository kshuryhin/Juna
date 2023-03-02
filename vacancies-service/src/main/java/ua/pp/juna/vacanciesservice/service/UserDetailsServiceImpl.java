package ua.pp.juna.vacanciesservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.pp.juna.vacanciesservice.domain.UserDetails;
import ua.pp.juna.vacanciesservice.repo.UserDetailsRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserDetailsRepository userDetailsRepository;
    @Override
    public UserDetails saveUserDetails(UserDetails userDetails) {
        return userDetailsRepository.save(userDetails);
    }

    @Override
    public UserDetails getUserDetailsById(Long id) {
        return userDetailsRepository.findById(id).orElse(null);
    }

    @Override
    public List<UserDetails> getAllUserDetails() {
        return userDetailsRepository.findAll();
    }

    @Override
    public UserDetails getUserDetailsByEmail(String email) {
        return userDetailsRepository.findUserDetailsByEmail(email).orElse(null);
    }

    @Override
    public UserDetails updateUserDetails(UserDetails userDetails, Long id) {
        final UserDetails updatedUserDetails = getUserDetailsById(id)
                .withEmail(userDetails.getEmail())
                .withFirstName(userDetails.getFirstName())
                .withLastName(userDetails.getLastName())
                .withLoggedIn(userDetails.isLoggedIn())
                .withPassword(userDetails.getPassword());
        return saveUserDetails(updatedUserDetails);
    }

    @Override
    public void deleteUserDetails(Long id) {
        userDetailsRepository.deleteById(id);
    }
}
