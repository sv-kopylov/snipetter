package ru.kopylov.snippeter.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

/**
 * Литературное произведение из которого взыт отрывок
 */
@Getter
@Setter
@Entity
public class Source {
    @Id
    @GeneratedValue
    private Long id;


    private String author;
    private String translator;
    private String title;
    private String linkToFile;


    private int year;
    private boolean isTranslation;

    @ManyToOne(optional=false)
    @JoinColumn(name="genre_id")
    private Genre genre;


    @Override
    public String toString() {
        return title+". \n\t"+author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Source source = (Source) o;
        return id.equals(source.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
