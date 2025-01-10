package project.ticket_manager.service;


import project.ticket_manager.dto.RegistrationDto;
import project.ticket_manager.model.UserEntity;

import java.util.List;

public interface UserService {
    void saveUser(RegistrationDto registrationDto);

    UserEntity findByEmail(String email);

    UserEntity findByUsername(String username);

    List<UserEntity> findAll();
    void deleteById(Long userId);

    public void save(RegistrationDto user);

    UserEntity findById(Long userId);
}
