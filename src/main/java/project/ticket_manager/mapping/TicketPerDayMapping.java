package project.ticket_manager.mapping;

import project.ticket_manager.dto.TicketPerDayDto;
import project.ticket_manager.model.TicketPerDay;

import java.util.Optional;

public class TicketPerDayMapping {

    public static TicketPerDay mapToTicketPerDay(TicketPerDayDto ticketPerDayDto) {
        TicketPerDay ticketPerDay = TicketPerDay.builder()
                .createdOn(ticketPerDayDto.getCreatedOn())
                .Id(ticketPerDayDto.getId())
                .numberOfPeople(ticketPerDayDto.getNumberOfPeople())
                .price(ticketPerDayDto.getPrice())
                .updatedOn(ticketPerDayDto.getUpdatedOn())
                .build();
        return ticketPerDay;
    }

    public static TicketPerDayDto mapToTicketPerDayDto(TicketPerDay ticketPerDay) {
        TicketPerDayDto ticketPerDayDto = TicketPerDayDto.builder()
                .createdOn(ticketPerDay.getCreatedOn())
                .Id(ticketPerDay.getId())
                .numberOfPeople(ticketPerDay.getNumberOfPeople())
                .price(ticketPerDay.getPrice())
                .updatedOn(ticketPerDay.getUpdatedOn())
                .build();
        return ticketPerDayDto;
    }
}
