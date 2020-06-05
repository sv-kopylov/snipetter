package ru.kopylov.snippeter.management;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Logger;
import ru.kopylov.snippeter.model.Genre;
import ru.kopylov.snippeter.model.Source;
import ru.kopylov.snippeter.utils.EmTAProxy;
import ru.kopylov.snippeter.utils.EntityManagerHolder;

import javax.persistence.EntityManager;
import java.util.Collections;

@Getter
@Setter
public class SourceManager {
    private static Logger logger = Logger.getLogger(SourceManager.class);
    private EntityManager em = EntityManagerHolder.getInstance().getEntityManager();
    private EmTAProxy emProxy;
    private ObservableList<Source> sources = FXCollections.observableArrayList();

    public SourceManager() {
        emProxy = new EmTAProxy(em);
        completeSources();
    }


    public Source createSource(String tytle, String author, int year, boolean isTranslation, String translator, Genre genre,  String linkToFile){
        Source source = new Source();
        source.setTitle(tytle);
        source.setAuthor(author);
        source.setYear(year);
        source.setTranslation(isTranslation);
        source.setTranslator(translator);
        source.setGenre(genre);
        source.setLinkToFile(linkToFile);
        emProxy.persist(source);
        sources.add(source);
        logger.info("source created "+source);
        return source;
    }

    private void completeSources(){
        emProxy.<Source>getAllInstancesStream(Source.class).forEach(s -> sources.add(s));
    }

    public void sortByAutor (){
        Collections.sort(sources,(s1, s2)-> s1.getAuthor().compareTo(s2.getAuthor()) );
    }
    public void sortByTytle (){
        Collections.sort(sources,(s1, s2)-> s1.getTitle().compareTo(s2.getTitle()) );
    }






}
