package com.example.appbootstrap.controller;


import com.example.appbootstrap.model.Role;
import com.example.appbootstrap.model.User;
import com.example.appbootstrap.service.RoleService;
import com.example.appbootstrap.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/")
    public String findAll(Model model, Authentication authentication) {
        List<User> usersList = userService.findAll();
        User adminData = usersList.get(0);
        usersList.remove(0);
        model.addAttribute("users", usersList);
        model.addAttribute("user", new User());
        model.addAttribute("adminData", adminData);
        model.addAttribute("email", adminData.getEmail());
        model.addAttribute("adminRoles", roleService.getRolesNames(adminData.getRoles()));
        return "users";
    }

    @GetMapping("/user")
    public String findUserById(@RequestParam("id") Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "user";
    }

    @PostMapping("/delete")
    public String deleteUser(@ModelAttribute("user") User deleteduser) {
        userService.deleteById(deleteduser.getId());
        return "redirect:/admin/";
    }

    @PostMapping("/edit")
    public String editUser(@ModelAttribute("user") User updatedUser, @RequestParam("Long") List<Long> rolesId) {
        User existingUser = userService.findById(updatedUser.getId());
        List<Role> roles = rolesId.stream().map(id->roleService.findById(id).orElse(null)).collect(Collectors.toList());
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setAge(updatedUser.getAge());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setRoles(roles);
        userService.edit(existingUser);

        return "redirect:/admin/";
    }


}
