package com.baziaka.service.crud;

import com.baziaka.domain.Cinema;
import com.baziaka.repository.CinemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CinemaService extends CRUDService<Cinema> {

    private CinemaRepository cinemaRepository;

    @Autowired
    public CinemaService(CinemaRepository cinemaRepository) {
        super(cinemaRepository);
        this.cinemaRepository = cinemaRepository;
    }

    public Cinema findByName(String name) {
        return cinemaRepository.findByName(name);
    }

    public boolean canSave(Cinema cinema) {
        return cinemaRepository.findByName(cinema.getName()) == null;
    }
}
