package com.bus.booking.service;

import com.bus.booking.model.Bus;
import com.bus.booking.repository.BusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BusService {

    @Autowired
    private BusRepository busRepository;

    public List<Bus> findAll() {
        return busRepository.findAll();
    }

    public Optional<Bus> findById(Long id) {
        return busRepository.findById(id);
    }

    public Bus save(Bus bus) {
        return busRepository.save(bus);
    }

    public void deleteById(Long id) {
        busRepository.deleteById(id);
    }
}
