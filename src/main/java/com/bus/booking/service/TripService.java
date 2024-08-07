package com.bus.booking.service;

import com.bus.booking.model.Trip;
import com.bus.booking.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TripService {

    @Autowired
    private TripRepository tripRepository;

    public List<Trip> findAll() {
        return tripRepository.findAll();
    }

    public Optional<Trip> findById(Long id) {
        return tripRepository.findById(id);
    }

    public Trip save(Trip trip) {
        return tripRepository.save(trip);
    }

    public void deleteById(Long id) {
        tripRepository.deleteById(id);
    }

    public List<Trip> searchTrips(String startLocation, String endLocation, LocalDateTime departureTimeStart, LocalDateTime departureTimeEnd) {
        return tripRepository.findByRoute_StartLocationAndRoute_EndLocationAndDepartureTimeBetween(
                startLocation, endLocation, departureTimeStart, departureTimeEnd);
    }
}

