package com.baziaka.controller;

import com.baziaka.domain.Ticket;
import com.baziaka.domain.User;
import com.baziaka.dto.UserDTO;
import com.baziaka.service.crud.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String getRegistrationView(@ModelAttribute UserDTO user) {
        return "registration";
    }

    @PostMapping("/registration")
    public String registerNewUser(@NonNull UserDTO userDTO) {

        if (userService.findByEmail(userDTO.getEmail()) == null) {
            userService.registerNewUser(userDTO);
            return "redirect:/booking/select/event";

        } else {
            return "redirect:/registration?error=true";
        }
    }

    @GetMapping("/login")
    public String getLoginView() {
        return "login";
    }

    @GetMapping("/tickets")
    public String getTicketsView(Principal principal, Model model) {

        String email = principal.getName();

        User user = userService.findByEmailWithTickets(email);
        List<Ticket> tickets = user.getTickets();

        Collections.reverse(tickets);
        model.addAttribute("bill", user.getBill());
        model.addAttribute("tickets", tickets);

        return "tickets";
    }
}
