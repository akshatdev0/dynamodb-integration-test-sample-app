package com.breader.infrastructure.repository;

import com.breader.application.TicketRepository;
import com.breader.domain.Ticket;
import com.breader.domain.TicketId;

import java.util.Optional;

public class TicketRepositoryImpl implements TicketRepository {

    @Override
    public void save(Ticket ticket) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Ticket> findById(TicketId ticketId) {
        throw new UnsupportedOperationException();
    }
}
