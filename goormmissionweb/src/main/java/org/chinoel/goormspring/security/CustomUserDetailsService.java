package org.chinoel.goormspring.security;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.chinoel.goormspring.entity.User;
import org.chinoel.goormspring.entity.UserRole;
import org.chinoel.goormspring.repository.UserRoleRepository;
import org.chinoel.goormspring.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;
    private final UserRoleRepository userRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findUserByID(username);

        if (user == null) throw new UsernameNotFoundException("");

        List<UserRole> userRoles = userRoleRepository.findByUser(user);
        List<GrantedAuthority> authorities = userRoles.stream()
                .map(userRole -> new SimpleGrantedAuthority("ROLE_"
                + userRole.getRole().getName()))
                .collect(Collectors.toList());

        CustomUserDetails customUserDetails = new CustomUserDetails();
        customUserDetails.setSeq(user.getId());
        customUserDetails.setLoginId(user.getUsername());
        customUserDetails.setPassword(user.getPassword());
        customUserDetails.setAuthorities(authorities);

        return customUserDetails;
    }
}
