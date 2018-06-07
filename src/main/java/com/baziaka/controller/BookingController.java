package com.baziaka.controller;

import com.baziaka.domain.Event;
import com.baziaka.domain.Ticket;
import com.baziaka.service.BookingService;
import com.baziaka.service.crud.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/booking")
public class BookingController {

    private EventService eventService;
    private BookingService bookingService;

    @Autowired
    public BookingController(EventService eventService, BookingService bookingService) {
        this.eventService = eventService;
        this.bookingService = bookingService;
    }

    @GetMapping("/select/event")
    public String getSelectEventBookingView(Model model) {
        List<Event> events = eventService.findAllWithCinemaAndAuditory();
        model.addAttribute("events", events);

        return "select_event_booking";
    }

    @GetMapping("/select/seats")
    public String getSelectSeatsView(@RequestParam Long eventId, Model model) {
        Event event = eventService.findById(eventId);

        model.addAttribute("basicPrice", event.getBasicPrice().toString());
        model.addAttribute("seats", eventService.getFreeSeatsForEvent(eventId));

        return "select_seats_booking";
    }

    @GetMapping("/tickets")
    public String getBooking(@RequestParam Long eventId,
                             @RequestParam List<Long> seats,
                             Model model, Principal principal) {

        String email = principal.getName();
        List<Ticket> tickets = bookingService.bookTicket(eventId, email, seats);

        if (tickets.size() > 0) {
            model.addAttribute("tickets", tickets);
        }

        return "show_tickets_booking";
    }
}
