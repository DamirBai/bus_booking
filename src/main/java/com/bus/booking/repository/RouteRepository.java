package com.bus.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bus.booking.model.Route;

public interface RouteRepository extends JpaRepository<Route, Long> {}

