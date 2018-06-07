package com.baziaka.domain.seat;

import com.baziaka.domain.auditorium.Auditorium;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "seat",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"fk_auditorium", "number"})
        })
@NamedEntityGraph(name = "seat.booking",
        attributeNodes = @NamedAttributeNode("bookingForSeat"))
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number", nullable = false)
    private int number;

    @ManyToOne
    @JoinColumn(name = "fk_auditorium")
    private Auditorium auditorium;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type", nullable = false)
    private SeatType type;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "seat")
    private List<SeatBooked> bookingForSeat;

    public Seat() {
        this.type = SeatType.Basic;
        this.bookingForSeat = new ArrayList<>();
    }

    public Seat(SeatType type) {
        this();
        this.type = type;
    }

    public Seat(int number) {
        this();
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Auditorium getAuditorium() {
        return auditorium;
    }

    public void setAuditorium(Auditorium auditorium) {
        this.auditorium = auditorium;
    }

    public SeatType getType() {
        return type;
    }

    public void setType(SeatType type) {
        this.type = type;
    }

    public void addBookingForSeat(SeatBooked seatBooked) {
        bookingForSeat.add(seatBooked);
    }

    public List<SeatBooked> getBookingForSeat() {
        return bookingForSeat;
    }

    public void setBookingForSeat(List<SeatBooked> bookingForSeat) {
        this.bookingForSeat = bookingForSeat;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Seat)) {
            return false;
        }

        Seat seat = (Seat) other;

        return new EqualsBuilder()
                .append(this.number, seat.number)
                .append(this.type, seat.type)
                .append(this.auditorium.getName(), seat.auditorium.getName())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(number)
                .append(type)
                .append(auditorium.getName())
                .build();
    }
}
