package ru.kopylov.snippeter.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Центральный элемент - отрывок из произведения, обладающий, желательно всеми признаками
 */
@Getter
@Setter
@Entity
public class Snippet {
    @Id
    @GeneratedValue
    private Long id;


//    позиции отрывка в произведении
    private long begin;
    private long end;

    String snippet;
    String treatedSnippet;

    @ManyToOne(optional=false, cascade= CascadeType.ALL)
    @JoinColumn(name="source_id")
    private Source source;

}
