package com.bus.booking.controller;

import com.bus.booking.model.request.UserNameRequest;
import com.bus.booking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody UserNameRequest userInfo) {
        var user = userService.updateUser(userInfo, userService.getAuthenticatedUser());
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @GetMapping("/authenticated")
    public ResponseEntity<?> getAuthenticatedUserId() {
        return ResponseEntity.ok(userService.getAuthenticatedUser().getId());
    }

    @RequestMapping(value = "/confirmemail", method = {RequestMethod.GET, RequestMethod.POST})
    private ResponseEntity<?> confirmEmail(@RequestParam("token") String token) {
        return userService.confirmEmail(token);
    }
}





