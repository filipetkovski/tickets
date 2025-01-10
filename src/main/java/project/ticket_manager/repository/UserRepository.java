package project.ticket_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.ticket_manager.model.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long>{
    UserEntity findByEmail(String email);
    UserEntity findByUsername(String username);

    UserEntity findFirstByUsername(String username);
    UserEntity findFirstByEmail(String email);
}
