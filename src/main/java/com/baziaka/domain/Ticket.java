package com.baziaka.domain;

import com.baziaka.domain.discount.Discount;
import com.baziaka.domain.discount.TicketDiscount;
import com.baziaka.domain.seat.Seat;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ticket",
        uniqueConstraints = @UniqueConstraint(columnNames = {"event_id", "seat_id"}))
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "price", nullable = false)
    private Double price;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "seat_id")
    private Seat seat;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ticket")
    private Set<TicketDiscount> discounts;

    public Ticket(User user, Double price, Event event, Seat seat) {
        this();
        this.user = user;
        this.price = price;
        this.event = event;
        this.seat = seat;
    }

    public Ticket() {
        discounts = new HashSet<>();
    }

    public Double getPrice() {
        return price;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public Event getEvent() {
        return event;
    }

    public DateTime getDate() {
        return event.getDate();
    }

    public Seat getSeat() {
        return seat;
    }

    public User getUser() {
        return user;
    }

    public void addDiscount(Discount discount) {
        discounts.add(new TicketDiscount(discount, this));
    }

    public Set<TicketDiscount> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(Set<TicketDiscount> discounts) {
        this.discounts = discounts;
    }

    public Long getId() {
        return id;
    }

    @Override
    public int hashCode() {

        return new HashCodeBuilder()
                .append(price)
                .append(event)
                .append(user)
                .append(seat)
                .append(getDate())
                .build();
    }
}
