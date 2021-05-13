package ru.kopylov.snippeter.management;

import ru.kopylov.snippeter.model.Feature;
import ru.kopylov.snippeter.model.Snippet;
import ru.kopylov.snippeter.model.SnippetFeatureBunch;
import ru.kopylov.snippeter.model.Source;
import ru.kopylov.snippeter.utils.EntityManagerHolder;

import javax.persistence.EntityManager;
import java.util.List;


public class SnippetDTO {
    private EntityManager em = EntityManagerHolder.getInstance().getEntityManager();



    public void persist(String snipStr, Source source, List<Feature> features){
        Snippet snippet = new Snippet(snipStr, source);
        if(!em.getTransaction().isActive()){
            em.getTransaction().begin();
        }
        em.persist(snippet);
        for(Feature feature: features){
            em.persist(new SnippetFeatureBunch(snippet, feature));
        }
        em.flush();
        em.getTransaction().commit();

    }

    public void persist(Snippet snippet, List<Feature> features){
        if(!em.getTransaction().isActive()){
            em.getTransaction().begin();
        }
        em.persist(snippet);
        for(Feature feature: features){
            em.persist(new SnippetFeatureBunch(snippet, feature));
        }
        em.flush();
        em.getTransaction().commit();

    }


}
