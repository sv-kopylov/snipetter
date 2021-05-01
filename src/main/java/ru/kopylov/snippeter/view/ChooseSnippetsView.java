package ru.kopylov.snippeter.view;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import ru.kopylov.snippeter.context.Context;
import ru.kopylov.snippeter.management.BunchManager;
import ru.kopylov.snippeter.model.Feature;
import ru.kopylov.snippeter.model.Snippet;
import ru.kopylov.snippeter.model.Source;

import java.util.ArrayList;
import java.util.HashMap;

public class ChooseSnippetsView implements Viewable{
    Context ctx = Context.getInstance();
    private ListView<Snippet> listView;

    public ChooseSnippetsView() {
        listView = new ListView<>();

    }

    private BunchManager getBunchManager(){
        return ctx.get(BunchManager.class);
    }

    private Source getCurrentSource(){
        return ctx.get(Source.class);
    }

    public void updateSnips(){
        listView.getItems().clear();
        Source source = getCurrentSource();
        if(source!=null){
            HashMap<Snippet, ArrayList<Feature>> map = getBunchManager().fetchSnippetsBySource(source);
            listView.getItems().addAll(map.keySet());
        }

    }

    @Override
    public Node getView() {
        return listView;
    }
}
