package com.longshit.copydog.rest.controllers;

import com.longshit.copydog.rest.models.User;
import com.longshit.copydog.rest.payload.response.JwtResponse;
import com.longshit.copydog.rest.repository.RoleRepository;
import com.longshit.copydog.rest.repository.UserRepository;
import com.longshit.copydog.rest.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
public class AuthenticationController {


    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtUtils jwtUtils;

    @GetMapping("/generateToken")
    public ResponseEntity<?> generateToken(@AuthenticationPrincipal OAuth2User principal) {
        String jwt = jwtUtils.generateJwtToken(principal.getAttribute("login").toString());

        User user = userRepository.findByUsername(principal.getAttribute("login").toString()).get();


        return ResponseEntity.ok(new JwtResponse(jwt,
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRoles().stream().map(x -> x.getName().name()).collect(Collectors.toSet())));
    }

}
