package com.freelance.project.demo.config.security;

import com.freelance.project.demo.models.Person;
import com.freelance.project.demo.repository.PersonRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class CustomUserDetailsService implements UserDetailsService {

    private PersonRepository users;

    public CustomUserDetailsService(PersonRepository users) {
        this.users = users;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Person user = users.findByEmail(email);

        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(), true,
                true, true,
                true, getGrantedAuthorities(user));
    }

    private List<GrantedAuthority> getGrantedAuthorities(Person person) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + person.getRole()));
        return authorities;
    }
}