package project.ticket_manager.service;

import project.ticket_manager.dto.TicketPerDayDto;
import project.ticket_manager.dto.TicketPerMonthDto;
import project.ticket_manager.model.Ticket;
import project.ticket_manager.model.TicketPerDay;
import project.ticket_manager.model.TicketPerMonth;

import java.time.LocalDateTime;
import java.util.List;

public interface TicketPerMonthService {
    void updateDatabase(Ticket ticket);

    List<TicketPerMonthDto> findAll();

    TicketPerMonth getTicket(Long ticketId);

    void deleteById(Long ticketId);

    void deleteByYear(LocalDateTime createdOn);

    void changeTicket(LocalDateTime createdOn, Integer numberOfPeople, Integer price);
}
