package project.ticket_manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.ticket_manager.dto.TicketDto;
import project.ticket_manager.model.Ticket;
import project.ticket_manager.model.UserEntity;
import project.ticket_manager.security.SecurityUtil;
import project.ticket_manager.service.TicketService;
import project.ticket_manager.service.UserService;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
public class TicketController {
    TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/daily/tickets")
    public String getTickets(Model model) {
        LocalDateTime date = LocalDateTime.now();
        List<TicketDto> tickets = ticketService.getDailyTickets(date);
        model.addAttribute("tickets", tickets);
        return "daily_tickets";
    }

    @GetMapping("/ticket/create")
    public String createTicket(Model model) {
        Ticket ticket = new Ticket();
        model.addAttribute("ticket", ticket);
        return "create_ticket";
    }

    @PostMapping("/ticket/create")
    public String updateTicket(@ModelAttribute("ticket") Ticket ticket, HttpSession session) {
        BufferedReader reader = null;
        String line;
        try {
            reader = new BufferedReader(new FileReader("price.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            line = reader.readLine();
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ticket.setPrice(ticket.getNumberOfPeople() * Integer.parseInt(line));
        ticketService.save(ticket);
        session.setAttribute("ticket", ticket);
        return "redirect:/ticket/create/perday";
    }

    @GetMapping("/ticket/{ticketId}/delete")
    public String deleteTicket(@PathVariable("ticketId") Long ticketId, HttpSession session) {
        Ticket ticket = ticketService.getById(ticketId);
        session.setAttribute("ticket", ticket);
        ticketService.deleteWithId(ticketId);
        return "redirect:/ticket/{ticketId}/delete/perday";
    }

    @GetMapping("/ticket/code")
    public String findByCode() {
        return "find_ticket";
    }

    @PostMapping("/ticket/code")
    @ResponseBody
    public ResponseEntity<?> findTicketByCode(@RequestParam String code) {
        try {
            Ticket foundTicket = ticketService.findByCode(code);
            if(!code.matches("\\d+")) {
                return ResponseEntity.ok("Invalid");
            }
            if (foundTicket == null) {
                return ResponseEntity.ok("NotFound");
            }
            return ResponseEntity.ok(foundTicket);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    @GetMapping("/ticket/price")
    public String changePrice() {
        return "ticket_price";
    }

    @PostMapping("/ticket/price")
    public String updatePrice(@RequestParam("price") Integer price) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter("price.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            writer.write(price.toString());
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/";
    }
}

