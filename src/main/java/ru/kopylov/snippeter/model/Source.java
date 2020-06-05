package ru.kopylov.snippeter.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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

    @ManyToOne(optional=false, cascade= CascadeType.ALL)
    @JoinColumn(name="genre_id")
    private Genre genre;


    @Override
    public String toString() {
        return "Source{" +
                ", title='" + title + '\'' +
                "author='" + author + '\'' +
                ", year=" + year +
                ", genre=" + genre +
                ", isTranslation=" + isTranslation +
                ", translator='" + translator + '\'' +
                ", linkToFile='" + linkToFile + '\'' +
                '}';
    }
}
