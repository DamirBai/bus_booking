package com.bus.booking.controller;

import com.bus.booking.model.User;
import com.bus.booking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/list")
    public String userList(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "user-list"; // This corresponds to user-list.html in templates directory
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.save(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        return userService.findById(id)
                .map(user -> {
                    user.setFullName(userDetails.getFullName());
                    user.setPassword(userDetails.getPassword());
                    user.setEmail(userDetails.getEmail());
                    user.setIsAdmin(userDetails.getIsAdmin());
                    user.setRoles(userDetails.getRoles());
                    user.setTickets(userDetails.getTickets());
                    user.setNotification(userDetails.getNotification());
                    return ResponseEntity.ok(userService.save(user));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        return userService.findById(id)
                .map(user -> {
                    userService.deleteById(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/profile")
    public String viewProfile(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Optional<User> user = userService.findByEmail(email);
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            return "profile"; // This corresponds to profile.html in templates directory
        } else {
            return "error"; // Or another appropriate error page
        }
    }

    @PostMapping("/profile")
    public String updateProfile(@ModelAttribute User user, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Optional<User> existingUser = userService.findByEmail(email);
        if (existingUser.isPresent()) {
            User updatedUser = existingUser.get();
            updatedUser.setFullName(user.getFullName());
            updatedUser.setPassword(user.getPassword());
            updatedUser.setEmail(user.getEmail());
            userService.save(updatedUser);
            model.addAttribute("user", updatedUser);
            return "profile"; // Redirect to profile page after update
        } else {
            return "error"; // Or another appropriate error page
        }
    }
}
