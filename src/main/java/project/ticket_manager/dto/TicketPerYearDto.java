package project.ticket_manager.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TicketPerYearDto {
    private Long Id;
    private Integer numberOfPeople;
    private Integer price;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
}
