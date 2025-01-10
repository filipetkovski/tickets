package project.ticket_manager.service.impl;

import org.springframework.stereotype.Service;
import project.ticket_manager.dto.TicketPerDayDto;
import project.ticket_manager.dto.TicketPerMonthDto;
import project.ticket_manager.model.Ticket;
import project.ticket_manager.model.TicketPerDay;
import project.ticket_manager.model.TicketPerMonth;
import project.ticket_manager.repository.TicketPerMonthRepository;
import project.ticket_manager.service.TicketPerMonthService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static project.ticket_manager.mapping.TicketPerMonthMapping.mapToTicketPerMonthDto;

@Service
public class TicketPerMonthServiceImpl implements TicketPerMonthService {
    TicketPerMonthRepository ticketPerMonthRepository;

    public TicketPerMonthServiceImpl(TicketPerMonthRepository ticketPerMonthRepository) {
        this.ticketPerMonthRepository = ticketPerMonthRepository;
    }

    @Override
    public void updateDatabase(Ticket ticket) {
        TicketPerMonth ticketPerMonth = ticketPerMonthRepository.findByCreatedOn(ticket.getCreatedOn());
        if(ticketPerMonth != null) {
            ticketPerMonth.setPrice(ticketPerMonth.getPrice() + ticket.getPrice());
            ticketPerMonth.setNumberOfPeople(ticketPerMonth.getNumberOfPeople() + ticket.getNumberOfPeople());
            ticketPerMonthRepository.save(ticketPerMonth);
        } else {
            TicketPerMonth ticketPerMonthNew = new TicketPerMonth();
            ticketPerMonthNew.setPrice(ticket.getPrice());
            ticketPerMonthNew.setNumberOfPeople(ticket.getNumberOfPeople());
            ticketPerMonthRepository.save(ticketPerMonthNew);
        }
    }

    @Override
    public List<TicketPerMonthDto> findAll() {
        List<TicketPerMonth> ticketPerMonths = ticketPerMonthRepository.findAll();
        return ticketPerMonths.stream().map((ticketPerMonth) -> mapToTicketPerMonthDto(ticketPerMonth)).collect(Collectors.toList());
    }

    @Override
    public TicketPerMonth getTicket(Long ticketId) {

        return ticketPerMonthRepository.getById(ticketId);
    }

    @Override
    public void deleteById(Long ticketId) {
        ticketPerMonthRepository.deleteById(ticketId);
    }

    @Override
    public void deleteByYear(LocalDateTime createdOn) {
        TicketPerMonth ticket = ticketPerMonthRepository.findByYear(createdOn);
        ticketPerMonthRepository.deleteById(ticket.getId());
    }

    @Override
    public void changeTicket(LocalDateTime createdOn, Integer numberOfPeople, Integer price) {
        TicketPerMonth ticketPerMonth = ticketPerMonthRepository.findByCreatedOn(createdOn);
        ticketPerMonth.setNumberOfPeople(ticketPerMonth.getNumberOfPeople() - numberOfPeople);
        int number = ticketPerMonth.getPrice() - price;
        if(number > 0) {
            ticketPerMonth.setPrice(ticketPerMonth.getPrice() - price);
            ticketPerMonthRepository.save(ticketPerMonth);
        } else {
            ticketPerMonthRepository.delete(ticketPerMonth);
        }
    }
}
