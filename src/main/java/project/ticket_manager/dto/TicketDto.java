package project.ticket_manager.dto;

import lombok.Builder;
import lombok.Data;
import project.ticket_manager.model.UserEntity;

import java.time.LocalDateTime;

@Data
@Builder
public class TicketDto {
    private Long Id;
    private Integer numberOfPeople;
    private Integer price;
    private String code;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
}
