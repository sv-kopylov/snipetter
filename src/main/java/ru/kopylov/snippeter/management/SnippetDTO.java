package ru.kopylov.snippeter.management;

import ru.kopylov.snippeter.model.Feature;
import ru.kopylov.snippeter.model.Snippet;
import ru.kopylov.snippeter.model.SnippetFeatureBunch;
import ru.kopylov.snippeter.model.Source;

import javax.persistence.EntityManager;
import java.util.ArrayList;


public class SnippetDTO {
    private EntityManager em;

    private Source source;
    private ArrayList<Feature> features = new ArrayList<>();
    private Snippet snippet;

    public SnippetDTO(EntityManager em, Source source) {
        this.em = em;
        this.source = source;
    }

    public Snippet newSnippet(String snipStr){
        snippet = new Snippet(snipStr, source);
        return snippet;
    }

    public void addFeature(Feature feature){
        features.add(feature);
    }

    public void persist(){
        em.getTransaction().begin();
        em.persist(snippet);
        for(Feature feature: features){
            em.persist(new SnippetFeatureBunch(this.snippet, feature));
        }
        em.flush();
        em.getTransaction().commit();
    }

    public void setPosition(int begin){
        this.snippet.setPosition(begin);
    }

}
