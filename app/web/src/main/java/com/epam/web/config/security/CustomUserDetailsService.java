package com.epam.web.config.security;

import com.epam.core.entity.User;
import com.epam.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * The type Custom user details service.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    /**
     * Instantiates a new Custom user details service.
     *
     * @param userService the user service
     */
    @Autowired
    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> optionalUser = Optional.ofNullable(userService.getUserByLogin(s));
        User user = optionalUser.orElseThrow(() -> new UsernameNotFoundException("User not found"));
        String login = user.getLogin();
        String password = user.getPassword();
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole().toString()));
        return new org.springframework.security.core.userdetails.User(login, password, authorities);
    }
}
