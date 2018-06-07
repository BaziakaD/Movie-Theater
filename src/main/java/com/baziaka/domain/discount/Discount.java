package com.baziaka.domain.discount;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "discount")
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, unique = true)
    private DiscountType discountType;

    @Column(name = "discount", nullable = false)
    private Double discount;

    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "discount")
    private Set<TicketDiscount> ticketDiscounts;

    public Discount() {
        ticketDiscounts = new HashSet<>();
    }

    public Discount(DiscountType type, Double discount) {
        this();
        this.discountType = type;
        this.discount = discount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Set<TicketDiscount> getTicketDiscounts() {
        return ticketDiscounts;
    }

    public void setTicketDiscounts(Set<TicketDiscount> ticketDiscounts) {
        this.ticketDiscounts = ticketDiscounts;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public void setDiscountType(DiscountType discountType) {
        this.discountType = discountType;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(discountType)
                .append(discount)
                .build();
    }
}
