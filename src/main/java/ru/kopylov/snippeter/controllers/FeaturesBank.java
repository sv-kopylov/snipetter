package ru.kopylov.snippeter.controllers;


import org.apache.log4j.Logger;
import ru.kopylov.snippeter.model.Feature;
import ru.kopylov.snippeter.view.App;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FeaturesBank {
    private static Logger logger = Logger.getLogger(FeaturesBank.class);
    private HashMap<String, Feature> map = new HashMap<>();

    public void clear(){
        map.clear();
    }

    public void addFeature(Feature feature){

        map.put(feature.getValue(), feature);
        logger.debug("Adding feature "+ feature);
        logger.trace(map.toString());
    }

    public Feature removeFeature(String featureStr){
        Feature feature = map.get(featureStr);
        map.remove(featureStr);
        return feature;
    }

    public List<Feature> getAllFeatures(){
        return new ArrayList<Feature>(map.values());
    }

}
