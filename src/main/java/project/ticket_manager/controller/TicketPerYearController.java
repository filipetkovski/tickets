package project.ticket_manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import project.ticket_manager.dto.TicketPerMonthDto;
import project.ticket_manager.dto.TicketPerYearDto;
import project.ticket_manager.model.*;
import project.ticket_manager.security.SecurityUtil;
import project.ticket_manager.service.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class TicketPerYearController {
    TicketPerYearService ticketPerYearService;
    TicketPerMonthService ticketPerMonthService;
    TicketService ticketService;
    TicketPerDayService ticketPerDayService;
    UserService userService;

    public TicketPerYearController(TicketPerYearService ticketPerYearService, TicketPerMonthService ticketPerMonthService, TicketService ticketService, TicketPerDayService ticketPerDayService, UserService userService) {
        this.ticketPerYearService = ticketPerYearService;
        this.ticketPerMonthService = ticketPerMonthService;
        this.ticketService = ticketService;
        this.ticketPerDayService = ticketPerDayService;
        this.userService = userService;
    }

    @GetMapping("/peryear/tickets")
    public String getPerYear(Model model) {
        UserEntity user = new UserEntity();
        List<TicketPerYearDto> tickets = ticketPerYearService.findAll();
        String username = SecurityUtil.getSessionUser();
        if(username != null) {
            user = userService.findByEmail(username);
            model.addAttribute("user", user);
        }
        model.addAttribute("user", user);
        model.addAttribute("tickets", tickets);
        return "peryear_tickets";
    }

    @GetMapping("/ticket/create/peryear")
    public String updatePerMonth(HttpSession session) {
        Ticket ticket = (Ticket) session.getAttribute("ticket");
        session.setAttribute("ticket", ticket);
        ticketPerYearService.updateDatabase(ticket);
        return "redirect:/";
    }

    @GetMapping("/peryear/ticket/{ticketId}/delete")
    public String deleteTicketPerYear(@PathVariable("ticketId") Long ticketId) {
        TicketPerYear ticketPerYear = ticketPerYearService.getTicket(ticketId);
        ticketService.deleteByYear(ticketPerYear.getCreatedOn());
        ticketPerDayService.deleteByYear(ticketPerYear.getCreatedOn());
        ticketPerMonthService.deleteByYear(ticketPerYear.getCreatedOn());
        ticketPerYearService.deleteById(ticketId);
        return "redirect:/peryear/tickets";
    }

    @GetMapping("/ticket/{ticketId}/delete/peryear")
    public String deleteTicket(HttpSession session) {
        Ticket ticket = (Ticket) session.getAttribute("ticket");
        session.setAttribute("ticket", ticket);
        ticketPerYearService.changeTicket(ticket.getCreatedOn(), ticket.getNumberOfPeople(), ticket.getPrice());
        return "redirect:/daily/tickets";
    }

    @GetMapping("/perday/ticket/{ticketId}/delete/peryear")
    public String deleteTicketPerDay(HttpSession session) {
        TicketPerDay ticketPerDay = (TicketPerDay) session.getAttribute("ticket");
        session.setAttribute("ticket", ticketPerDay);
        ticketPerYearService.changeTicket(ticketPerDay.getCreatedOn(), ticketPerDay.getNumberOfPeople(), ticketPerDay.getPrice());
        return "redirect:/perday/tickets";
    }

    @GetMapping("/permonth/ticket/{ticketId}/delete/peryear")
    public String deleteTicketPerMonth(HttpSession session) {
        TicketPerMonth ticketPerMonth = (TicketPerMonth) session.getAttribute("ticket");
        session.setAttribute("ticket", ticketPerMonth);
        ticketPerYearService.changeTicket(ticketPerMonth.getCreatedOn(), ticketPerMonth.getNumberOfPeople(), ticketPerMonth.getPrice());
        return "redirect:/permonth/tickets";
    }
}
