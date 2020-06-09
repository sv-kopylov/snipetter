package ru.kopylov.snippeter.view;

import javafx.scene.Node;
import javafx.scene.web.WebView;
import org.apache.log4j.Logger;

import java.io.File;
import java.net.MalformedURLException;

public class TextViewer implements Viewable{
    private WebView webView = new WebView();
    private static Logger logger = Logger.getLogger(TextViewer.class);


    public void setText(String text){
        webView.getEngine().loadContent(text, "text/plain");
    }

    public void setByURL(String path){
        File file = new File(path);
        try {
            webView.getEngine().load(file.toURI().toURL().toString());
            webView.getEngine().reload();
            logger.info("Файл загружен: "+file.toURI().toURL().toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Node getView() {
        return webView;
    }
}
