package com.bus.booking.controller;

import com.bus.booking.model.User;
import com.bus.booking.model.UserInfo;
import com.bus.booking.service.UserService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class UserController {

    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }
    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody UserInfo userInfo) {
        var user = userService.updateUser(userInfo, userService.getAuthenticatedUser());
        return ResponseEntity.ok(user);
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
    @GetMapping("/authenticated")
    public ResponseEntity<?> getAuthenticatedUserId() {
        return ResponseEntity.ok(userService.getAuthenticatedUser().getId());
    }
    @RequestMapping(value = "/confirmemail", method = {RequestMethod.GET, RequestMethod.POST})
    private ResponseEntity<?> confirmEmail(@RequestParam("token") String token) {
        return userService.confirmEmail(token);
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.save(user);
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
