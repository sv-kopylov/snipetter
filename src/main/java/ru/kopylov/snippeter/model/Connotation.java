package ru.kopylov.snippeter.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Помимо основного смысла могут быть дополнительные
 */
@Getter @Setter
@Entity
public class Connotation {
    @Id
    @GeneratedValue
    private Long id;

    private String connotation;
}
