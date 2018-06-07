package com.baziaka.domain;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;

@Entity
@Table(name = "cinema")
public class Cinema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "rating", nullable = false)
    private Rating rating;

    @Column(name = "length", nullable = false)
    private Integer length;

    public Cinema() {
    }

    public Cinema(String name, Rating rating, Integer length) {
        this.name = name;
        this.rating = rating;
        this.length = length;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(name)
                .append(rating)
                .append(length)
                .build();
    }
}
