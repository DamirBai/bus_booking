package com.bus.booking.config;

import com.bus.booking.model.*;
import com.bus.booking.repository.BusRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.parameters.P;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

@Configuration
public class BusConfig {

    @Bean
    CommandLineRunner commandLineRunner(BusRepository busRepository) {
        return args -> {
        };
    }
}
