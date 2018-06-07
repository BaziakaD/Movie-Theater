package com.baziaka.repository;

import com.baziaka.domain.Event;
import org.hibernate.action.internal.EntityVerifyVersionProcess;
import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.Set;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    Set<Event> findByDateAfterAndDateBefore(Date from, Date to);
    Event findByCinemaNameAndDate(String cinemaName, DateTime date);

    @EntityGraph("event.cinema.auditorium")
    @Query("SELECT event FROM Event event WHERE id = :id")
    Event findByIdWithCinemaAndAuditroium(@Param("id") Long id);


    @EntityGraph("event.cinema.auditorium")
    @Query("SELECT event FROM Event event")
    List<Event> findByAllWithAuditoriumWithBooking();

    @EntityGraph("event.auditorium")
    @Query("SELECT event FROM Event event WHERE id = :id")
    Event findByIdWithAuditorium(@Param("id") Long id);

    @EntityGraph("event.auditorium.booking")
    @Query("SELECT event FROM Event event WHERE id = :id")
    Event findByIdWithAuditoriumWithBooking(@Param("id") Long id);

    @EntityGraph("event.cinema")
    @Query("SELECT event FROM Event event WHERE id = :id")
    Event findFullById(@Param("id") Long id);
}
