package project.ticket_manager.service;

import project.ticket_manager.dto.TicketDto;
import project.ticket_manager.model.Ticket;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TicketService {
    public List<TicketDto> getDailyTickets(LocalDateTime date);

    void save(Ticket ticket);

    void deleteWithId(Long ticketId);

    List<TicketDto> findAll();

    void deleteByDate(LocalDateTime updatedOn);

    void deleteByMonth(LocalDateTime createdOn);

    void deleteByYear(LocalDateTime createdOn);

    Ticket getById(Long ticketId);

    Ticket findByCode(String code);
}
