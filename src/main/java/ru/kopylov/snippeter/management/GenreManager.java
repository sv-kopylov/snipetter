package ru.kopylov.snippeter.management;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.kopylov.snippeter.model.Genre;
import ru.kopylov.snippeter.utils.EmTAProxy;
import ru.kopylov.snippeter.utils.EntityManagerHolder;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collections;

public class GenreManager {
    private EntityManager em = EntityManagerHolder.getInstance().getEntityManager();
    private ObservableList<Genre> genres = FXCollections.observableArrayList();
    private EmTAProxy emProxy;

    public GenreManager() {
        emProxy = new EmTAProxy(em);
        completeGenres();
    }

    private void completeGenres(){
        emProxy.<Genre>getAllInstancesStream(Genre.class).forEach(g -> genres.add(g));
        sort();
    }

    public ObservableList<Genre> getGenres() {
        return genres;
    }

    public void addGenre(String newGenre){
        Genre genre = new Genre(newGenre);
        emProxy.persist(genre);
        genres.clear();
        completeGenres();

    }

    public boolean isExist(String genreName){
        for(Genre g: genres){
            if(g.toString().equalsIgnoreCase(genreName.trim())) return true;
        }
        return false;
    }

    public void sort(){
        Collections.sort(genres, (g1, g2)-> g1.getName().compareTo(g2.getName()));
    }
}
