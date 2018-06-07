package com.baziaka.service.crud;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

abstract class CRUDService<T> {

    private JpaRepository<T, Long> repository;

    public CRUDService(JpaRepository<T, Long> repository) {
        this.repository = repository;
    }

    public T save(T obj) {
        return repository.save(obj);
    }

    public void saveAll(Set<T> items) {
        items.forEach(this::save);
    }

    public T findById(Long id) {
        return repository.findById(id).get();
    }

    public T update(T obj) {
        return repository.save(obj);
    }

    public List<T> findAll() {
        return repository.findAll();
    }

    public void remove(T obj) {
        repository.delete(obj);
    }
}
