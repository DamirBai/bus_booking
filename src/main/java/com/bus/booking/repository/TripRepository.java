package com.bus.booking.repository;

import com.bus.booking.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    List<Trip> findByRoute_StartLocationAndRoute_EndLocationAndDepartureTimeBetween(
            String startLocation, String endLocation,
            LocalDateTime departureTimeStart, LocalDateTime departureTimeEnd);
}
