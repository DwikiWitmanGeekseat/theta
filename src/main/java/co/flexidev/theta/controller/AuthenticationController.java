package co.flexidev.theta.controller;

import co.flexidev.theta.error.BadRequestException;
import co.flexidev.theta.error.ForbiddenRequestException;
import co.flexidev.theta.model.Person;
import co.flexidev.theta.model.Role;
import co.flexidev.theta.security.Login;
import co.flexidev.theta.service.PersonService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/authentication")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final PersonService personService;

    public AuthenticationController(AuthenticationManager authenticationManager, PersonService personService) {
        this.authenticationManager = authenticationManager;
        this.personService = personService;
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                Person person = personService.findByEmail(username);
                String access_token = JWT.create()
                        .withSubject(person.getEmail())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withClaim("email", "useremail")
                        .withClaim("roles", person.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);

                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception e) {
                throw new ForbiddenRequestException(e.getMessage());
            }
        } else {
            throw new BadRequestException("Refresh token is missing");
        }
    }

    @PostMapping(path = "/login")
    public ResponseEntity login(@RequestBody Login login) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    login.getEmail(), login.getPassword()
                            )
                    );

            Person person = (Person) authentication.getPrincipal();
            Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());

            String access_token = JWT.create()
                    .withSubject(person.getEmail())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000)) // 30 minutes
                    .withClaim("roles", person.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                    .withClaim("principal", person.toMap())
                    .sign(algorithm);

            String refresh_token = JWT.create()
                    .withSubject(person.getEmail())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 60 * 1000)) // 60 minutes
                    .sign(algorithm);

            LinkedHashMap<String, Object> tokens = new LinkedHashMap<>();
            tokens.put("id", person.getId());
            tokens.put("name", person.getName());
            tokens.put("email", person.getEmail());
            tokens.put("token", access_token);
            tokens.put("refresh_token", refresh_token);

            return ResponseEntity.ok(tokens);
        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body("Invalid Username and Password");
        }
    }
}
