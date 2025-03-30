package org.chinoel.goormspring.security;

import lombok.RequiredArgsConstructor;
import org.chinoel.goormspring.entity.User;
import org.chinoel.goormspring.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findUserByID(username);
        CustomUserDetails customUserDetails = new CustomUserDetails();

        customUserDetails.setSeq(user.getId());
        customUserDetails.setLoginId(user.getUsername());
        customUserDetails.setPassword(user.getPassword());
        return customUserDetails;
    }
}
