package ru.kopylov.snippeter.view;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import ru.kopylov.snippeter.context.Context;
import ru.kopylov.snippeter.management.SnippetManager;
import ru.kopylov.snippeter.model.Snippet;

import java.util.List;

public class ChooseSnippetsView implements Viewable{
    private static Logger logger = Logger.getLogger(ChooseSnippetsView.class);

    private Context ctx = Context.getInstance();
    private ListView<Snippet> listView;
    private VBox root;
    private Stage parent;

    private EventHandler<MouseEvent> handler = action->{
        if(action.getClickCount()==2){
            Snippet snippet = listView.getSelectionModel().getSelectedItem();
            openChangeSnippetDialog(snippet);
            logger.info("Snippet selected: "+snippet);
            parent.close();

        }
    };

    public ChooseSnippetsView(Stage parent) {
        this.parent = parent;
        root = new VBox();
        listView = new ListView<>();
        root.getChildren().add(listView);
        root.setPrefWidth(960.);

        listView.setOnMouseClicked(handler);

    }

    private void openChangeSnippetDialog(Snippet snippet) {
        ResearchView researchView = ctx.get(ResearchView.class);
        researchView.setEditModeEnabled(true);
        researchView.prepareForEditMode(snippet);

        researchView.show();
    }

    private SnippetManager getSnippetManages(){
        return ctx.get(SnippetManager.class);
    }

    public void updateSnips(){
        listView.getItems().clear();
        List<Snippet> list = getSnippetManages().fetchSnippetsBySource();
        if(list!=null){
            listView.getItems().addAll(list);
        }
    }

    @Override
    public Node getView() {
        return root;
    }


}
