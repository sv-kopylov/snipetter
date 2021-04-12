package ru.kopylov.snippeter.controllers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.log4j.Logger;
import ru.kopylov.snippeter.model.Feature;

/**
 * Класс для работы с фичами в рантайме
 */
public class FeaturesBank {
    private static Logger logger = Logger.getLogger(FeaturesBank.class);
    private ObservableList<Feature> list = FXCollections.observableArrayList();

    public void clear(){
        list.clear();
    }

    public void addFeature(Feature feature){
        if(!list.contains(feature)) {
            list.add(feature);
            logger.debug("Adding feature " + feature);
        }
    }

    public Feature removeFeature(String featureStr){

        for(Feature f: list){
            if(f.toString().equalsIgnoreCase(featureStr)){
                list.remove(f);
                return f;
            }
        }
        return null;
    }

    public Feature removeFeature(Feature feature){
        logger.debug("removing feature "+feature);
        list.remove(feature);
        logger.debug(list);
        return feature;
    }

    public ObservableList<Feature> getAllFeatures(){
        return list;
    }
}
