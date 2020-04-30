package ru.kopylov.snippeter.model;

import javax.persistence.*;

/**
 * Литературное произведение из которого взыт отрывок
 */

@Entity
public class Source {
    @Id
    @GeneratedValue
    private Long id;


    private String author;
    private String translator;
    private String title;
    private String isbn;
    private String linkToFile;


    private int year;
    private boolean isTranslation;

    @ManyToOne(optional=false, cascade= CascadeType.ALL)
    @JoinColumn(name="genre_id")
    private Genre genre;



}
