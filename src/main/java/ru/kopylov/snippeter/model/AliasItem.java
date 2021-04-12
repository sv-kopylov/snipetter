package ru.kopylov.snippeter.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class AliasItem {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional=false)
    @JoinColumn(name="feature_id")
    private Feature feature;

    @Column(columnDefinition="varchar(128)")
    private String name;

    public AliasItem(String name, Feature feature) {
        this.feature = feature;
        this.name = name;
    }

    public AliasItem() {
    }
}
