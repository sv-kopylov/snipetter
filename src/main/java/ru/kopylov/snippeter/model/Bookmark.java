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

    @ManyToOne(optional=false)
    @JoinColumn(name="source_id")
    private Source source;

    private int position;
    private double width;
    private double fontScale;

    public Bookmark() {
    }

    public Bookmark(String name, Source source, int position, double width, double fontScale) {
        this.name = name;
        this.source = source;
        this.position = position;
        this.width = width;
        this.fontScale = fontScale;
    }



    @Override
    public String toString() {
        String shortName = name.length()>20?name.substring(0, 20):name;
        return String.format("%s\t[w:%.0f, f:%.1f]", shortName, width, fontScale);
    }
}
