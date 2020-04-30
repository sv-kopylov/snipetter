package ru.kopylov.snippeter.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Делаю на всякий случай - мало ли что
 */
@Getter @Setter
@Entity
public class Context {

    @Id
    @GeneratedValue
    private Long id;


    private String context;
}
