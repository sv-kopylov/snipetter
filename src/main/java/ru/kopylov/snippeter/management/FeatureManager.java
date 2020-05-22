package ru.kopylov.snippeter.management;

import ru.kopylov.snippeter.model.Category;
import ru.kopylov.snippeter.model.Feature;
import ru.kopylov.snippeter.utils.EmTAProxy;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashMap;

public class FeatureManager {
    private final HashMap<Category, ArrayList<Feature>> features;
    private EntityManager em;
    private EmTAProxy emTAProxy;

    public FeatureManager(EntityManager em) {
        this.em=em;
        emTAProxy = new EmTAProxy(em);
        this.features = new HashMap<>();
        for(Category cat: Category.values()){
            features.put(cat, new ArrayList<>());
        }
        completteFeatures();

    }

    public void addFeature(Feature feature){
        emTAProxy.persist(feature);
        features.get(feature.getCategory()).add(feature);
    }

    public Feature createFeature(String categoryName, String featureValue){
        Feature feature = new Feature(Category.valueOf(categoryName), featureValue);
        addFeature(feature);
        return feature;
    }

    public Feature createFeature(Category category, String featureValue){
        Feature feature = new Feature(category, featureValue);
        addFeature(feature);
        return feature;
    }

    public void completteFeatures(){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Feature> query = cb.createQuery(Feature.class);
        Root<Feature> from = query.from(Feature.class);
        query.select(from);
        em.createQuery(query).getResultStream().forEach(f -> features.get(f.getCategory()).add(f));


    }

    public HashMap<Category, ArrayList<Feature>> getFeatures() {
        return features;
    }
}
