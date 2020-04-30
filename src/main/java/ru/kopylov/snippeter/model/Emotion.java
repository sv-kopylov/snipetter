package ru.kopylov.snippeter.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * эмоциz, которую испытывает или описывает автор
 */
@Getter @Setter
@Entity
public class Emotion {

    @Id
    @GeneratedValue
    private Long id;

    private String emotion;
}
