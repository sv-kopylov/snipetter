package ru.kopylov.snippeter.management;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.kopylov.snippeter.model.Genre;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class GenreManagerTest {
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
    public void getGenres() {
        GenreManager gm = new GenreManager(em);
        ArrayList<Genre> a = gm.getGenres();
        for(Genre g: a){
            System.out.println(g);
        }
    }


    @Test
    public void addGenre() {
        GenreManager gm = new GenreManager(em);
        gm.addGenre("Статья");
        gm.addGenre("Научная фантастика");
    }
}