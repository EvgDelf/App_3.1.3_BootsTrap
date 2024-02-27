package com.example.appbootstrap.controller;


import com.example.appbootstrap.model.User;
import com.example.appbootstrap.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String findAll(Model model, Authentication authentication) {
        List<User> usersList = userService.findAll();
        User adminData = usersList.get(0);
        usersList.remove(0);
        model.addAttribute("users", usersList);
        model.addAttribute("user", new User());
        model.addAttribute("adminData", adminData);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("role", user.getRole());
        return "users";
    }

    @GetMapping("/admin/user")
    public String findUserById(@RequestParam("id") Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "user";
    }

    @PostMapping("/admin/user-delete")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }

    @GetMapping("/admin/editForm")
    public String editForm(@RequestParam("id") Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "edit";
    }

    @PostMapping("/admin/edit")
    public String editUser(@ModelAttribute("userEdit") User user) {
        userService.edit(user);
        return "redirect:/admin";
    }


}
