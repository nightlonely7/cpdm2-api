package vn.edu.fpt.cpdm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import vn.edu.fpt.cpdm.forms.users.UserLoginForm;
import vn.edu.fpt.cpdm.models.UserToken;
import vn.edu.fpt.cpdm.services.TokenAuthenticationService;
import vn.edu.fpt.cpdm.services.UserService;

@Controller
public class MainController {

    private final AuthenticationManager authenticationManager;
    private final TokenAuthenticationService tokenAuthenticationService;
    private final UserService userService;

    @Autowired
    public MainController(AuthenticationManager authenticationManager,
                          TokenAuthenticationService tokenAuthenticationService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.tokenAuthenticationService = tokenAuthenticationService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserToken> login(@RequestBody UserLoginForm userLoginForm) {
        UsernamePasswordAuthenticationToken authReq
                = new UsernamePasswordAuthenticationToken(userLoginForm.getUsername(), userLoginForm.getPassword());
        Authentication auth = authenticationManager.authenticate(authReq);
        if (auth != null) {
            UserToken userToken = tokenAuthenticationService.getToken(auth);
            return ResponseEntity.ok(userToken);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

//    @GetMapping("/self")
//    public ResponseEntity<UserBasic> self(Principal principal) {
//
//        UserBasic userBasic = userService.findBasicByEmail(principal.getName());
//
//        return ResponseEntity.ok(userBasic);
//    }

}
