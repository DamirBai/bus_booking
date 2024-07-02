package com.bus.booking.controller;

import com.bus.booking.model.Ticket;
import com.bus.booking.model.Trip;
import com.bus.booking.service.TicketService;
import com.bus.booking.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private TripService tripService;

    @GetMapping
    public List<Ticket> getAllTickets() {
        return ticketService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable Long id) {
        return ticketService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Ticket createTicket(@RequestBody Ticket ticket) {
        return ticketService.save(ticket);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ticket> updateTicket(@PathVariable Long id, @RequestBody Ticket ticketDetails) {
        return ticketService.findById(id)
                .map(ticket -> {
                    ticket.setTrip(ticketDetails.getTrip());
                    ticket.setUser(ticketDetails.getUser());
                    ticket.setSeatNumber(ticketDetails.getSeatNumber());
                    ticket.setPayment(ticketDetails.getPayment());
                    ticket.setStatus(ticketDetails.getStatus());
                    return ResponseEntity.ok(ticketService.save(ticket));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        return ticketService.findById(id)
                .map(ticket -> {
                    ticketService.deleteById(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/book")
    public ResponseEntity<String> bookTicket(@RequestParam Long tripId, @RequestParam int seatNumber) {
        Optional<Trip> tripOptional = tripService.findById(tripId);

        if (tripOptional.isPresent()) {
            Trip trip = tripOptional.get();
            int busCapacity = trip.getBus().getCapacity();
            if (seatNumber > 0 && seatNumber <= busCapacity) {
                Optional<Ticket> ticketOptional = ticketService.findByTripAndSeatNumber(tripId, seatNumber);
                if (ticketOptional.isPresent()) {
                    Ticket ticket = ticketOptional.get();
                    if ("free".equals(ticket.getStatus())) {
                        ticket.setStatus("busy");
                        ticket.setTrip(trip);
                        ticketService.save(ticket);

                        if (!trip.getTickets().contains(ticket)) {
                            trip.getTickets().add(ticket);
                            tripService.save(trip);
                        }

                        return ResponseEntity.ok("Ticket booked successfully!");
                    } else {
                        return ResponseEntity.status(400).body("Seat is already booked");
                    }
                } else {
                    return ResponseEntity.status(404).body("Ticket not found");
                }
            } else {
                return ResponseEntity.status(400).body("Invalid seat number");
            }
        } else {
            return ResponseEntity.status(404).body("Trip not found");
        }
    }
}
