package ru.kopylov.snippeter.management;

import ru.kopylov.snippeter.context.Context;
import ru.kopylov.snippeter.model.Category;
import ru.kopylov.snippeter.model.Feature;
import ru.kopylov.snippeter.model.Snippet;
import ru.kopylov.snippeter.model.Source;
import ru.kopylov.snippeter.utils.EntityManagerHolder;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SnippetManager {
    Context ctx = Context.getInstance();
    private EntityManager em;
    private HashMap<Snippet, ArrayList<Feature>> snippet2Features = new HashMap<>();

    private HashMap<Category, ArrayList<Snippet>> categories2snippets = new HashMap<>();
    private HashMap<Feature, ArrayList<Snippet>> feature2snippets = new HashMap<>();
    public SnippetManager() {
        em = EntityManagerHolder.getInstance().getEntityManager();
    }

    public List<Snippet> fetchSnippetsBySource(){
        Source source = ctx.get(Source.class);
        if(source==null) return null;

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Snippet> query = cb.createQuery(Snippet.class);
        Root<Snippet> from = query.from(Snippet.class);
        query.select(from);
        query.where(cb.equal(from.get("source"), source));

        return em.createQuery(query).getResultList();
    }

}
