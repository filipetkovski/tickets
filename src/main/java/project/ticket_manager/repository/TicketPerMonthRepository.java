package project.ticket_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.ticket_manager.model.TicketPerDay;
import project.ticket_manager.model.TicketPerMonth;
import project.ticket_manager.model.TicketPerYear;

import java.time.LocalDateTime;

public interface TicketPerMonthRepository extends JpaRepository<TicketPerMonth, Long> {
    @Query("SELECT t FROM TicketPerMonth t WHERE FUNCTION('DATE_TRUNC', 'MONTH', CAST(t.createdOn AS date)) = FUNCTION('DATE_TRUNC', 'MONTH', CAST(:date AS date))")
    TicketPerMonth findByCreatedOn(@Param("date") LocalDateTime date);

    @Query("SELECT t FROM TicketPerMonth t WHERE FUNCTION('DATE_TRUNC', 'YEAR', CAST(t.createdOn AS date)) = FUNCTION('DATE_TRUNC', 'YEAR', CAST(:date AS date))")
    TicketPerMonth findByYear(@Param("date") LocalDateTime date);

    TicketPerMonth getById(Long ticketId);
}
