package ru.kopylov.snippeter.utils;


import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.stream.Stream;

/**
 * оборачивает сохранение в транзакцию
 */
public class EmTAProxy {
    private EntityManager em;

    public EmTAProxy(EntityManager em) {
        this.em = em;
    }

    public void persist(Object entity){
        if(!em.getTransaction().isActive()){
            em.getTransaction().begin();
        }
        em.persist(entity);
        em.flush();
        em.getTransaction().commit();

    }

    public void merge(Object entity){
        if(!em.getTransaction().isActive()){
            em.getTransaction().begin();
        }
        em.merge(entity);
        em.flush();
        em.getTransaction().commit();

    }

    public <T> Stream <T> getAllInstancesStream (Class clazz){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(clazz);
        Root<T> from = query.from(clazz);
        query.select(from);
       return em.createQuery(query).getResultStream();

    }
}
