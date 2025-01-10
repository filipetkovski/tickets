package project.ticket_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.ticket_manager.model.TicketPerDay;
import project.ticket_manager.model.TicketPerMonth;

import java.time.LocalDateTime;

public interface TicketPerDayRepository extends JpaRepository<TicketPerDay, Long> {
    @Query("SELECT t FROM TicketPerDay t WHERE FUNCTION('DATE_TRUNC', 'DAY', CAST(t.createdOn AS date)) = FUNCTION('DATE_TRUNC', 'DAY', CAST(:date AS date))")
    TicketPerDay findByCreatedOn(@Param("date") LocalDateTime date);

    @Query("SELECT t FROM TicketPerDay t WHERE FUNCTION('DATE_TRUNC', 'MONTH', CAST(t.createdOn AS date)) = FUNCTION('DATE_TRUNC', 'MONTH', CAST(:date AS date))")
    TicketPerDay findByMonth(@Param("date") LocalDateTime date);

    @Query("SELECT t FROM TicketPerDay t WHERE FUNCTION('DATE_TRUNC', 'YEAR', CAST(t.createdOn AS date)) = FUNCTION('DATE_TRUNC', 'YEAR', CAST(:date AS date))")
    TicketPerDay findByYear(@Param("date") LocalDateTime date);
    TicketPerDay getById(Long ticketId);
}
