package com.bus.booking.service;

import com.bus.booking.model.Trip;
import com.bus.booking.repository.TripRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TripService {
    @Autowired
    private TripRepository tripRepository;

    public List<Trip> searchTrips(String startLocation, String endLocation, LocalDateTime startTime, LocalDateTime endTime) {
        return tripRepository.findByRoute_StartLocationAndRoute_EndLocationAndDepartureTimeBetween(startLocation, endLocation, startTime, endTime);
    }

    public Trip getTripDetails(Long tripId) {
        return tripRepository.findById(tripId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trip not found"));
    }
}
