package ru.kopylov.snippeter.management;

import ru.kopylov.snippeter.model.Feature;
import ru.kopylov.snippeter.model.Snippet;
import ru.kopylov.snippeter.model.SnippetFeatureBunch;
import ru.kopylov.snippeter.utils.EntityManagerHolder;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashMap;

public class BunchManager {
    HashMap<Snippet,ArrayList<Feature>> map = new HashMap<>();
    private EntityManager em = EntityManagerHolder.getInstance().getEntityManager();

    public void fetchAllSnippets(){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<SnippetFeatureBunch> query = cb.createQuery(SnippetFeatureBunch.class);
        Root<SnippetFeatureBunch> from = query.from(SnippetFeatureBunch.class);
        query.select(from);
        em.createQuery(query).getResultStream().forEach(this::treatOneBunch);

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
