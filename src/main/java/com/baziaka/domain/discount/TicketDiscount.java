package com.baziaka.domain.discount;

import com.baziaka.domain.Ticket;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;

@Entity
@Table(name = "ticket_discount")
public class TicketDiscount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ticket_discount", nullable = false)
    private Double discountValue;

    @ManyToOne
    @JoinColumn(name = "discount_id", nullable = false)
    private Discount discount;

    @ManyToOne
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;

    public TicketDiscount() {
    }

    public TicketDiscount(Discount discount, Ticket ticket) {
        this.discountValue = discount.getDiscount();
        this.discount = discount;
        this.ticket = ticket;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Double getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(Double discountValue) {
        this.discountValue = discountValue;
    }

    public Discount getDiscount() {
        return discount;
    }

    @Override
    public int hashCode() {

        return new HashCodeBuilder()
                .append(ticket)
                .append(discount)
                .build();
    }
}
