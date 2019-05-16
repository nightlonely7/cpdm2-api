package vn.edu.fpt.cpdm.services;

import vn.edu.fpt.cpdm.entities.UserEntity;

public interface AuthenticationService {
    UserEntity getCurrentLoggedUser();
}
