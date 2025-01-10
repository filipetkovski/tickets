package project.ticket_manager.service.impl;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.ticket_manager.dto.RegistrationDto;
import project.ticket_manager.model.Role;
import project.ticket_manager.model.UserEntity;
import project.ticket_manager.repository.RoleRepository;
import project.ticket_manager.repository.UserRepository;
import project.ticket_manager.service.UserService;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(RegistrationDto registrationDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(registrationDto.getUsername());
        userEntity.setEmail(registrationDto.getEmail());
        userEntity.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        Role role = roleRepository.findByName("ROLE_USER");
        userEntity.setRoles(Arrays.asList(role));
        userRepository.save(userEntity);
    }

    @Override
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<UserEntity> findAll() {
        return userRepository.findAll().stream()
                .filter(user -> user.getRoles().stream().anyMatch(role -> "ROLE_USER".equals(role.getName())))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long userId) {
        UserEntity user = userRepository.getById(userId);
        Role role = roleRepository.getReferenceById(Long.valueOf(1));
        user.getRoles().remove(role);
        userRepository.deleteById(userId);
    }

    @Override
    public void save(RegistrationDto registrationDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(registrationDto.getId());
        userEntity.setUsername(registrationDto.getUsername());
        userEntity.setEmail(registrationDto.getEmail());
        userEntity.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        Role role = roleRepository.findByName("ROLE_USER");
        userEntity.setRoles(Arrays.asList(role));
        userRepository.save(userEntity);
    }

    @Override
    public UserEntity findById(Long userId) {
        return userRepository.getReferenceById(userId);
    }
}
