package ru.kopylov.snippeter.view.snippetstable;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import ru.kopylov.snippeter.context.Context;
import ru.kopylov.snippeter.management.BunchManager;
import ru.kopylov.snippeter.management.SnippetManager;
import ru.kopylov.snippeter.model.Feature;
import ru.kopylov.snippeter.model.Snippet;
import ru.kopylov.snippeter.model.Source;
import ru.kopylov.snippeter.view.ResearchView;
import ru.kopylov.snippeter.view.Viewable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChooseSnippetsView implements Viewable {
    private static Logger logger = Logger.getLogger(ChooseSnippetsView.class);

    private Context ctx = Context.getInstance();
    private SnippetsTableView snippetsTableView = new SnippetsTableView();


    private VBox root;
    private Stage parent;

    private EventHandler<MouseEvent> handler = action->{
        if(action.getClickCount()==2){
            Snippet snippet = snippetsTableView.getTableView().getSelectionModel().getSelectedItem().getSnippet();
            openChangeSnippetDialog(snippet);
            logger.info("Snippet selected: "+snippet);
            parent.close();

        }
    };

    public ChooseSnippetsView(Stage parent) {
        this.parent = parent;
        root = new VBox();
        snippetsTableView.getTableView().setOnMouseClicked(handler);

        root.getChildren().add(snippetsTableView.getTableView());
        root.setPrefWidth(960.);
    }

    private void openChangeSnippetDialog(Snippet snippet) {
        ResearchView researchView = ctx.get(ResearchView.class);
        researchView.prepareForEditMode(snippet);
        researchView.show();
    }

    private SnippetManager getSnippetManages(){
        return ctx.get(SnippetManager.class);
    }
    
    private BunchManager getBunchManager(){
        return ctx.get(BunchManager.class);
    }

    public void updateSnips(){
        HashMap<Snippet, ArrayList<Feature>> map = getBunchManager().fetchAllSnippets(ctx.get(Source.class));
        snippetsTableView.clear();
        for (Map.Entry<Snippet, ArrayList<Feature>> entry: map.entrySet()){
            SnippetRepresentation sr = new SnippetRepresentation();
            sr.setValues(entry.getKey(), entry.getValue());
            snippetsTableView.addRow(sr);
        }
    }
    
    @Override
    public Node getView() {
        return root;
    }
}