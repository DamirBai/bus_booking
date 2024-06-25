package com.bus.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bus.booking.model.User;

public interface UserRepository extends JpaRepository<User, Long> {}
