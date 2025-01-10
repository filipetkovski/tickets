package project.ticket_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.ticket_manager.model.Ticket;
import project.ticket_manager.model.TicketPerDay;
import project.ticket_manager.model.UserEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    @Query("SELECT t FROM Ticket t WHERE FUNCTION('DATE_TRUNC', 'DAY', CAST(t.createdOn AS date)) = FUNCTION('DATE_TRUNC', 'DAY', CAST(:date AS date))")
    List<Ticket> findByCreatedOn(@Param("date") LocalDateTime date);

    @Query("SELECT t FROM Ticket t WHERE FUNCTION('DATE_TRUNC', 'MONTH', CAST(t.createdOn AS date)) = FUNCTION('DATE_TRUNC', 'MONTH', CAST(:date AS date))")
    List<Ticket> findByMonth(@Param("date") LocalDateTime date);

    @Query("SELECT t FROM Ticket t WHERE FUNCTION('DATE_TRUNC', 'YEAR', CAST(t.createdOn AS date)) = FUNCTION('DATE_TRUNC', 'YEAR', CAST(:date AS date))")
    List<Ticket> findByYear(@Param("date") LocalDateTime date);

    Ticket getById(Long ticketId);

    Ticket findByCode(String code);
}
