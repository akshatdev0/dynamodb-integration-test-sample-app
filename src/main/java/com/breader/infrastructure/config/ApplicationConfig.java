package com.breader.infrastructure.config;

import com.breader.application.TicketRepository;
import com.breader.application.TicketService;
import com.breader.infrastructure.repository.TicketRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class ApplicationConfig {

    @Bean
    public TicketRepository ticketRepository() {
        return new TicketRepositoryImpl();
    }

    @Bean
    public TicketService ticketService(TicketRepository ticketRepository) {
        return new TicketService(ticketRepository);
    }
}
