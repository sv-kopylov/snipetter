package ru.kopylov.snipetter.management;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.kopylov.snippeter.management.FeatureManager;
import ru.kopylov.snippeter.model.Category;
import ru.kopylov.snippeter.model.Feature;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

public class FeatureManagerTest {
    private EntityManager em;
    @Before
    public void init() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory( "snip_pu" );
        em = emf.createEntityManager();
        em.getTransaction().begin();
    }

    @After
    public void close() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().commit();
        }
        em.getEntityManagerFactory().close();
        em.close();
    }

    @Test
    public void addFeature() {


    }

    @Test
    public void createFeature() throws UnsupportedEncodingException {
        FeatureManager fm = new FeatureManager(em);
        fm.createFeature("EMOTION", "страх");
        fm.createFeature("EMOTION", "смирение");
        fm.createFeature("EMOTION", "радость");
        fm.createFeature("AFFECT", "раздражение");
        fm.createFeature("AFFECT", "симпатия");

    }

    @Test
    public void completteFeatures() {
        FeatureManager fm = new FeatureManager(em);
        fm.completteFeatures();
        HashMap<Category, ArrayList<Feature>> a = fm.getFeatures();

        for(Category cat: a.keySet()){
            ArrayList<Feature> b = a.get(cat);
            if(b.size()>0){
                for(Feature f: b){
                    System.out.println(f.getCategory()+" -- "+f.getValue());
                }
            }
        }

    }
}