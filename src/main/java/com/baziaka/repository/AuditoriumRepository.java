package com.baziaka.repository;

import com.baziaka.domain.auditorium.Auditorium;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditoriumRepository extends JpaRepository<Auditorium, Long> {

    Auditorium findByName(String auditoriumName);

    @EntityGraph(value = "auditorium.booking")
    @Query("SELECT auditorium FROM Auditorium auditorium WHERE name = :name")
    Auditorium findByNameWithBooking(@Param("name") String name);

    @EntityGraph(value = "auditorium.seats")
    @Query("SELECT auditorium FROM Auditorium auditorium WHERE name = :name")
    Auditorium findByNameWithSeats(String auditoriumName);

    @EntityGraph(value = "auditorium.full")
    @Query("SELECT auditorium FROM Auditorium auditorium WHERE name = :name")
    Auditorium findByNameWithAllData(String auditoriumName);
}
