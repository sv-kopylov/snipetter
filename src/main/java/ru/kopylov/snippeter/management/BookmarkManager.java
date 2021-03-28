package ru.kopylov.snippeter.management;

import ru.kopylov.snippeter.model.Bookmark;
import ru.kopylov.snippeter.model.Source;
import ru.kopylov.snippeter.utils.EmTAProxy;
import ru.kopylov.snippeter.utils.EntityManagerHolder;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BookmarkManager {
    private EmTAProxy emProxy;
    private EntityManager em;

    public BookmarkManager() {
        em = EntityManagerHolder.getInstance().getEntityManager();
        emProxy = EntityManagerHolder.getInstance().getEmTAProxy();
    }

    public List<Bookmark> getBookmarks(Source source){
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Bookmark> query = cb.createQuery(Bookmark.class);
        Root<Bookmark> from = query.from(Bookmark.class);

        query.select(from);
        query.where(cb.equal(from.get("source"), source));
        return em.createQuery(query).getResultStream().sorted(Comparator.comparingInt(Bookmark::getPosition)).collect(Collectors.toList());
    }

    public void persist(Bookmark bookmark){
        emProxy.persist(bookmark);
    }

    public void merge(Bookmark bookmark){
        emProxy.merge(bookmark);
    }


}
