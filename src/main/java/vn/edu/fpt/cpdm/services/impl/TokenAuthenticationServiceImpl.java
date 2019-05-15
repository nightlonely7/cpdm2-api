package vn.edu.fpt.cpdm.services.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import vn.edu.fpt.cpdm.models.UserToken;
import vn.edu.fpt.cpdm.services.TokenAuthenticationService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TokenAuthenticationServiceImpl implements TokenAuthenticationService {

    private static final Long EXPIRATION_TIME = 864_000_000L; // 10 days
    private static final String SECRET = "CPDM";
    private static final String TOKEN_PREFIX = "Bearer";
    private static final String HEADER_STRING = "Authorization";


    @Autowired
    public TokenAuthenticationServiceImpl() {
    }


    @Override
    public UserToken getToken(Authentication auth) {
        List<String> authorities = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        String tokenStr = Jwts.builder()
                .setSubject(auth.getName())
                .claim("authorities", authorities)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        UserToken userToken = new UserToken();
        userToken.setToken(tokenStr);
        return userToken;
    }

    @Override
    public Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {

            // parse the token.
            String username;
            List<String> authorities;
            try {
                Claims claims = Jwts.parser()
                        .setSigningKey(SECRET)
                        .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                        .getBody();
                username = claims.getSubject();
                authorities = (List<String>) claims.get("authorities");
            } catch (Exception e) {
                return null;
            }

            System.out.println(username);
            System.out.println(authorities);
            if (username != null && authorities != null) {
                return new UsernamePasswordAuthenticationToken(username, null,
                        authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
            }
        }
        return null;
    }
}
