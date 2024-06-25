package com.bus.booking.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String email;
    private String password;
    private boolean isAdmin;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Ticket> tickets;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Notification> notifications;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return fullName; }
    public void setUsername(String fullName) { this.fullName = fullName; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public boolean getIsAdmin() { return isAdmin; }
    public void setIsAdmin(boolean isAdmin) { this.isAdmin = isAdmin; }

    public Set<Ticket> getTickets() { return tickets; }
    public void setTickets(Set<Ticket> tickets) { this.tickets = tickets; }

    public Set<Notification> getNotifications() { return notifications; }
    public void setNotifications(Set<Notification> notifications) { this.notifications = notifications; }
}

