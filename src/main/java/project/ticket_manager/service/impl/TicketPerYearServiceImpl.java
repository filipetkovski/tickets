package project.ticket_manager.service.impl;

import org.springframework.stereotype.Service;
import project.ticket_manager.dto.TicketPerYearDto;
import project.ticket_manager.model.Ticket;
import project.ticket_manager.model.TicketPerMonth;
import project.ticket_manager.model.TicketPerYear;
import project.ticket_manager.repository.TicketPerYearRepository;
import project.ticket_manager.service.TicketPerYearService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static project.ticket_manager.mapping.TicketPerYearMapping.mapToTicketPerDayDto;

@Service
public class TicketPerYearServiceImpl implements TicketPerYearService {
    TicketPerYearRepository ticketPerYearRepository;

    public TicketPerYearServiceImpl(TicketPerYearRepository ticketPerYearRepository) {
        this.ticketPerYearRepository = ticketPerYearRepository;
    }

    @Override
    public void updateDatabase(Ticket ticket) {
        TicketPerYear ticketPerYear = ticketPerYearRepository.findByYear(ticket.getCreatedOn());
        if(ticketPerYear != null) {
            ticketPerYear.setPrice(ticketPerYear.getPrice() + ticket.getPrice());
            ticketPerYear.setNumberOfPeople(ticketPerYear.getNumberOfPeople() + ticket.getNumberOfPeople());
            ticketPerYearRepository.save(ticketPerYear);
        } else {
            TicketPerYear ticketPerYearNew = new TicketPerYear();
            ticketPerYearNew.setPrice(ticket.getPrice());
            ticketPerYearNew.setNumberOfPeople(ticket.getNumberOfPeople());
            ticketPerYearRepository.save(ticketPerYearNew);
        }
    }

    @Override
    public void deleteById(Long ticketId) {
        ticketPerYearRepository.deleteById(ticketId);
    }

    @Override
    public TicketPerYear getTicket(Long ticketId) {
        return ticketPerYearRepository.getById(ticketId);
    }

    @Override
    public List<TicketPerYearDto> findAll() {
        List<TicketPerYear> ticketPerYears = ticketPerYearRepository.findAll();
        return ticketPerYears.stream().map((ticketPerYear -> mapToTicketPerDayDto(ticketPerYear))).collect(Collectors.toList());
    }

    @Override
    public void changeTicket(LocalDateTime createdOn, Integer numberOfPeople, Integer price) {
        TicketPerYear ticketPerYear = ticketPerYearRepository.findByYear(createdOn);
        ticketPerYear.setNumberOfPeople(ticketPerYear.getNumberOfPeople() - numberOfPeople);
        int number = ticketPerYear.getPrice() - price;
        if(number > 0) {
            ticketPerYear.setPrice(ticketPerYear.getPrice() - price);
            ticketPerYearRepository.save(ticketPerYear);
        } else {
            ticketPerYearRepository.delete(ticketPerYear);
        }
    }
}
