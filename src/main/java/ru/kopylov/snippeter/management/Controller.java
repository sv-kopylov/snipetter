package ru.kopylov.snippeter.management;

import ru.kopylov.snippeter.model.Feature;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Controller {
   private EntityManager em;
   private GenreManager genreManager;
   private SourceManager sourceManager;
   private FeatureManager featureManager;
   private SnippetDTO snippetDTO;




    public Controller() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory( "snip_pu" );
        em = emf.createEntityManager();

        genreManager = new GenreManager(em);
        sourceManager = new SourceManager(em);
        featureManager = new FeatureManager(em);

    }

    public void shutdoun(){
        if (em.getTransaction().isActive()) {
            em.getTransaction().commit();
        }
        em.getEntityManagerFactory().close();
        em.close();
    }


    public void newSnippet(String snippet){
        snippetDTO = new SnippetDTO(em, sourceManager.getCurrentSource());
        snippetDTO.newSnippet(snippet);
    }

    public void addFeatureToSnippet(Feature feature){
        snippetDTO.addFeature(feature);
    }

    public void saveSnippet(){
        snippetDTO.persist();
    }




}