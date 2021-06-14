package ru.kopylov.snippeter.management;

import org.junit.Before;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class SnippetManagerTest {
    private EntityManager em;
    @Before
    public void init() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory( "snip_pu" );
        em = emf.createEntityManager();
        em.getTransaction().begin();
    }


}