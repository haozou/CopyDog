package com.longshit.copydog.rest.controllers;

import com.longshit.copydog.rest.models.User;
import com.longshit.copydog.rest.payload.response.ErrorResponse;
import com.longshit.copydog.rest.payload.response.UserResponse;
import com.longshit.copydog.rest.repository.UserRepository;
import com.longshit.copydog.rest.security.OauthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class UserManagementController {
    @Autowired
    OauthUtils oauthUtils;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/api/v1.0/user/info")
    public ResponseEntity<?> getUserInfo() {
        try {
            String username = oauthUtils.getUserNameFromAuth(SecurityContextHolder.getContext().getAuthentication());
            User user = userRepository.findByUsername(username).get();
            return ResponseEntity.ok(new UserResponse(user, user.getFollowed(), user.getFollowers()));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(new ErrorResponse("Failed to get user details", e.getMessage()));
        }
    }

    @PostMapping("/api/v1.0/user/followers")
    public ResponseEntity<?> follow(@RequestBody Map<String, List<String>> body) {
        try {
            if (!body.containsKey("followed_people")) {
                return ResponseEntity.status(401).body(new ErrorResponse("No followed people specified", "no followed_people key in payload"));
            }
            List<String> followedPeople = body.get("followed_people");
            String username = oauthUtils.getUserNameFromAuth(SecurityContextHolder.getContext().getAuthentication());
            User currentUser = userRepository.findByUsername(username).get();
            followedPeople.forEach(followedUsername -> {
                if (userRepository.existsByUsername(followedUsername)) {
                    User followedUser = userRepository.findByUsername(followedUsername).get();
                    currentUser.getFollowed().add(followedUser);
                }
            });
            userRepository.save(currentUser);
            return ResponseEntity.accepted().body("succeed");
        } catch (Exception e) {
            return ResponseEntity.status(401).body(new ErrorResponse("Failed to follow", e.getMessage()));
        }



    }

    @DeleteMapping("/api/v1.0/user/followers")
    public ResponseEntity<?> unfollow(@RequestBody Map<String, List<String>> body) {
        try {
            if (!body.containsKey("unfollowed_people")) {
                return ResponseEntity.status(401).body(new ErrorResponse("No unfollowed people specified", "no unfollowed_people key in payload"));
            }
            List<String> unfollowedPeople = body.get("unfollowed_people");
            String username = oauthUtils.getUserNameFromAuth(SecurityContextHolder.getContext().getAuthentication());
            User currentUser = userRepository.findByUsername(username).get();
            unfollowedPeople.forEach(unfollowedUsername -> {
                if (userRepository.existsByUsername(unfollowedUsername)) {
                    User followedUser = userRepository.findByUsername(unfollowedUsername).get();
                    currentUser.getFollowed().remove(followedUser);
                }
            });
            userRepository.save(currentUser);
            return ResponseEntity.accepted().body("succeed");
        } catch (Exception e) {
            return ResponseEntity.status(401).body(new ErrorResponse("failed to unfollow", e.getMessage()));
        }
    }
}
