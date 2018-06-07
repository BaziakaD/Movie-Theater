package com.baziaka.dto;

import java.util.HashSet;
import java.util.Set;

public class BookingDTO {

    private Long eventID;
    private Set<Long> seatsIds;

    public BookingDTO() {
        seatsIds = new HashSet<>();
    }

    public Long getEventID() {
        return eventID;
    }

    public void setEventID(Long eventID) {
        this.eventID = eventID;
    }

    public Set<Long> getSeatsIds() {
        return seatsIds;
    }

    public void setSeatsIds(Long seatId) {
        this.seatsIds.add(seatId);
    }
}
