package com.bus.booking.service;

import com.bus.booking.model.Ticket;
import com.bus.booking.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    public Optional<Ticket> findById(Long id) {
        return ticketRepository.findById(id);
    }

    public Ticket save(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public void deleteById(Long id) {
        ticketRepository.deleteById(id);
    }

    public Optional<Ticket> findByTripAndSeatNumber(Long tripId, int seatNumber) {
        return ticketRepository.findByTripIdAndSeatNumber(tripId, seatNumber);
    }
}
