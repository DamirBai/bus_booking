package com.bus.booking.controller;

import com.bus.booking.model.Ticket;
import com.bus.booking.model.Trip;
import com.bus.booking.service.TicketService;
import com.bus.booking.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping("{trip_id}/bookTicket/{ticket_id}")
    public ResponseEntity<String> bookTicket(@PathVariable Long trip_id, @PathVariable Long ticket_id) {
        Trip trip = tripService.findById(trip_id).orElse(null);
        Ticket ticket = ticketService.findById(ticket_id).orElse(null);

        if (trip == null || ticket == null) {
            return ResponseEntity.badRequest().body("Trip or Ticket does not exist!");
        }
        if (ticket.getTrip() != null) {
            return ResponseEntity.badRequest().body("Ticket is already booked!");
        }

        ticket.setTrip(trip);
        ticketService.save(ticket);

        if (!trip.getTickets().contains(ticket)) {
            trip.getTickets().add(ticket);
            tripService.save(trip);
        }

        return ResponseEntity.ok("Ticket booked successfully!");
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
}