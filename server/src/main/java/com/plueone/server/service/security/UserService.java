package com.plueone.server.service.security;

import java.util.HashSet;
import java.util.Set;

import com.plueone.server.models.JwtAuth.Role;
import com.plueone.server.models.JwtAuth.User;
import com.plueone.server.repo.JwtRepo.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.h2.H2ConsoleAutoConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("In the user details service");

        return userRepo.findByName(username).orElseThrow(()-> new UsernameNotFoundException(username));
    }

}
