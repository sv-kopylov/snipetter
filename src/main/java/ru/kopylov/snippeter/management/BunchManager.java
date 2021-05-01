package ru.kopylov.snippeter.management;

import ru.kopylov.snippeter.model.Feature;
import ru.kopylov.snippeter.model.Snippet;
import ru.kopylov.snippeter.model.SnippetFeatureBunch;
import ru.kopylov.snippeter.model.Source;
import ru.kopylov.snippeter.utils.EntityManagerHolder;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashMap;

public class BunchManager {
    HashMap<Snippet,ArrayList<Feature>> map = new HashMap<>();
    private EntityManager em;

    public BunchManager() {
        em = EntityManagerHolder.getInstance().getEntityManager();
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

    public HashMap<Snippet, ArrayList<Feature>> fetchSnippetsBySource(Source source){
        map.clear();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<SnippetFeatureBunch> query = cb.createQuery(SnippetFeatureBunch.class);
        Root<SnippetFeatureBunch> from = query.from(SnippetFeatureBunch.class);
        query.select(from);
        query.where(cb.equal(from.get("source"), source));
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
}
