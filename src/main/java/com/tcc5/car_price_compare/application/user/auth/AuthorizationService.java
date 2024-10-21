package com.tcc5.car_price_compare.application.user.auth;

import com.tcc5.car_price_compare.infra.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {
    @Autowired
    private UserRepository repository;
    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        return repository.findByEmail(mail);
    }
}
