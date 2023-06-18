package com.breader.application;

import com.breader.domain.Ticket;
import com.breader.domain.TicketId;
import com.breader.infrastructure.web.command.CreateTicketCommand;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;

    public TicketId handle(CreateTicketCommand createTicketCommand) {
        TicketId newTicketId = TicketId.create();
        Ticket ticket = new Ticket(newTicketId, createTicketCommand.eventName());
        ticketRepository.save(ticket);

        return newTicketId;
    }
}
