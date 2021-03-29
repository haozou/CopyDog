package com.longshit.copydog.rest.security;

import com.longshit.copydog.rest.models.ERole;
import com.longshit.copydog.rest.models.Role;
import com.longshit.copydog.rest.models.User;
import com.longshit.copydog.rest.repository.RoleRepository;
import com.longshit.copydog.rest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class OauthUtils {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public void registerOauthUser(OAuth2User oAuth2User) {
        String username = oAuth2User.getAttribute("login").toString();
        String email = oAuth2User.getAttribute("email").toString();
        if (userRepository.existsByUsername(username) || userRepository.existsByEmail(email)) {
            return;
        }

        // Create new user's account
        User user = new User(username, email);

        Set<String> strRoles = oAuth2User.getAuthorities().stream().map(x -> x.getAuthority().toLowerCase()).collect(Collectors.toSet());
        Set<Role> roles = new HashSet<>();

        strRoles.forEach(role -> {
            switch (role) {
                case "admin":
                    Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(adminRole);

                    break;
                case "guest":
                    Role modRole = roleRepository.findByName(ERole.ROLE_GUEST)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(modRole);

                    break;
                default:
                    Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(userRole);
            }
        });

        user.setRoles(roles);
        userRepository.save(user);
    }

    public String getUserNameFromAuth(Authentication authentication) {
        if (authentication.getPrincipal() instanceof UserDetails) {
            return ((UserDetails) authentication.getPrincipal()).getUsername();
        } else if (authentication.getPrincipal() instanceof OAuth2User) {
            return ((OAuth2User) authentication.getPrincipal()).getAttribute("login");
        } else {
            throw new RuntimeException("authentication type not supported: " + authentication.getPrincipal().toString());
        }
    }
}
