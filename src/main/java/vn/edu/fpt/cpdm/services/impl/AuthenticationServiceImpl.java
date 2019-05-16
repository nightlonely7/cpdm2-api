package vn.edu.fpt.cpdm.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.edu.fpt.cpdm.entities.UserEntity;
import vn.edu.fpt.cpdm.repositories.UserRepository;
import vn.edu.fpt.cpdm.services.AuthenticationService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;

    @Autowired
    public AuthenticationServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserEntity getCurrentLoggedUser() {

        String currentLoggedUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity currentLoggedUser = userRepository.findByUsername(currentLoggedUsername).orElseThrow(
                () -> new UsernameNotFoundException(currentLoggedUsername)
        );
        return currentLoggedUser;
    }
}
