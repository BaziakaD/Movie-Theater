package com.baziaka.service.crud;

import com.baziaka.domain.auditorium.Auditorium;
import com.baziaka.dto.EventDTO;
import com.baziaka.domain.Cinema;
import com.baziaka.domain.Event;
import com.baziaka.domain.seat.Seat;
import com.baziaka.repository.EventRepository;
import com.baziaka.repository.SeatRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EventService extends CRUDService<Event> {

    private EventRepository eventRepository;
    private SeatRepository seatRepository;
    private AuditoriumService auditoriumService;
    private CinemaService cinemaService;

    @Autowired
    public EventService(EventRepository eventRepository,
                        SeatRepository seatRepository,
                        AuditoriumService auditoriumService,
                        CinemaService cinemaService) {

        super(eventRepository);
        this.eventRepository = eventRepository;
        this.seatRepository = seatRepository;
        this.auditoriumService = auditoriumService;
        this.cinemaService = cinemaService;
    }


    private Event generateEvent(EventDTO eventDTO) {
        Cinema cinema = cinemaService.findByName(eventDTO.getCinemaName());
        Double price = eventDTO.getPrice();
        Auditorium auditorium = auditoriumService.findByNameWithBooking(eventDTO.getAuditoriumName());

        Event event = new Event();

        event.setCinema(cinema);
        event.setBasicPrice(price);
        event.setAuditorium(auditorium);
        event.setDate(eventDTO.getDate());

        return event;
    }

    @Transactional
    public Event createEvent(EventDTO eventDTO) {

        Event event = generateEvent(eventDTO);
        auditoriumService.reserveAuditorium(event.getAuditorium(), event.getDate(), event.getCinema().getLength());
        save(event);

        return event;
    }

    @Transactional
    public Set<Event> createEvents(Set<EventDTO> eventsDto) {

        Set<Event> events = new HashSet<>();

        for (EventDTO eventDTO : eventsDto) {
            Event event = generateEvent(eventDTO);
            events.add(event);
            auditoriumService.reserveAuditorium(event.getAuditorium(), event.getDate(), event.getCinema().getLength());
        }

        saveAll(events);

        return events;
    }

    public boolean canCreateEvent(EventDTO eventDTO) {

        Auditorium auditorium = auditoriumService.findByNameWithBooking(eventDTO.getAuditoriumName());
        Cinema cinema = cinemaService.findByName(eventDTO.getCinemaName());

        return canCreateEvent(auditorium, eventDTO.getDate(), cinema);

    }

    public boolean canCreateEvent(Auditorium auditorium, DateTime from, Cinema cinema) {
        return auditoriumService.isAuditoriumFree(auditorium, from, cinema.getLength());
    }

    @Transactional
    public List<Seat> getFreeSeatsForEvent(Long eventId) {

        Event event = findById(eventId);

        Set<Seat> bookedSeats = seatRepository
                .findByAuditoriumAndBookingForSeatDateTime(event.getAuditorium(), event.getDate());

        List<Seat> freeSeats = event
                .getAuditorium()
                .getSeats()
                .stream()
                .filter(seat -> !bookedSeats.contains(seat))
                .collect(Collectors.toList());

        return freeSeats;
    }

    public Set<Event> getEventsForDateRange(Date from, Date to) {
        return eventRepository.findByDateAfterAndDateBefore(from, to);
    }

    public Set<Event> getNextEvents(Date to) {
        return eventRepository.findByDateAfterAndDateBefore(new Date(), to);
    }

    public Event findByCinemaNameAndDate(String cinemaName, DateTime date) {
        return eventRepository.findByCinemaNameAndDate(cinemaName, date);
    }

    public List<Event> findAllWithCinemaAndAuditory() {
        return eventRepository.findByAllWithAuditoriumWithBooking();
    }
}
