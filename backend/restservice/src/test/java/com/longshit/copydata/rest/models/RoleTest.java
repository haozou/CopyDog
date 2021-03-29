package com.longshit.copydata.rest.models;


import com.longshit.copydog.rest.models.ERole;
import com.longshit.copydog.rest.models.Role;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;

public class RoleTest {
    @Test
    public void testRole() {
        Role role1 = new Role(ERole.ROLE_USER);
        Role role2 = new Role(ERole.ROLE_USER);
        Set<Role> roles = new HashSet<>();
        roles.add(role1);
        roles.add(role2);
        Assert.assertEquals(roles.size(), 1);
    }
}
