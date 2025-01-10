package project.ticket_manager.service.impl;

import org.springframework.stereotype.Service;
import project.ticket_manager.dto.TicketDto;
import project.ticket_manager.mapping.TicketMapping;
import project.ticket_manager.model.Ticket;
import project.ticket_manager.model.UserEntity;
import project.ticket_manager.repository.TicketRepository;
import project.ticket_manager.repository.UserRepository;
import project.ticket_manager.security.SecurityUtil;
import project.ticket_manager.service.TicketService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static project.ticket_manager.mapping.TicketMapping.mapToTicketDto;


@Service
public class TicketServiceImpl implements TicketService {
    TicketRepository ticketRepository;
    UserRepository userRepository;

    public TicketServiceImpl(TicketRepository ticketRepository, UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<TicketDto> getDailyTickets(LocalDateTime date) {
        List<Ticket> tickets = ticketRepository.findByCreatedOn(date);
        return tickets.stream().map((ticket) -> mapToTicketDto(ticket)).collect(Collectors.toList());
    }

    @Override
    public void save(Ticket ticket) {
        LocalDateTime localDateTime = LocalDateTime.now();
        String code = localDateTime.getYear() + "" + localDateTime.getDayOfMonth() + "" + localDateTime.getHour() + "" + localDateTime.getMinute() + "" + localDateTime.getSecond();
        while(code.length() != 12) {
            code += "0";
        }
        ticket.setCode(code);
        ticketRepository.save(ticket);
    }

    @Override
    public void deleteWithId(Long ticketId) {
        ticketRepository.deleteById(ticketId);
    }

    @Override
    public List<TicketDto> findAll() {
        List<Ticket> tickets = ticketRepository.findAll();
        return tickets.stream().map((ticket) -> mapToTicketDto(ticket)).collect(Collectors.toList());
    }

    @Override
    public void deleteByDate(LocalDateTime createdOn) {
        List<Ticket> tickets = ticketRepository.findByCreatedOn(createdOn);
        tickets.stream().forEach(ticket -> ticketRepository.deleteById(ticket.getId()));
    }

    @Override
    public void deleteByMonth(LocalDateTime createdOn) {
        List<Ticket> tickets = ticketRepository.findByMonth(createdOn);
        tickets.stream().forEach(ticket -> ticketRepository.deleteById(ticket.getId()));
    }

    @Override
    public void deleteByYear(LocalDateTime createdOn) {
        List<Ticket> tickets = ticketRepository.findByYear(createdOn);
        tickets.stream().forEach(ticket -> ticketRepository.deleteById(ticket.getId()));

    }

    @Override
    public Ticket getById(Long ticketId) {
        return ticketRepository.getById(ticketId);
    }

    @Override
    public Ticket findByCode(String code) {
        return ticketRepository.findByCode(code);
    }
}
