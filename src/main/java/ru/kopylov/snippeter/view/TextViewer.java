package ru.kopylov.snippeter.view;

import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.apache.log4j.Logger;
import ru.kopylov.snippeter.utils.Context;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class TextViewer implements Viewable{

    private static Logger logger = Logger.getLogger(TextViewer.class);
    private WebView webView = new WebView();
    WebEngine engine;



    public TextViewer() {
        webView.setContextMenuEnabled(false);
        engine = webView.getEngine();
        createContextMenu(webView);



    }


    public void setByURL(String path){
        File file = new File(path);
        try {
            URL url = file.toURI().toURL();
            engine.load(url.toString());
            logger.info("Файл загружен: "+url.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Node getView() {
        return webView;
    }

    private void sendToSnippet(String snip){
        SnippetSaveView snippetSaveView = (SnippetSaveView) Context.getInstance().get("snippetSaveView");
        snippetSaveView.setText(snip);
    }

    private void createContextMenu(WebView webView) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem send = new MenuItem("send");
        send.setOnAction(e -> {
            String selection = (String) webView.getEngine()
                    .executeScript("window.getSelection().toString()");
            sendToSnippet(selection);
            logger.debug(selection);
        });
        contextMenu.getItems().addAll(send);

        webView.setOnMousePressed(e -> {
            if (e.getButton() == MouseButton.SECONDARY) {
                contextMenu.show(webView, e.getScreenX(), e.getScreenY());
            } else {
                contextMenu.hide();
            }
        });
    }
}
