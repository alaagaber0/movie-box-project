package com.fawry.movie.auth.Service;


import com.fawry.movie.auth.Entity.AppUser;
import com.fawry.movie.auth.Entity.Model.SecurityUser;
import com.fawry.movie.auth.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUser> user = userRepository.findByUsername(username);
        if(user.isPresent())
        {
            return new SecurityUser(user.get());
        }
        throw new UsernameNotFoundException("No Such User Found");
    }
}
