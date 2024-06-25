package com.bus.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bus.booking.model.Bus;

public interface BusRepository extends JpaRepository<Bus, Long> {}

