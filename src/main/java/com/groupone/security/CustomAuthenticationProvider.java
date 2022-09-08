package com.groupone.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
@RequiredArgsConstructor
@Service
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final CustomUserDetailsService customUserDetailsService;
    private static BCryptPasswordEncoder passwordEcorder = new BCryptPasswordEncoder();


    public Boolean doPasswordsMatch(String rawPassword,String encodedPassword) {
        return passwordEcorder.matches(rawPassword, encodedPassword);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        System.out.println("username = " + username);
        System.out.println("password = " + password);

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        return checkPassword(userDetails,password);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(aClass);
    }

    private Authentication checkPassword(UserDetails user, String rawPassword) {
        System.out.println("rawPassword = " + rawPassword);
        System.out.println("user.getPassword() = " + user.getPassword());
        if (doPasswordsMatch(rawPassword, user.getPassword())) {
            User innerUser = new User(
                    user.getUsername(),
                    user.getPassword(),
                    user.getAuthorities()
            );

            return new UsernamePasswordAuthenticationToken(innerUser, user.getPassword(), user.getAuthorities());
        } else {
            throw new BadCredentialsException("Bad credentials");
        }
    }
}