package com.bus.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bus.booking.model.Trip;

import java.util.List;
import java.time.LocalDateTime;

public interface TripRepository extends JpaRepository<Trip, Long> {
    List<Trip> findByRoute_StartLocationAndRoute_EndLocationAndDepartureTimeBetween(
            String startLocation, String endLocation, LocalDateTime startTime, LocalDateTime endTime);
}

