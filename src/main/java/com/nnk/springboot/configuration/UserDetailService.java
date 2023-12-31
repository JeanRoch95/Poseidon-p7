package com.nnk.springboot.configuration;

import com.nnk.springboot.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Custom UserDetailsService implementation.
 */
@Service
@Transactional
public class UserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Loads user by username.
     *
     * @param username the username
     * @return UserDetails object
     * @throws UsernameNotFoundException if username is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        com.nnk.springboot.domain.User user = userRepository.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("User " + username + " not found");
        }

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole()));

        System.out.println(grantedAuthorities);
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }
}
