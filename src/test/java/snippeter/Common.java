package snippeter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.kopylov.snippeter.model.Genre;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class Common {
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
    public void shouldStartHibernate() {

        Genre genre = new Genre("fiction");
        em.persist(genre);
        System.out.println("xt sdfg;ldgj");
        em.flush();
    }
}

