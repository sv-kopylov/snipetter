package ru.kopylov.snippeter.management;

import lombok.Getter;
import lombok.Setter;
import ru.kopylov.snippeter.model.Genre;
import ru.kopylov.snippeter.model.Source;

import javax.persistence.EntityManager;
import java.util.ArrayList;

@Getter
@Setter
public class SourceManager {
    private EntityManager em;
    private String text;
    private Source currentSource;
    private GenreManager genreManager;

    public SourceManager(EntityManager em) {
        this.em = em;
    }

    public Source createSource(){
        return null;
    }
    public Source chooseSourse(){
        return null;
    }



}
