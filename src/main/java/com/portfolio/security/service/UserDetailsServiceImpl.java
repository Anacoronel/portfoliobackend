package com.portfolio.security.service;

import com.portfolio.security.entity.UserPrincipal;
import com.portfolio.security.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    
    @Autowired
    UserService userService;
    
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userService.getByUsernameOrEmail(usernameOrEmail).get();
        return UserPrincipal.build(user);
    }
}
