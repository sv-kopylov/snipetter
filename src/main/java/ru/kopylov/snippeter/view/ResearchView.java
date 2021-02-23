package ru.kopylov.snippeter.view;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import ru.kopylov.snippeter.context.Context;
import ru.kopylov.snippeter.controllers.FeaturesBank;
import ru.kopylov.snippeter.management.SnippetDTO;
import ru.kopylov.snippeter.model.Feature;
import ru.kopylov.snippeter.model.Source;


public class ResearchView implements Viewable {
    private static Logger logger = Logger.getLogger(ResearchView.class);

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
    private Stage dialog;
    Scene dialogScene;


    public ResearchView() {
        Context ctx = Context.getInstance();

        featuresBank = ctx.<FeaturesBank>get(FeaturesBank.class);
        featuresView = ctx.<FeaturesView>get(FeaturesView.class);

// Верхнее поле, содержащее отрывок
        snippetText = new Label(Labels.ResearchView_SnipetTextPlaceholder);
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
        root.setStyle(Styles.ResearchView_RootStyle);

// подготовка модального окна
        dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(ctx.getPrimaryStage());
        dialogScene = new Scene(root, Styles.ResearchView_WindowWidth, Styles.ResearchView_WindowHeight);
        dialog.setScene(dialogScene);
    }

// показ модального окна
    public void show(){
        dialog.show();
    }
// обработчики событий
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
