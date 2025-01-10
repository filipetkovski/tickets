package project.ticket_manager.service;

import project.ticket_manager.dto.TicketPerDayDto;
import project.ticket_manager.model.Ticket;
import project.ticket_manager.model.TicketPerDay;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TicketPerDayService {
    void updateDatabase(Ticket ticket);

    List<TicketPerDayDto> findAll();

    void deleteById(Long ticketId);

    TicketPerDay getTicket(Long ticketId);

    void deleteByMonth(LocalDateTime createdOn);

    void deleteByYear(LocalDateTime createdOn);

    void changeTicket(LocalDateTime date, Integer numberOfPeople, Integer price);
}
