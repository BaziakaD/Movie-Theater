package com.baziaka.domain.auditorium;

import com.baziaka.domain.seat.Seat;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "auditorium")
@NamedEntityGraphs({
        @NamedEntityGraph(name = "auditorium.seats",
                attributeNodes = @NamedAttributeNode("seats")),
        @NamedEntityGraph(name = "auditorium.booking",
                attributeNodes = @NamedAttributeNode("bookingForAuditorium")),
        @NamedEntityGraph(name = "auditorium.full",
                attributeNodes = {@NamedAttributeNode("seats"), @NamedAttributeNode("bookingForAuditorium")})
})
public class Auditorium {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", unique = true)
    private String name;

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "auditorium")
    private List<Seat> seats;

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "auditorium")
    private List<AuditoriumBooked> bookingForAuditorium;

    public Auditorium() {
        seats = new ArrayList<>();
        bookingForAuditorium = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public List<AuditoriumBooked> getBookingForAuditorium() {
        return bookingForAuditorium;
    }

    public void setBookingForAuditorium(List<AuditoriumBooked> bookingForAuditorium) {
        this.bookingForAuditorium = bookingForAuditorium;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return seats.size();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Auditorium)) {
            return false;
        }

        Auditorium auditorium = (Auditorium) other;

        return new EqualsBuilder()
                .append(this.name, auditorium.name)
                .append(this.seats.size(), auditorium.seats.size())
                .isEquals();
    }


    @Override
    public String toString() {
        return name;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(name)
                .append(seats.size())
                .build();
    }
}
