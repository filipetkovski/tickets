package project.ticket_manager.controller;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import project.ticket_manager.dto.RegistrationDto;
import project.ticket_manager.model.Ticket;
import project.ticket_manager.model.UserEntity;
import project.ticket_manager.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class AuthController {
    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String getRegisterForm(Model model) {
        RegistrationDto user = new RegistrationDto();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register/save")
    public String register(@ModelAttribute("user") RegistrationDto user, BindingResult result, Model model) {
        UserEntity existedUserEmail = userService.findByEmail(user.getEmail());
        if(existedUserEmail != null && existedUserEmail.getEmail() != null && !existedUserEmail.getEmail().isEmpty()) {
            return "redirect:/register?fail";
        }

        UserEntity existedUserUsername = userService.findByUsername(user.getUsername());
        if(existedUserUsername != null && existedUserUsername.getUsername() != null && !existedUserUsername.getUsername().isEmpty()) {
            return "redirect:/register?fail";
        }

        if(result.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
        }

        userService.saveUser(user);
        return "redirect:/ticket/create?success";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/user/manager")
    private  String getAllUsers(Model model) {
        List<UserEntity> users = userService.findAll();
        model.addAttribute("users", users);
        return "users_list";
    }

    @GetMapping("/user/{userId}/delete")
    private String deleteUser(@PathVariable("userId") Long userId) {
        userService.deleteById(userId);
        return "redirect:/user/manager";
    }

    @GetMapping("/user/{userId}/edit")
    private String editUser(@PathVariable("userId") Long userId, Model model) {
        UserEntity user = userService.findById(userId);
        model.addAttribute("user", user);
        return "user_edit";
    }

    @PostMapping("/user/{userId}/edit")
    private String updateEditUser(@PathVariable("userId") Long userId, @ModelAttribute("user") RegistrationDto user) {
        user.setId(userId);
        userService.save(user);
        return "redirect:/user/manager?success";
    }
}
