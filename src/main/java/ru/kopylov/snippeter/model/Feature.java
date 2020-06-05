package ru.kopylov.snippeter.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Feature {

    @Id
    @GeneratedValue
    private Long id;

    public Feature() {
    }

    public Feature(Category category, String value) {
        this.category = category;
        this.value = value;
    }
    @Enumerated(EnumType.STRING)
    private Category category;
    private String value;

    public String toString(){
        return category.toString()+"-"+value;
    }
}
