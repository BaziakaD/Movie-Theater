package com.baziaka.service.security;

import com.baziaka.domain.Role;
import com.baziaka.domain.User;
import com.baziaka.service.crud.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
public class CinemaTheatreUserDetailService implements UserDetailsService {

    private UserService userService;

    @Autowired
    public CinemaTheatreUserDetailService(UserService userService) {
        this.userService = userService;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException(
                    "No user found with username: " + email);
        }

        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        Set<GrantedAuthority> authorities = getAuthorities(Collections.singleton(user.getRole()));

        return new org.springframework.security.core.userdetails.User
                (user.getEmail(),
                        user.getPassword(), enabled, accountNonExpired,
                        credentialsNonExpired, accountNonLocked,
                        authorities);
    }

    private static Set<GrantedAuthority> getAuthorities(Set<Role> roles) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRole()));
        }
        return authorities;
    }
}
