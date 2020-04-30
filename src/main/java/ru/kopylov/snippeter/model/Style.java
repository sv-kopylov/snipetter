package ru.kopylov.snippeter.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * стиль речи все, что угодно, и стилизации надо будет подумать
 * исторический, блатной, намерено упрощенный, открытый список
 */
@Getter @Setter
@Entity
public class Style {

    @Id
    @GeneratedValue
    private Long id;


    private String style;
}
