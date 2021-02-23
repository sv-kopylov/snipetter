package ru.kopylov.snippeter.view;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import org.apache.log4j.Logger;
import ru.kopylov.snippeter.controllers.FeaturesBank;
import ru.kopylov.snippeter.management.SnippetDTO;
import ru.kopylov.snippeter.model.Feature;
import ru.kopylov.snippeter.model.Source;
import ru.kopylov.snippeter.utils.Context;

import javax.persistence.EntityManager;


public class SnippetSaveView implements Viewable {
    private static Logger logger = Logger.getLogger(SnippetSaveView.class);

// shared resources
    private FeaturesBank featuresBank;
    private SnippetDTO snippetDTO;
    private Source source;

    private GridPane root;
    private Label snippetText;
    private FeaturesView featuresView;
    private ListView<Feature> listView;
    private Button flushButton;
    private Button remButton;

    public SnippetSaveView() {

        featuresBank = new FeaturesBank();

        snippetText = new Label("Здесь будет в меру длинный и красивый отрывок текста, который нужно будет исследовать");
        snippetText.setStyle("-fx-border-color: black; -fx-background-color: white; -fx-font-size: 140%");
        snippetText.setWrapText(true);

        featuresView = new FeaturesView(featuresBank);
        snippetDTO = new SnippetDTO();

        listView = new ListView(featuresBank.getAllFeatures());
        flushButton = new Button("save snip");
        flushButton.setStyle("-fx-border-color: red;");
        flushButton.setOnAction(flushButtonHandler);

        remButton = new Button("rem");
        remButton.setOnAction(remButtonHandler);


        root = new GridPane();
//        root.setGridLinesVisible(true);
        root.setPadding(new Insets(20));
        root.setHgap(10);
        root.setVgap(10);


        root.add(snippetText, 0,0,5,1);
        root.add(featuresView.getView(),0,1, 3, 4);
        root.add(listView, 3, 2,2,3);

        root.add(remButton, 3, 1);
        root.add(flushButton, 4, 1);
        root.setStyle("-fx-border-color: black;");

        Context.getInstance().put("snippetSaveView", this);

    }

    EventHandler<ActionEvent> remButtonHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            ObservableList<Feature> items = listView.getSelectionModel().getSelectedItems();
            for(Feature f: items){
                featuresBank.removeFeature(f);
            }
        }
    };

    EventHandler<ActionEvent> flushButtonHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            String txt = snippetText.getText();

            if(txt!=null&&txt.length()>0&&source!=null&&featuresBank.getAllFeatures().size()>0){
                    snippetDTO.persist(snippetText.getText(), source, featuresBank.getAllFeatures());
                    setText("");
                    featuresBank.clear();
            } else {
                logger.warn("Attempt to save bad snippet");
            }


        }
    };


    @Override
    public Node getView() {
        return root;
    }

    public void setSource(Source source){
        this.source = source;
    }

    public void setText(String text){
        snippetText.setText(text);
    }


}
