package com.breader.application;

import com.breader.domain.Ticket;
import com.breader.domain.TicketId;

import java.util.Optional;

public interface TicketRepository {

    void save(Ticket ticket);

    Optional<Ticket> findById(TicketId ticketId);

}
