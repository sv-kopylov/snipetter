package ru.kopylov.snippeter.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.apache.log4j.Logger;
import ru.kopylov.snippeter.controllers.FeatureCategorySubController;
import ru.kopylov.snippeter.controllers.FeaturesBank;
import ru.kopylov.snippeter.model.Feature;



public class OneCategoryFeatureView {
    private static Logger logger = Logger.getLogger(FeaturesBank.class);

    private FeatureCategorySubController featureCategorySubController;
    private FeaturesBank featuresBank;

    private HBox root;
    private Label category;
    private ObservableList<String> featureNames;
    private ChoiceBox<String> choiceBox;
    private Button sendTo;
    private Button newFeature;



    public OneCategoryFeatureView(FeatureCategorySubController featureCategorySubController, FeaturesBank featuresBank) {
        this.featureCategorySubController = featureCategorySubController;
        this.featuresBank = featuresBank;
        category = new Label(featureCategorySubController.getCategory().name());
        featureNames = FXCollections.observableArrayList(featureCategorySubController.getAllFeatureValues());
        choiceBox = new ChoiceBox<String>(featureNames);
        sendTo = new Button("->");
        newFeature = new Button("new");

        sendTo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String str = choiceBox.getValue();
                if(str!=null&&str.length()>0){
                    featuresBank.addFeature(featureCategorySubController.getByName(str));
                }



            }
        });







        root = new HBox(10, category, choiceBox, sendTo, newFeature);
    }

    public HBox getRoot(){
        return root;
    }


}
