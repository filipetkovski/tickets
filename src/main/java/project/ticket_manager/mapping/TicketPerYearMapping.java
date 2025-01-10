package project.ticket_manager.mapping;

import project.ticket_manager.dto.TicketPerDayDto;
import project.ticket_manager.dto.TicketPerYearDto;
import project.ticket_manager.model.TicketPerDay;
import project.ticket_manager.model.TicketPerYear;

public class TicketPerYearMapping {
    public static TicketPerYear mapToTicketPerYear(TicketPerYearDto ticketPerYearDto) {
        TicketPerYear ticketPerYear = TicketPerYear.builder()
                .createdOn(ticketPerYearDto.getCreatedOn())
                .Id(ticketPerYearDto.getId())
                .numberOfPeople(ticketPerYearDto.getNumberOfPeople())
                .price(ticketPerYearDto.getPrice())
                .updatedOn(ticketPerYearDto.getUpdatedOn())
                .build();
        return ticketPerYear;
    }

    public static TicketPerYearDto mapToTicketPerDayDto(TicketPerYear ticketPerYear) {
        TicketPerYearDto ticketPerYearDto = TicketPerYearDto.builder()
                .createdOn(ticketPerYear.getCreatedOn())
                .Id(ticketPerYear.getId())
                .numberOfPeople(ticketPerYear.getNumberOfPeople())
                .price(ticketPerYear.getPrice())
                .updatedOn(ticketPerYear.getUpdatedOn())
                .build();
        return ticketPerYearDto;
    }
}
