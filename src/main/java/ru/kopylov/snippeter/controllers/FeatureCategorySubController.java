package ru.kopylov.snippeter.controllers;

import ru.kopylov.snippeter.management.FeatureManager;
import ru.kopylov.snippeter.model.Category;
import ru.kopylov.snippeter.model.Feature;

import java.util.ArrayList;
import java.util.HashMap;

public class FeatureCategorySubController {
    private Category category;
    private FeatureManager featureManager;
    private HashMap<String, Feature> availabelFeatures;

    public FeatureCategorySubController(Category category, FeatureManager featureManager) {
        this.category = category;
        this.featureManager = featureManager;
        availabelFeatures = new HashMap<>();
        for(Feature feature: featureManager.getFeatures().get(category)){
            availabelFeatures.put(feature.getValue(), feature);
        }

    }

    public ArrayList<String> getAllFeatureValues(){
        return new ArrayList<String>(availabelFeatures.keySet());
    }

    public Category getCategory() {
        return category;
    }

    public void addNewFeature(String featureStr){
            Feature newFeature = featureManager.createFeature(category, featureStr);
            availabelFeatures.put(newFeature.getValue(), newFeature);
    }

    public Feature getByName(String name){
        return availabelFeatures.get(name);
    }
}
