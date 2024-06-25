package com.bus.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bus.booking.model.Ticket;
import com.bus.booking.model.Trip;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByTripAndSeatStatus(Trip trip, boolean seatStatus);
}
