package com.example.clientauthorizationcode.security;

import com.example.clientauthorizationcode.user.ClientUser;
import com.example.clientauthorizationcode.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class ClientUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository users;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<ClientUser> optionalUser = users.findByUsername(username);

        if(!optionalUser.isPresent()) {
            throw new UsernameNotFoundException("invalid username or password");
        }
        return new ClientUserDetails(optionalUser.get());
    }
}
