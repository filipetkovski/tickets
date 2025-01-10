package project.ticket_manager.service.impl;

import org.springframework.stereotype.Service;
import project.ticket_manager.dto.TicketPerDayDto;
import project.ticket_manager.model.Ticket;
import project.ticket_manager.model.TicketPerDay;
import project.ticket_manager.repository.TicketPerDayRepository;
import project.ticket_manager.service.TicketPerDayService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static project.ticket_manager.mapping.TicketPerDayMapping.mapToTicketPerDayDto;

@Service
public class TicketPerDayServiceImpl implements TicketPerDayService {
    TicketPerDayRepository ticketPerDayRepository;

    public TicketPerDayServiceImpl(TicketPerDayRepository ticketPerDayRepository) {
        this.ticketPerDayRepository = ticketPerDayRepository;
    }

    @Override
    public void updateDatabase(Ticket ticket) {
        TicketPerDay ticketPerDay = ticketPerDayRepository.findByCreatedOn(ticket.getCreatedOn());
        if(ticketPerDay != null) {
            ticketPerDay.setPrice(ticketPerDay.getPrice() + ticket.getPrice());
            ticketPerDay.setNumberOfPeople(ticketPerDay.getNumberOfPeople() + ticket.getNumberOfPeople());
            ticketPerDayRepository.save(ticketPerDay);
        } else {
            TicketPerDay ticketPerDayNew = new TicketPerDay();
            ticketPerDayNew.setPrice(ticket.getPrice());
            ticketPerDayNew.setNumberOfPeople(ticket.getNumberOfPeople());
            ticketPerDayRepository.save(ticketPerDayNew);
        }
    }

    @Override
    public List<TicketPerDayDto> findAll() {
        List<TicketPerDay> ticketPerDays = ticketPerDayRepository.findAll();
        return ticketPerDays.stream().map((ticketPerDay) -> mapToTicketPerDayDto(ticketPerDay)).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long ticketId) {

        ticketPerDayRepository.deleteById(ticketId);
    }

    @Override
    public TicketPerDay getTicket(Long ticketId) {

        return ticketPerDayRepository.getById(ticketId);
    }

    @Override
    public void deleteByMonth(LocalDateTime createdOn) {
        TicketPerDay ticket = ticketPerDayRepository.findByMonth(createdOn);
        ticketPerDayRepository.deleteById(ticket.getId());

    }

    @Override
    public void deleteByYear(LocalDateTime createdOn) {
        TicketPerDay ticket = ticketPerDayRepository.findByYear(createdOn);
        ticketPerDayRepository.deleteById(ticket.getId());
    }

    @Override
    public void changeTicket(LocalDateTime date, Integer numberOfPeople, Integer price) {
        TicketPerDay ticketPerDay = ticketPerDayRepository.findByCreatedOn(date);
        ticketPerDay.setNumberOfPeople(ticketPerDay.getNumberOfPeople() - numberOfPeople);
        int number = ticketPerDay.getPrice() - price;
        if(number > 0) {
            ticketPerDay.setPrice(ticketPerDay.getPrice() - price);
            ticketPerDayRepository.save(ticketPerDay);
        } else {
            ticketPerDayRepository.delete(ticketPerDay);
        }
    }


}
