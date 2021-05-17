package ru.kopylov.snippeter.view;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Logger;
import ru.kopylov.snippeter.context.Context;
import ru.kopylov.snippeter.controllers.FeaturesBank;
import ru.kopylov.snippeter.management.BunchManager;
import ru.kopylov.snippeter.management.SnippetDTO;
import ru.kopylov.snippeter.model.Feature;
import ru.kopylov.snippeter.model.Snippet;
import ru.kopylov.snippeter.model.Source;
import ru.kopylov.snippeter.utils.AnghorBinder;

import java.util.List;


public class ResearchView implements Viewable {
    private static Logger logger = Logger.getLogger(ResearchView.class);

    private Snippet editedSnippet;
    private final EventHandler<ActionEvent> flushButtonHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            if(isEditModeEnabled()){
                flushEditedSnippet();
            } else  {
                flushNewSnippet();
            }
        }
    };


// shared resources
    private FeaturesBank featuresBank;
    private SnippetDTO snippetDTO;
    private Source source;
    private Scene dialogScene;

    private AnchorPane root;
    private TextArea snippetText;
    private FeaturesView featuresView;
    private ListView<Feature> listView;
    private Button flushButton;
    private Button remButton;
    private Stage dialog;
    private AliasView aliasView;

    private void flushNewSnippet(){
        String txt = snippetText.getText();

        if (txt != null && txt.length() > 0 && source != null) {
            snippetDTO.persist(snippetText.getText(), source, featuresBank.getAllFeatures());
            setText("");
            featuresBank.clear();
        } else {
            logger.warn("Attempt to save bad snippet");
        }

        dialog.hide();
    }
// обработчики событий
   private EventHandler<ActionEvent> remButtonHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            ObservableList<Feature> items = listView.getSelectionModel().getSelectedItems();
            for(Feature f: items){
                featuresBank.removeFeature(f);
            }
        }
    };

// показ модального окна
    public void show(){
        dialog.show();
    }
    @Setter
    @Getter
    private boolean editModeEnabled = false;

    public ResearchView() {
        Context ctx = Context.getInstance();

        featuresBank = ctx.<FeaturesBank>get(FeaturesBank.class);
        featuresView = ctx.<FeaturesView>get(FeaturesView.class);

// Верхнее поле, содержащее отрывок
        snippetText = new TextArea(Labels.ResearchView_SnipetTextPlaceholder);
        snippetText.setPrefHeight(130);
        snippetText.setStyle(Styles.ResearchView_TexstStyle);
        snippetText.setWrapText(true);

//        Больше нигде не используется, в контекст не помещаю (пока?)
        snippetDTO = new SnippetDTO();

//        Больше нигде не используется, в контекст не помещаю (пока?)
        listView = new ListView(featuresBank.getAllFeatures());
        flushButton = new Button(Labels.ResearchView_FlushButtonText);
        flushButton.setStyle(Styles.ResearchView_FlushButtonStyle);
        flushButton.setOnAction(flushButtonHandler);

        remButton = new Button(Labels.ResearchView_RemButtonText);
        remButton.setOnAction(remButtonHandler);

        root = new AnchorPane();
        Node featuresViewView = featuresView.getView();
        aliasView = new AliasView();

        root.getChildren().addAll(snippetText, aliasView.getView(), featuresViewView, listView, remButton, flushButton);



//        root.setGridLinesVisible(true);
        root.setPadding(new Insets(20));
        AnghorBinder.bind(snippetText, 10.,10.,null, 10.);
        AnghorBinder.bind(aliasView.getView(), 150.,10.,null, null);
        AnghorBinder.bind(featuresViewView, 270.,10.,10., null);
        AnghorBinder.bind(flushButton, 150., 500.,null,null);
        AnghorBinder.bind(remButton, 150., 450.,null,null);
        AnghorBinder.bind(listView, 190.,450.,10., 10.);

        root.setStyle(Styles.ResearchView_RootStyle);

// подготовка модального окна
        dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(ctx.getPrimaryStage());
        dialogScene = new Scene(root, Styles.ResearchView_WindowWidth, Styles.ResearchView_WindowHeight);
        dialog.setScene(dialogScene);
    }

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

    // часть кода для редактирования отрывка
    public void prepareForEditMode(Snippet snippet){
        setEditModeEnabled(true);
        editedSnippet = snippet;
        setText(snippet.getSnippet());
        BunchManager bunchManager = Context.getInstance().get(BunchManager.class);
        List<Feature> features = bunchManager.getFeatures(snippet);
        listView.getItems().clear();
        listView.getItems().addAll(features);
    }

    private void flushEditedSnippet(){
        BunchManager bunchManager = Context.getInstance().get(BunchManager.class);
        bunchManager.deleteBunches(editedSnippet);
            snippetDTO.persist(editedSnippet, featuresBank.getAllFeatures());
            setText("");
            featuresBank.clear();
        setEditModeEnabled(false);

        listView.getItems().clear();
        setText("");
        dialog.hide();
    }
    public void prepareForNewMode(String text){
        setEditModeEnabled(false);
        setText(text);
        listView.getItems().clear();

    }

}
