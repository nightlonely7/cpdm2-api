package vn.edu.fpt.cpdm.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import vn.edu.fpt.cpdm.models.users.UserBasic;

public interface UserService extends UserDetailsService {

    UserBasic findBasicByUsername(String username);
}
