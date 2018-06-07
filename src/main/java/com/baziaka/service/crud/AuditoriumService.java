package com.baziaka.service.crud;

import com.baziaka.domain.auditorium.Auditorium;
import com.baziaka.domain.seat.SeatType;
import com.baziaka.domain.auditorium.AuditoriumBooked;
import com.baziaka.domain.seat.Seat;
import com.baziaka.repository.AuditoriumRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class AuditoriumService extends CRUDService<Auditorium> {

    private AuditoriumRepository auditoriumRepository;
    private Properties properties;

    @Autowired
    public AuditoriumService(AuditoriumRepository auditoriumRepository,
                             @Qualifier("auditorium") Properties properties) {
        super(auditoriumRepository);

        this.auditoriumRepository = auditoriumRepository;
        this.properties = properties;
    }

    @PostConstruct
    @Transactional
    public void loadAuditoriumsFromProperties() {

        Set<Auditorium> auditoriums = new HashSet<>();

        for (Object obj : this.properties.keySet()) {

            String auditoryName = (String) obj;

            if (!auditoryName.endsWith("vip")) {

                int len = Integer.parseInt(this.properties.getProperty(auditoryName));

                List<Integer> vipPlaces =

                        Arrays.stream(splitVipPlaces(auditoryName))
                                .mapToInt(Integer::parseInt).boxed()
                                .collect(Collectors.toList());

                Auditorium auditorium = getAuditorium(auditoryName, len, vipPlaces);

                auditoriums.add(auditorium);
            }
        }

        saveAll(auditoriums);
    }

    private Auditorium getAuditorium(String auditoryName, int len, List<Integer> vipPlaces) {
        Auditorium auditorium = new Auditorium();
        auditorium.setName(auditoryName);

        List<Seat> seats = generateSeats(len, vipPlaces, auditorium);

        auditorium.setSeats(seats);
        return auditorium;
    }

    private String[] splitVipPlaces(String auditoryName) {
        return this.properties.getProperty(auditoryName + ".vip")
                .replace("[", "")
                .replace("]", "")
                .split("[,]");
    }

    private List<Seat> generateSeats(int len, List<Integer> vipPlaces, Auditorium auditorium) {
        return IntStream.rangeClosed(1, len)
                .mapToObj(seatNumber -> getSeat(vipPlaces, auditorium, seatNumber))
                .collect(Collectors.toList());
    }

    private Seat getSeat(List<Integer> vipPlaces, Auditorium auditorium, int seatNumber) {
        Seat seat = new Seat();

        seat.setNumber(seatNumber);
        seat.setAuditorium(auditorium);

        if (vipPlaces.contains(seatNumber)) {
            seat.setType(SeatType.Vip);
        }

        return seat;
    }

    public Auditorium findByNameWithBooking(String auditoriumName) {
        return auditoriumRepository.findByNameWithBooking(auditoriumName);
    }

    public Auditorium findByNameWithSeats(String auditoriumName) {
        return auditoriumRepository.findByNameWithSeats(auditoriumName);
    }

    public Auditorium findByNameWithSeatsAndBooking(String auditoriumName) {
        return auditoriumRepository.findByNameWithAllData(auditoriumName);
    }

    @Transactional
    public void reserveAuditorium(Auditorium auditorium, DateTime date, Integer length) {
        auditorium.
                getBookingForAuditorium()
                .add(new AuditoriumBooked(auditorium, date, date.plusMinutes(length)));
    }

    public boolean isAuditoriumFree(Auditorium auditorium, DateTime from, Integer duration) {
        for (AuditoriumBooked booking : auditorium.getBookingForAuditorium()) {

            DateTime to = from.plusMinutes(duration);

            if (from.isEqual(booking.getFrom()) || from.isEqual(booking.getTo())) {
                return false;
            }

            if (to.isEqual(booking.getFrom()) || to.isEqual(booking.getTo())) {
                return false;
            }

            if (from.isAfter(booking.getFrom()) && from.isBefore(booking.getTo())) {
                return false;
            }

            if (to.isAfter(booking.getFrom()) && to.isBefore(booking.getTo())) {
                return false;
            }
        }

        return true;
    }
}
