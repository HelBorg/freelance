package com.freelance.project.demo.controller;

import com.freelance.project.demo.config.security.jwt.JwtTokenProvider;
import com.freelance.project.demo.models.Person;
import com.freelance.project.demo.repository.PersonRepository;
import com.freelance.project.demo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    PersonService personService;


    @PostMapping("/singin")
    public ResponseEntity signin(@RequestBody AuthenticationRequest credentials) {
        try {
            String email = credentials.getEmail();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, credentials.getPassword()));
            String token = jwtTokenProvider.createToken(email, this.personService.findByEmail(email).getRole());

            Map<Object, Object> model = new HashMap<>();
            model.put("email", email);
            model.put("token", token);
            return ok(model);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid email/password");
        }
    }

    @GetMapping("/me")
    public ResponseEntity currentUser(@AuthenticationPrincipal UserDetails userDetails){
        // careful!!! in custom userDetails username is EMAIL
        Person currentPerson = personService.findByEmail(userDetails.getUsername());
        Map<Object, Object> model = new HashMap<>();
        model.put("id", currentPerson.getPersonId());
        model.put("name", currentPerson.getName());
        return ok(model);
    }
}
