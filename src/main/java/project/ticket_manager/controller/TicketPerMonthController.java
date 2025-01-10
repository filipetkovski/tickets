package project.ticket_manager.controller;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import project.ticket_manager.dto.TicketPerDayDto;
import project.ticket_manager.dto.TicketPerMonthDto;
import project.ticket_manager.model.Ticket;
import project.ticket_manager.model.TicketPerDay;
import project.ticket_manager.model.TicketPerMonth;
import project.ticket_manager.model.UserEntity;
import project.ticket_manager.security.SecurityUtil;
import project.ticket_manager.service.TicketPerDayService;
import project.ticket_manager.service.TicketPerMonthService;
import project.ticket_manager.service.TicketService;
import project.ticket_manager.service.UserService;
import project.ticket_manager.service.impl.TicketPerMonthServiceImpl;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class TicketPerMonthController {
    TicketPerMonthService ticketPerMonthService;
    TicketService ticketService;
    TicketPerDayService ticketPerDayService;
    UserService userService;

    public TicketPerMonthController(TicketPerMonthService ticketPerMonthService,TicketPerDayService ticketPerDayService, TicketService ticketService, UserService userService) {
        this.ticketPerMonthService = ticketPerMonthService;
        this.ticketPerDayService = ticketPerDayService;
        this.ticketService = ticketService;
        this.userService = userService;
    }

    @GetMapping("/ticket/create/permonth")
    public String updatePerMonth(HttpSession session) {
        Ticket ticket = (Ticket) session.getAttribute("ticket");
        session.setAttribute("ticket", ticket);
        ticketPerMonthService.updateDatabase(ticket);
        return "redirect:/ticket/create/peryear";
    }

    @GetMapping("/permonth/tickets")
    public String getPerMonth(Model model) {
        UserEntity user = new UserEntity();
        List<TicketPerMonthDto> tickets = ticketPerMonthService.findAll();
        String username = SecurityUtil.getSessionUser();
        if(username != null) {
            user = userService.findByEmail(username);
            model.addAttribute("user", user);
        }
        model.addAttribute("user", user);
        model.addAttribute("tickets", tickets);
        return "permonth_tickets";
    }

    @GetMapping("/ticket/{ticketId}/delete/permonth")
    public String deleteTicket(HttpSession session) {
        Ticket ticket = (Ticket) session.getAttribute("ticket");
        session.setAttribute("ticket", ticket);
        ticketPerMonthService.changeTicket(ticket.getCreatedOn(), ticket.getNumberOfPeople(), ticket.getPrice());
        return "redirect:/ticket/{ticketId}/delete/peryear";
    }

    @GetMapping("/perday/ticket/{ticketId}/delete/permonth")
    public String deleteTicketPerDay(HttpSession session) {
        TicketPerDay ticketPerDay = (TicketPerDay) session.getAttribute("ticket");
        session.setAttribute("ticket", ticketPerDay);
        ticketPerMonthService.changeTicket(ticketPerDay.getCreatedOn(), ticketPerDay.getNumberOfPeople(), ticketPerDay.getPrice());
        return "redirect:/perday/ticket/{ticketId}/delete/peryear";
    }

    @GetMapping("/permonth/ticket/{ticketId}/delete")
    public String deleteTicketPerMonth(@PathVariable("ticketId") Long ticketId, HttpSession session) {
        TicketPerMonth ticketPerMonth = ticketPerMonthService.getTicket(ticketId);
        session.setAttribute("ticket", ticketPerMonth);
        ticketService.deleteByMonth(ticketPerMonth.getCreatedOn());
        ticketPerDayService.deleteByMonth(ticketPerMonth.getCreatedOn());
        ticketPerMonthService.deleteById(ticketId);
        return "redirect:/permonth/ticket/{ticketId}/delete/peryear";
    }
}
