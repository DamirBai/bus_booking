package com.bus.booking.service;

import com.bus.booking.model.User;
import com.bus.booking.model.UserInfo;
import com.bus.booking.repository.ConfirmationTokenRepository;
import com.bus.booking.repository.UserRepository;
import org.hibernate.NonUniqueObjectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final ConfirmationTokenService confirmationTokenService;

    public UserService(UserRepository userRepository, ConfirmationTokenRepository confirmationTokenRepository, ConfirmationTokenService confirmationTokenService) {
        this.userRepository = userRepository;
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.confirmationTokenService = confirmationTokenService;
    }


    public UserDetailsService userDetailsService() {
        return username -> userRepository.findUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User getAuthenticatedUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public User updateUser(UserInfo userInfo, User user) {
        userInfo.setId(user.getId());
        user.setUserInfo(userInfo);
        userRepository.save(user);
        return user;
    }

    public ResponseEntity<?> confirmEmail(String token) {
        var confirmationToken = confirmationTokenRepository.findConfirmationTokenByConfirmationToken(token);
        User user = confirmationToken.orElseThrow().getUser();
        if (user.isEnabled()) return ResponseEntity.ok("email already verified");
        user.setEnabled(true);
        userRepository.save(user);
        return ResponseEntity.ok("email successfully verified");
    }

    public User saveUser(User user) {
        if(userRepository.existsByEmail(user.getEmail()))
            throw new NonUniqueObjectException("", null, user.getEmail());
        userRepository.save(user);
        confirmationTokenService.sendConfirmationToken(user);
        return user;
    }

    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(()
                -> new NoSuchElementException(String.format("User with id '%d' not found", id)));
    }

    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(()
                -> new NoSuchElementException(String.format("User with email '%d' not found", email)));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }


    public User save(User user) {
        return userRepository.save(user);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
