package com.example.customclaimsjwt.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ResourceOwnerDetailsService implements UserDetailsService {

    private ResourceOwnerRepository repo;

    public ResourceOwnerDetailsService(ResourceOwnerRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ResourceOwner user =
                repo.findByUsername(username).orElseThrow(() -> new RuntimeException());

        return new ResourceOwnerUserDetails(user);
    }
}
