package com.bus.booking.controller;

import com.bus.booking.model.Bus;
import com.bus.booking.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/buses")
public class BusController {

    @Autowired
    private BusService busService;

    @GetMapping
    public List<Bus> getAllBuses() {
        return busService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bus> getBusById(@PathVariable Long id) {
        return busService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Bus createBus(@RequestBody Bus bus) {
        return busService.save(bus);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bus> updateBus(@PathVariable Long id, @RequestBody Bus busDetails) {
        return busService.findById(id)
                .map(bus -> {
                    bus.setModel(busDetails.getModel());
                    bus.setCapacity(busDetails.getCapacity());
                    bus.setLicensePlate(busDetails.getLicensePlate());
                    bus.setTrips(busDetails.getTrips());
                    return ResponseEntity.ok(busService.save(bus));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBus(@PathVariable Long id) {
        return busService.findById(id)
                .map(bus -> {
                    busService.deleteById(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
