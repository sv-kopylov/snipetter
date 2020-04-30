package ru.kopylov.snippeter.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * главный смысл отрывка, без коннотаций, без эмоций
 */

@Getter @Setter
@Entity
public class Substance {
    @Id
    @GeneratedValue
    private Long id;


    private String substance;
}
