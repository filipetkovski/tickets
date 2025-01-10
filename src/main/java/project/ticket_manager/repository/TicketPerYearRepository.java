package project.ticket_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.ticket_manager.model.TicketPerDay;
import project.ticket_manager.model.TicketPerYear;

import java.time.LocalDateTime;

public interface TicketPerYearRepository extends JpaRepository<TicketPerYear, Long> {
    @Query("SELECT t FROM TicketPerYear t WHERE FUNCTION('DATE_TRUNC', 'YEAR', CAST(t.createdOn AS date)) = FUNCTION('DATE_TRUNC', 'YEAR', CAST(:date AS date))")
    TicketPerYear findByYear(@Param("date") LocalDateTime date);

}
