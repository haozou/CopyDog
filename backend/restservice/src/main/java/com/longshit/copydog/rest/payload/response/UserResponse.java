package com.longshit.copydog.rest.payload.response;

import com.longshit.copydog.rest.models.User;

import java.util.Set;

public class UserResponse {
    protected User user;
    protected Set<User> followedUsers;
    protected Set<User> followers;

    public UserResponse() {

    }

    public UserResponse(User user, Set<User> followedUsers, Set<User> followers) {
        this.user = user;
        this.followedUsers = followedUsers;
        this.followers = followers;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<User> getFollowedUsers() {
        return followedUsers;
    }

    public void setFollowedUsers(Set<User> followedUsers) {
        this.followedUsers = followedUsers;
    }

    public Set<User> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<User> followers) {
        this.followers = followers;
    }
}
