package ru.kopylov.snippeter.management;

import org.apache.log4j.Logger;
import ru.kopylov.snippeter.model.Feature;
import ru.kopylov.snippeter.model.Snippet;
import ru.kopylov.snippeter.model.SnippetFeatureBunch;
import ru.kopylov.snippeter.utils.EmTAProxy;
import ru.kopylov.snippeter.utils.EntityManagerHolder;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class BunchManager {

    private static Logger logger = Logger.getLogger(BunchManager.class);


    HashMap<Snippet,ArrayList<Feature>> map = new HashMap<>();


    private EntityManager em;
    private EmTAProxy emTAProxy;

    public BunchManager() {
        em = EntityManagerHolder.getInstance().getEntityManager();
        emTAProxy = new EmTAProxy(em);
    }

    public HashMap<Snippet, ArrayList<Feature>> fetchAllSnippets(){
        map.clear();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<SnippetFeatureBunch> query = cb.createQuery(SnippetFeatureBunch.class);
        Root<SnippetFeatureBunch> from = query.from(SnippetFeatureBunch.class);
        query.select(from);
        em.createQuery(query).getResultStream().forEach(this::treatOneBunch);
        return map;

    }


    private void treatOneBunch(SnippetFeatureBunch bunch){
        if(!map.containsKey(bunch.getSnippet())){
            map.put(bunch.getSnippet(), new ArrayList<Feature>());
        }
        map.get(bunch.getSnippet()).add(bunch.getFeature());
    }

    public HashMap<Snippet, ArrayList<Feature>> getMap() {
        return map;
    }

    public List<Feature> getFeatures(Snippet snippet){
        if(!em.getTransaction().isActive()){
            em.getTransaction().begin();
        }
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<SnippetFeatureBunch> query = cb.createQuery(SnippetFeatureBunch.class);
        Root<SnippetFeatureBunch> from = query.from(SnippetFeatureBunch.class);
        query.select(from);
        query.where(cb.equal(from.get("snippet"), snippet));
        List<Feature> result = em.createQuery(query).getResultStream().map(bunch -> bunch.getFeature()).collect(Collectors.toList());

        em.flush();
        em.getTransaction().commit();

        return result;
    }

    public void deleteBunches(Snippet snippet){
        if(!em.getTransaction().isActive()){
            em.getTransaction().begin();
        }
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaDelete<SnippetFeatureBunch> query = cb.createCriteriaDelete(SnippetFeatureBunch.class);
        Root<SnippetFeatureBunch> from = query.from(SnippetFeatureBunch.class);
        query.where(cb.equal(from.get("snippet"), snippet));
        int result = em.createQuery(query).executeUpdate();
        logger.info(String.format("deleted %d bunches", result));
        em.flush();
        em.getTransaction().commit();
    }
}
