package project.ticket_manager.mapping;

import project.ticket_manager.dto.TicketPerDayDto;
import project.ticket_manager.dto.TicketPerMonthDto;
import project.ticket_manager.model.TicketPerDay;
import project.ticket_manager.model.TicketPerMonth;

public class TicketPerMonthMapping {
    public static TicketPerMonth mapToTicketPerMonth(TicketPerMonthDto ticketPerMonthDto) {
        TicketPerMonth ticketPerMonth = TicketPerMonth.builder()
                .createdOn(ticketPerMonthDto.getCreatedOn())
                .Id(ticketPerMonthDto.getId())
                .numberOfPeople(ticketPerMonthDto.getNumberOfPeople())
                .price(ticketPerMonthDto.getPrice())
                .updatedOn(ticketPerMonthDto.getUpdatedOn())
                .build();
        return ticketPerMonth;
    }

    public static TicketPerMonthDto mapToTicketPerMonthDto (TicketPerMonth ticketPerMonth) {
        TicketPerMonthDto ticketPerMonthDto = TicketPerMonthDto.builder()
                .createdOn(ticketPerMonth.getCreatedOn())
                .Id(ticketPerMonth.getId())
                .numberOfPeople(ticketPerMonth.getNumberOfPeople())
                .price(ticketPerMonth.getPrice())
                .updatedOn(ticketPerMonth.getUpdatedOn())
                .build();
        return ticketPerMonthDto;
    }
}
