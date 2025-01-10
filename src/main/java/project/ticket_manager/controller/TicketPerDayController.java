package project.ticket_manager.controller;

import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import project.ticket_manager.dto.TicketDto;
import project.ticket_manager.dto.TicketPerDayDto;
import project.ticket_manager.model.Ticket;
import project.ticket_manager.model.TicketPerDay;
import project.ticket_manager.model.UserEntity;
import project.ticket_manager.security.SecurityUtil;
import project.ticket_manager.service.TicketPerDayService;
import project.ticket_manager.service.TicketService;
import project.ticket_manager.service.UserService;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class TicketPerDayController {
    TicketPerDayService ticketPerDayService;
    TicketService ticketService;
    UserService userService;

    public TicketPerDayController(TicketPerDayService ticketPerDayService, UserService userService, TicketService ticketService) {
        this.ticketPerDayService = ticketPerDayService;
        this.userService = userService;
        this.ticketService = ticketService;
    }

    @GetMapping("/ticket/create/perday")
    public String updatePerDay(HttpSession session) {
        Ticket ticket = (Ticket) session.getAttribute("ticket");
        session.setAttribute("ticket", ticket);
        ticketPerDayService.updateDatabase(ticket);
        return "redirect:/ticket/create/permonth";
    }

    @GetMapping("/perday/tickets")
    public String getRenderPerDayTickets(Model model) {
        UserEntity user = new UserEntity();
        List<TicketPerDayDto> tickets = ticketPerDayService.findAll();
        String username = SecurityUtil.getSessionUser();
        if(username != null) {
            user = userService.findByEmail(username);
            model.addAttribute("user", user);
        }
        model.addAttribute("user", user);
        model.addAttribute("tickets", tickets);
        return "perday_tickets";
    }

    @GetMapping("/perday/ticket/{ticketId}/delete")
    public String deleteTicketPerDay(@PathVariable("ticketId") Long ticketId, HttpSession session) {
        TicketPerDay ticketPerDay = ticketPerDayService.getTicket(ticketId);
        session.setAttribute("ticket", ticketPerDay);
        ticketService.deleteByDate(ticketPerDay.getCreatedOn());
        ticketPerDayService.deleteById(ticketId);
        return "redirect:/perday/ticket/{ticketId}/delete/permonth";
    }

    @GetMapping("/ticket/{ticketId}/delete/perday")
    public String deleteTicket(HttpSession session) {
        Ticket ticket = (Ticket) session.getAttribute("ticket");
        session.setAttribute("ticket", ticket);
        ticketPerDayService.changeTicket(ticket.getCreatedOn(), ticket.getNumberOfPeople(), ticket.getPrice());
        return "redirect:/ticket/{ticketId}/delete/permonth";
    }
}
