package com.baziaka.dto;

import com.baziaka.domain.Event;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.DateTime;

public class EventDTO {

    private String cinemaName;
    private String auditoriumName;
    private Double price;
    private DateTime date;

    public static EventDTO of(Event event) {
        EventDTO eventDTO = new EventDTO();

        eventDTO.auditoriumName = event.getAuditorium().getName();
        eventDTO.cinemaName = event.getCinema().getName();
        eventDTO.price = event.getBasicPrice();
        eventDTO.date = event.getDate();

        return eventDTO;
    }

    public EventDTO() {
        super();
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    public String getAuditoriumName() {
        return auditoriumName;
    }

    public void setAuditoriumName(String auditoriumName) {
        this.auditoriumName = auditoriumName;
    }

    @JsonIgnore
    public DateTime getDate() {
        return date;
    }

    @JsonProperty("date")
    public String getDateInString() {
        return date.toString();
    }

    @JsonProperty("date")
    public void setDate(String date) {
        this.date = DateTime.parse(date);
    }
}
