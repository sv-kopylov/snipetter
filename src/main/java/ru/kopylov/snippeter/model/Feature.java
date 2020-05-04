package ru.kopylov.snippeter.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class Feature {

    @Id
    @GeneratedValue
    private Long id;

    public Feature(Category category, String value) {
        this.category = category;
        this.value = value;
    }

    private Category category;
    private String value;
}
