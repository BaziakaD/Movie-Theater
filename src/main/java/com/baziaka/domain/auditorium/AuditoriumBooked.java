package com.baziaka.domain.auditorium;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

@Entity
@Table(name = "auditorium_booked")
public class AuditoriumBooked {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auditorium_id")
    private Auditorium auditorium;

    @Column(name = "date_from", nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime from;

    @Column(name = "date_to", nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime to;

    public AuditoriumBooked() {
    }

    public AuditoriumBooked(Auditorium auditorium, DateTime from, DateTime to) {
        this.auditorium = auditorium;
        this.from = from;
        this.to = to;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Auditorium getAuditorium() {
        return auditorium;
    }

    public void setAuditorium(Auditorium auditorium) {
        this.auditorium = auditorium;
    }

    public DateTime getFrom() {
        return from;
    }

    public void setFrom(DateTime from) {
        this.from = from;
    }

    public DateTime getTo() {
        return to;
    }

    public void setTo(DateTime to) {
        this.to = to;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AuditoriumBooked)) {
            return false;
        }

        AuditoriumBooked auditoriumBooked = (AuditoriumBooked) other;

        return new EqualsBuilder()
                .append(this.auditorium, auditoriumBooked.auditorium)
                .append(this.from, auditoriumBooked.from)
                .append(this.to, auditoriumBooked.to)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(auditorium)
                .append(from)
                .append(to)
                .build();
    }
}
