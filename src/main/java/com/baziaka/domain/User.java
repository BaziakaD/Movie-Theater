package com.baziaka.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
@NamedEntityGraph(name = "user.tickets",
        attributeNodes =
        @NamedAttributeNode(value = "tickets", subgraph = "tickets.auditorium.event.seat"),
        subgraphs = {
                @NamedSubgraph(name = "tickets.auditorium.event.seat",
                        attributeNodes = {
                                @NamedAttributeNode(value = "event",
                                        subgraph = "event.cinema.auditorium"),
                                @NamedAttributeNode(value = "discounts",
                                        subgraph = "ticketdiscount.discount")}),
                @NamedSubgraph(name = "ticketdiscount.discount",
                        attributeNodes = @NamedAttributeNode("discount"))
        })
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "bill", nullable = false)
    private Double bill;

    @Column(name = "BIRTHDAY", nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate birthday;

    @OneToMany(mappedBy = "user")
    private List<Ticket> tickets;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public User() {
        tickets = new ArrayList<>();
        birthday = new LocalDate();
    }

    public User(@NotBlank @Email String email, String password,
                @NonNull LocalDate birthday, Double bill) {
        this();
        this.email = email;
        this.birthday = birthday;
        this.password = password;
        this.bill = bill;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null) {
            return false;
        }

        if (getClass() != other.getClass()) {
            return false;
        }

        User otherUser = (User) other;

        return new EqualsBuilder()
                .append(email, otherUser.email)
                .append(this.birthday, otherUser.birthday)
                .isEquals();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Double getBill() {
        return bill;
    }

    public void setBill(Double bill) {
        this.bill = bill;
    }

    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void substractBill(Double amount) {
        if (bill - amount >= 0) {
            bill -= amount;
        } else {
            throw new RuntimeException("Not enough money. User - " + email);
        }
    }

    public void additionBill(Double amount) {
        bill += amount;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(email)
                .append(password)
                .append(birthday)
                .build();
    }
}
