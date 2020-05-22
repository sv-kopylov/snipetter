package ru.kopylov.snippeter.utils;


import javax.persistence.EntityManager;

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
}
