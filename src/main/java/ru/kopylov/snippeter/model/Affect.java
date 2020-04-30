package ru.kopylov.snippeter.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 *  В отличие от эмоции, которую испытывает или описывает автор эффект это то, что испытывает читатель
 */
@Getter @Setter
@Entity
public class Affect {
    @Id
    @GeneratedValue
    private Long id;

    private String affect;
}
