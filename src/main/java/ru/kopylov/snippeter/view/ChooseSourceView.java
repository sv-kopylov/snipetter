package ru.kopylov.snippeter.view;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import ru.kopylov.snippeter.management.SourceManager;
import ru.kopylov.snippeter.model.Source;
import ru.kopylov.snippeter.utils.Context;

public class ChooseSourceView implements Viewable {
    private static Logger logger = Logger.getLogger(ChooseSourceView.class);

    private SourceManager sourceManager = new SourceManager();
    private VBox root = new VBox();
    private ListView<Source> listView;

    private Button sortByTitle = new Button("по названию");
    private Button sortByAuthor = new Button("по автору");

    public ChooseSourceView(Stage parent) {
        TextViewer textViewer = (TextViewer) Context.getInstance().get("textViewer");
        listView = new ListView<Source>(sourceManager.getSources());
        HBox hBox = new HBox();
        hBox.getChildren().addAll(sortByTitle, sortByAuthor);
        root.getChildren().addAll(hBox, listView);


        sortByTitle.setOnAction(action -> sourceManager.sortByTytle());
        sortByAuthor.setOnAction(action -> sourceManager.sortByAutor());

        listView.setOnMouseClicked(action->{
            if(action.getClickCount()==2){
               Source source = (Source) listView.getSelectionModel().getSelectedItem();
               logger.info(source+" selected");
               textViewer.setByURL(source.getLinkToFile());
               SnippetSaveView snippetSaveView = (SnippetSaveView)Context.getInstance().get("snippetSaveView");
               snippetSaveView.setSource(source);
               parent.close();

            }
        });


    }

    @Override
    public Node getView() {
        return root;
    }
}
