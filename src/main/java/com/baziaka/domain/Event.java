package com.baziaka.domain;

import com.baziaka.domain.auditorium.Auditorium;
import com.baziaka.domain.seat.Seat;
import com.baziaka.domain.seat.SeatBooked;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "event",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"fk_CinemaId", "fk_auditorium", "date"})
        })
@NamedEntityGraphs({
        @NamedEntityGraph(name = "event.full",
                subgraphs = {
                        @NamedSubgraph(name = "event.cinema.auditorium",
                                attributeNodes = @NamedAttributeNode("cinema")),
                        @NamedSubgraph(name = "event.auditorium.booking",
                                attributeNodes = @NamedAttributeNode("auditorium"))}),
        @NamedEntityGraph(name = "event.cinema",
                attributeNodes = @NamedAttributeNode(value = "cinema")),
        @NamedEntityGraph(name = "event.auditorium",
                attributeNodes = @NamedAttributeNode(value = "auditorium", subgraph = "auditorium.seats")),
        @NamedEntityGraph(name = "event.cinema.auditorium",
                attributeNodes =
                        {@NamedAttributeNode("cinema"), @NamedAttributeNode("auditorium")}),
        @NamedEntityGraph(name = "event.auditorium.booking",
                attributeNodes = @NamedAttributeNode(value = "auditorium", subgraph = "auditorium.booking"))})
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fk_CinemaId")
    private Cinema cinema;

    @ManyToOne
    @JoinColumn(name = "fk_auditorium")
    private Auditorium auditorium;

    @Column(name = "basic_price", nullable = false)
    private Double basicPrice;

    @Column(name = "date", nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime date;

    public Event() {
    }

    public Event(Cinema cinema, Auditorium auditorium, Double basicPrice, DateTime date) {
        this.cinema = cinema;
        this.auditorium = auditorium;
        this.basicPrice = basicPrice;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cinema getCinema() {
        return cinema;
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }

    public Auditorium getAuditorium() {
        return auditorium;
    }

    public void setAuditorium(Auditorium auditorium) {
        this.auditorium = auditorium;
    }

    public Double getBasicPrice() {
        return basicPrice;
    }

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    public void setBasicPrice(Double basicPrice) {
        this.basicPrice = basicPrice;
    }

    public Set<Seat> getBoughtSeats() {
        return auditorium.getSeats()
                .stream()
                .filter(seat -> {

                    for (SeatBooked seatBooked : seat.getBookingForSeat()) {
                        if (seatBooked.getDateTime().equals(date)) {
                            return true;
                        }
                    }
                    return false;
                })
                .collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        return String.format("%s, auditorium - %s, date and time - %s",
                cinema.getName(), auditorium.getName(), date.toString("dd-MM-yyyy HH:mm"));
    }

    @Override
    public int hashCode() {

        return new HashCodeBuilder()
                .append(auditorium)
                .append(cinema)
                .append(date)
                .append(basicPrice)
                .build();
    }
}
