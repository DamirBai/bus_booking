package com.bus.booking.service;

import com.bus.booking.model.Route;
import com.bus.booking.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RouteService {

    @Autowired
    private RouteRepository routeRepository;

    public List<Route> findAll() {
        return routeRepository.findAll();
    }

    public Optional<Route> findById(Long id) {
        return routeRepository.findById(id);
    }

    public Route save(Route route) {
        return routeRepository.save(route);
    }

    public void deleteById(Long id) {
        routeRepository.deleteById(id);
    }
}

