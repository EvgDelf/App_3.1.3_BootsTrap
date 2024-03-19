package com.example.appbootstrap.configs;

import com.example.appbootstrap.service.RoleService;
import com.example.appbootstrap.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SuccessHandler implements AuthenticationSuccessHandler {


    private UserService userService;

    private RoleService roleService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {

        UserDetails userDetails = userService.loadUserByUsername(authentication.getName());

        if (userDetails.getAuthorities().contains(roleService.findByName("ROLE_ADMIN"))) {
            response.sendRedirect("/admin/");
            return;
        }
        if (userDetails.getAuthorities().contains(roleService.findByName("ROLE_USER"))) {
            response.sendRedirect("/user");
            return;
        }

    }
}
