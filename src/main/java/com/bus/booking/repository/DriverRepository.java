package com.bus.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bus.booking.model.Driver;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
}
