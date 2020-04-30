package ru.kopylov.snippeter.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * типы повествование (описание действия), диалог, описание, определение, это характеристика отрывка и может быть только одна
 */

@Getter @Setter
@Entity
public class Type {
    @Id
    @GeneratedValue
    private Long id;


    private String type;
}
