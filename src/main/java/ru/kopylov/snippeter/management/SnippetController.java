package ru.kopylov.snippeter.management;

import ru.kopylov.snippeter.model.Feature;
import ru.kopylov.snippeter.model.Source;

import javax.persistence.EntityManager;
import java.util.HashMap;

public class SnippetController {
    private EntityManager em;
    private Source source;
    private FeatureManager featureManager;
    private HashMap<String, Feature> availabelFeatures;


    private HashMap<String, Feature> chosedFeatures;

    public SnippetController(EntityManager em, Source source, FeatureManager featureManager) {
        this.em = em;
        this.source = source;
        this.featureManager = featureManager;

    }
}
