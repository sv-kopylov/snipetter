package ru.kopylov.snippeter.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Жанр - свуязан с произведением (источником) но не с отрывком
 */
@Entity
public class Genre {
    @Id
    @GeneratedValue
    private Long id;


    private String name;

    public Genre(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
