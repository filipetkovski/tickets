package project.ticket_manager.service;

import project.ticket_manager.dto.TicketPerYearDto;
import project.ticket_manager.model.Ticket;
import project.ticket_manager.model.TicketPerYear;

import java.time.LocalDateTime;
import java.util.List;

public interface TicketPerYearService {
    void updateDatabase(Ticket ticket);

    void deleteById(Long ticketId);

    TicketPerYear getTicket(Long ticketId);

    List<TicketPerYearDto> findAll();

    void changeTicket(LocalDateTime createdOn, Integer numberOfPeople, Integer price);
}
