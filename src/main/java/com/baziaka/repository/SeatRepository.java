package com.baziaka.repository;

import com.baziaka.domain.auditorium.Auditorium;
import com.baziaka.domain.seat.Seat;
import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface SeatRepository extends JpaRepository<Seat, Long> {

    Set<Seat> findByAuditoriumAndBookingForSeatDateTime(Auditorium auditorium, DateTime dateTime);

    @EntityGraph("seat.booking")
    @Query("SELECT seats FROM Seat seats WHERE id In :ids")
    List<Seat> findAllWithBooking(@Param("ids") List<Long> ids);
}
