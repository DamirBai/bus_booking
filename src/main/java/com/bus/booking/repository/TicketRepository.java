package com.bus.booking.repository;

import com.bus.booking.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
