package com.bus.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bus.booking.model.Trip;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
}
