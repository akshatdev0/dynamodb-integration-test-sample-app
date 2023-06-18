package com.breader.infrastructure.web.model;

import com.breader.domain.Ticket;

import java.util.UUID;

public record TicketDto(UUID ticketId, String eventName) {

    public static TicketDto fromTicket(Ticket ticket) {
        return new TicketDto(ticket.ticketId().value(), ticket.eventName());
    }
}
