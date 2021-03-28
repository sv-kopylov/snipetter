package ru.kopylov.snippeter.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
/**
 * Класс - закладка
 */
public class Bookmark {
    @Id
    @Column(columnDefinition="varchar(128)")
    private String name;

    @ManyToOne(optional=false, cascade= CascadeType.ALL)
    @JoinColumn(name="source_id")
    private Source source;

    private int position;

    private int maxPosition;

    public Bookmark() {
    }

    public Bookmark(String name, Source source, int position, int maxPosition) {
        this.name = name;
        this.source = source;
        this.position = position;
        this.maxPosition = maxPosition;
    }

    @Override
    public String toString() {
        String shortName = name.length()>20?name.substring(0, 20):name;
        return  shortName +" "+ position;
    }
}
