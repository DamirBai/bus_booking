package com.bus.booking.controller;

import com.bus.booking.model.Trip;
import com.bus.booking.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trips")
public class TripController {

    @Autowired
    private TripService tripService;

    @GetMapping
    public List<Trip> getAllTrips() {
        return tripService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trip> getTripById(@PathVariable Long id) {
        return tripService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Trip createTrip(@RequestBody Trip trip) {
        return tripService.save(trip);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Trip> updateTrip(@PathVariable Long id, @RequestBody Trip tripDetails) {
        return tripService.findById(id)
                .map(trip -> {
                    trip.setDepartureTime(tripDetails.getDepartureTime());
                    trip.setArrivalTime(tripDetails.getArrivalTime());
                    trip.setBus(tripDetails.getBus());
                    trip.setRoute(tripDetails.getRoute());
                    trip.setDriver(tripDetails.getDriver());
                    trip.setTickets(tripDetails.getTickets());
                    return ResponseEntity.ok(tripService.save(trip));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrip(@PathVariable Long id) {
        return tripService.findById(id)
                .map(trip -> {
                    tripService.deleteById(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}

