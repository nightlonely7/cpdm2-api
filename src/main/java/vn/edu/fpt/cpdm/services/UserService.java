package vn.edu.fpt.cpdm.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import vn.edu.fpt.cpdm.models.users.UserBasic;

import java.util.List;

public interface UserService extends UserDetailsService {

    UserBasic findBasicByUsername(String username);

    List<UserBasic> findAllExecutor();
}
