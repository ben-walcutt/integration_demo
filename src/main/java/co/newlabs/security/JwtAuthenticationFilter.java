package co.newlabs.security;

import co.newlabs.dto.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager a) {
        this.authenticationManager = a;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        try {
            UserDTO credentials = new ObjectMapper().readValue(req.getInputStream(), UserDTO.class);

            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword(), Collections.emptyList()));
        } catch (IOException ex) {
            throw new RuntimeException("error");
        }
    }

    @Override
    public void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException, ServletException {
        User user = (User)auth.getPrincipal();

        List<GrantedAuthority> authorities = user.getAuthorities().stream().collect(Collectors.toList());

        String token = Jwts.builder()
                .setSubject(user.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 10)))
                .setAudience(authorities.get(0).getAuthority())
                .signWith(SignatureAlgorithm.HS256, "somesecret".getBytes())
                .compact();
        res.setHeader("Authorization", "Bearer " + token);
        res.setHeader("ACCESS-CONTROL-EXPOSE-HEADERS", "AUTHORIZATION");
    }
}
