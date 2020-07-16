package ru.kopylov.snippeter.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import org.apache.log4j.Logger;
import ru.kopylov.snippeter.controllers.OneCategoryFeatureController;
import ru.kopylov.snippeter.controllers.FeaturesBank;

import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;


/**
 * класс отвечает за отображение одного набора управляющих элементов для управления фичами (признаками, features)
 * один набор соответствует одной категории признаков
 */
public class OneCategoryFeatureView implements Viewable{
    private static Logger logger = Logger.getLogger(OneCategoryFeatureView.class);

    private OneCategoryFeatureController oneCategoryFeatureController;
    private FeaturesBank featuresBank;

    private HBox rootLayout;
    private Label categoryLabel;
    private ObservableList<String> featureNamesList;
    private ChoiceBox<String> choiceBox;
    private Button sendToButton;
    private Button newFeatureButton;
    private Comparator<String> comparator = (s1, s2)-> s1.toLowerCase().compareTo(s2.toLowerCase());


    public OneCategoryFeatureView(OneCategoryFeatureController oneCategoryFeatureController, FeaturesBank featuresBank) {
        this.oneCategoryFeatureController = oneCategoryFeatureController;
        this.featuresBank = featuresBank;
        categoryLabel = new Label(oneCategoryFeatureController.getCategory().name());
        featureNamesList = FXCollections.observableArrayList(oneCategoryFeatureController.getAllFeatureValues());
        Collections.sort(featureNamesList,comparator);

        choiceBox = new ChoiceBox<String>(featureNamesList);
        categoryLabel.setPrefWidth(120);
        choiceBox.setPrefWidth(140);
        sendToButton = new Button("->");
        newFeatureButton = new Button("new");



        sendToButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String str = choiceBox.getValue();
                if(str!=null&&str.length()>0){
                    featuresBank.addFeature(oneCategoryFeatureController.getByName(str));
                }
            }
        });

        newFeatureButton.setOnAction(a ->{
            Optional<String> item = showInputTextDialog();
            if(item.isPresent()){
                if(!oneCategoryFeatureController.isExist(item.get())){
                    oneCategoryFeatureController.addNewFeature(item.get());
                    featureNamesList.add(item.get());
                    Collections.sort(featureNamesList,comparator);

                } else {
                    logger.warn("feature "+item.get()+"already exists in catecory "+ oneCategoryFeatureController.getCategory().name());
                }
            }
        });







        rootLayout = new HBox(10, categoryLabel, choiceBox, sendToButton, newFeatureButton);
    }


    private  Optional<String>  showInputTextDialog() {

        TextInputDialog dialog = new TextInputDialog();

        dialog.setTitle("Новый признак");
        dialog.setHeaderText("в категории "+ oneCategoryFeatureController.getCategory().name());
        dialog.setContentText("Имя:");

        return dialog.showAndWait();

    }


    @Override
    public Node getView() {
        return rootLayout;
    }
}
