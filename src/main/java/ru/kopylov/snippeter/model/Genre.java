package ru.kopylov.snippeter.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Жанр - свуязан с произведением (источником) но не с отрывком
 */
@Getter @Setter
@Entity
public class Genre {
    public Genre() {
    }

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    public Genre(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
