package com.bus.booking.service;


import com.bus.booking.model.User;
import com.bus.booking.model.request.SignInRequest;
import com.bus.booking.model.request.SignUpRequest;
import com.bus.booking.model.response.JwtAuthenticationResponse;
import com.bus.booking.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationService{
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private final RefreshTokenRepository refreshTokenRepository;

    public JwtAuthenticationResponse signup(SignUpRequest request) {
        var user = User.builder()
                .firstName(request.getFirst_name())
                .lastName(request.getLast_name())
                .email(request.getEmail()).password(passwordEncoder.encode(request.getPassword())).isAdmin(isAdminn(request.getEmail()))
                .build();
        userService.saveUser(user);
        return JwtAuthenticationResponse.builder()
                .accessToken(jwtService.generateAccessToken(user))
                .refreshToken(jwtService.generateRefreshToken(user).getRefreshToken())
                .build();
    }

    public JwtAuthenticationResponse signin(SignInRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userService.findUserByEmail(request.getEmail());
        refreshTokenRepository.deleteById(user.getId());
        return JwtAuthenticationResponse.builder()
                .accessToken(jwtService.generateAccessToken(user))
                .refreshToken(jwtService.generateRefreshToken(user).getRefreshToken())
                .build();
    }

    public JwtAuthenticationResponse refresh(String refreshToken) {
        User user = refreshTokenRepository.findByRefreshToken(refreshToken).orElseThrow(
                () -> new NoSuchElementException("incorrect refresh token")).getUser();
        refreshTokenRepository.deleteByRefreshToken(refreshToken);
        return JwtAuthenticationResponse.builder()
                .accessToken(jwtService.generateAccessToken(user))
                .refreshToken(jwtService.generateRefreshToken(user).getRefreshToken())
                .build();
    }
    public boolean isAdminn(String email){
        return Objects.equals(email, "abiev.arystanbek@gmail.com");
    }
}