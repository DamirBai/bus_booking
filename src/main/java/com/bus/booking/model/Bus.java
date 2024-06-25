package com.bus.booking.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
public class Bus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String licensePlate;
    private String model;
    private int capacity;

    @OneToMany(mappedBy = "bus", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Trip> trips;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getLicensePlate() { return licensePlate; }
    public void setLicensePlate(String licensePlate) { this.licensePlate = licensePlate; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public Set<Trip> getTrips() { return trips; }
    public void setTrips(Set<Trip> trips) { this.trips = trips; }
}

