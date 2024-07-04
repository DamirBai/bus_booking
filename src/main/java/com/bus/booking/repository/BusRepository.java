package com.bus.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bus.booking.model.Bus;

@Repository
public interface BusRepository extends JpaRepository<Bus, Long> {
}
