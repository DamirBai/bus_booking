package com.bus.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bus.booking.model.Driver;

public interface DriverRepository extends JpaRepository<Driver, Long> {}

