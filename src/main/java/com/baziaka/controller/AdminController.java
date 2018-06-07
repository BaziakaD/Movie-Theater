package com.baziaka.controller;

import com.baziaka.domain.auditorium.Auditorium;
import com.baziaka.dto.EventDTO;
import com.baziaka.dto.FillUserBillDto;
import com.baziaka.service.crud.AuditoriumService;
import com.baziaka.service.crud.CinemaService;
import com.baziaka.service.crud.EventService;
import com.baziaka.service.crud.UserService;
import com.baziaka.utils.JacksonHelper;
import com.baziaka.domain.Cinema;
import com.baziaka.domain.Rating;
import com.baziaka.domain.User;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {


    private CinemaService cinemaService;
    private EventService eventService;
    private AuditoriumService auditoriumService;
    private UserService userService;

    @Autowired
    public AdminController(CinemaService cinemaService, EventService eventService, AuditoriumService auditoriumService, UserService userService) {
        this.cinemaService = cinemaService;
        this.eventService = eventService;
        this.auditoriumService = auditoriumService;
        this.userService = userService;
    }

    @GetMapping("/cinema")
    public String getAddCinemaView(Model model) {

        List<Rating> ratings = Arrays.asList(Rating.Low, Rating.Mid, Rating.High);

        model.addAttribute("ratings", ratings);

        return "cinema";
    }

    @PostMapping("/cinema")
    public String addCinema(Cinema cinema) {

        if (cinemaService.canSave(cinema)) {

            cinemaService.save(cinema);

            return "redirect:/admin/cinema?successfullyAdding=true";
        } else {
            return "redirect:/admin/cinema?duplicateCinemaName=" + cinema.getName();
        }
    }

    @PostMapping("/cinema/file")
    public String addCinemasFromFile(@RequestBody MultipartFile file) throws IOException {

        TypeReference<Set<Cinema>> typeRef = new TypeReference<Set<Cinema>>() {
        };
        Set<Cinema> cinemas = JacksonHelper.getSetOfObjectsFromJson(file.getBytes(), typeRef);

        if (cinemas != null) {

            for (Cinema cinema : cinemas) {
                if (!cinemaService.canSave(cinema)) {
                    return "redirect:/admin/cinema?duplicateCinemaName=" + cinema.getName();
                }
            }

            cinemaService.saveAll(cinemas);
            return "redirect:/admin/cinema?successfullyAdding=true";
        }

        return "redirect:/admin/cinema?error=true";
    }

    @GetMapping("/event")
    public String getAddEventView(Model model) {

        List<Cinema> cinemas = cinemaService.findAll();
        List<Auditorium> auditoriums = auditoriumService.findAll();

        model.addAttribute("cinemas", cinemas);
        model.addAttribute("auditoriums", auditoriums);

        return "event";
    }

    @PostMapping("/event")
    public String addEvent(EventDTO eventDTO) {

        if (eventService.canCreateEvent(eventDTO)) {
            eventService.createEvent(eventDTO);

            return "redirect:/admin/event?successfullyAdding=true";
        } else {
            return "redirect:/admin/event?duplicateEvent=true";
        }
    }

    @PostMapping("/event/file")
    public String addEventsFromFile(@RequestBody MultipartFile file) throws IOException {


        TypeReference<Set<EventDTO>> typeRef = new TypeReference<Set<EventDTO>>() {};
        Set<EventDTO> events = JacksonHelper.getSetOfObjectsFromJson(file.getBytes(), typeRef);


        if (events != null) {

            for (EventDTO eventDTO : events) {
                if (!eventService.canCreateEvent(eventDTO)) {
                    return "redirect:/admin/event?duplicateEvent=true";
                }
            }

            eventService.createEvents(events);
            return "redirect:/admin/event?successfullyAdding=true";
        }

        return "redirect:/admin/event?error=true";
    }

    @GetMapping("/cash")
    public String getFillUserBillView(Model model) {
        List<User> users = userService.findAll();

        model.addAttribute("users", users);

        return "cash";
    }

    @PostMapping("/cash")
    public String fillUserBill(FillUserBillDto fillUserBillDto) {
        User user = userService.findByEmail(fillUserBillDto.getEmail());

        if (user != null) {
            userService.additionBill(user, fillUserBillDto.getAmount());
            return "redirect:/admin/cash?successfully=true";
        } else {
            return "redirect:/admin/cash?successfully=false";
        }
    }
}
