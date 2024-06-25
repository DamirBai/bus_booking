package com.bus.booking.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String startLocation;
    private String endLocation;
    private String duration;

    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Trip> trips;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getStartLocation() { return startLocation; }
    public void setStartLocation(String startLocation) { this.startLocation = startLocation; }

    public String getEndLocation() { return endLocation; }
    public void setEndLocation(String endLocation) { this.endLocation = endLocation; }

    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }

    public Set<Trip> getTrips() { return trips; }
    public void setTrips(Set<Trip> trips) { this.trips = trips; }
}
