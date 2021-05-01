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
    private long position=-1;

    @Column(columnDefinition="varchar(4096)")
    private String snippet;
    private String processedSnippet;

    public Snippet(String snippet, Source source) {
        this.snippet = filter(snippet);
        this.source = source;
    }

    @ManyToOne(optional=false)
    @JoinColumn(name="source_id")
    private Source source;

    public Snippet() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Snippet snippet = (Snippet) o;

        return id.equals(snippet.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    private String filter(String in){
        return in.replaceAll("(\r|\n)"," ");
    }

    @Override
    public String toString() {
        return snippet.length()>64?snippet.substring(0, 64):snippet;
    }
}
