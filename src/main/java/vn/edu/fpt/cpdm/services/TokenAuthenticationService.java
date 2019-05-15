package vn.edu.fpt.cpdm.services;


import org.springframework.security.core.Authentication;
import vn.edu.fpt.cpdm.models.UserToken;

import javax.servlet.http.HttpServletRequest;

public interface TokenAuthenticationService {

    UserToken getToken(Authentication auth);
    Authentication getAuthentication(HttpServletRequest request);
}
