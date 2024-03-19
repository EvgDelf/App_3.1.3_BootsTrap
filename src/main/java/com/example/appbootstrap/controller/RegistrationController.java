package com.example.appbootstrap.controller;

import com.example.appbootstrap.model.Role;
import com.example.appbootstrap.model.User;
import com.example.appbootstrap.service.RoleService;
import com.example.appbootstrap.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class RegistrationController {

    private final UserService userService;

    private final RoleService roleService;

    @Autowired
    public RegistrationController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/registration")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/register")
    public String registration(@ModelAttribute("newUser") User user, @RequestParam("roles") List<Long> rolesId) {
        List<Role> roles = rolesId.stream().map(id->roleService.findById(id).orElse(null)).toList();
        user.setRoles(roles);
        userService.registerUser(user);
        return "redirect:/admin/";
    }


}
