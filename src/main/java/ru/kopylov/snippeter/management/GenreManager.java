package ru.kopylov.snippeter.management;

import ru.kopylov.snippeter.model.Genre;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;

public class GenreManager {
    private EntityManager em;
    private ArrayList<Genre> genres = new ArrayList<>();

    public GenreManager(EntityManager em) {
        this.em = em;
        completeGenres();
    }

    private void completeGenres(){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Genre> query = cb.createQuery(Genre.class);
        Root<Genre> from = query.from(Genre.class);
        query.select(from);
        em.createQuery(query).getResultStream().forEach(g -> genres.add(g));
    }

    public ArrayList<Genre> getGenres() {
        return genres;
    }

    public void addGenre(String newGenre){
        Genre genre = new Genre(newGenre);
        em.persist(genre);
        em.flush();
        genres.clear();
        completeGenres();

    }
}
