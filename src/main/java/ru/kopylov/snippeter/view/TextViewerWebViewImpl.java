package ru.kopylov.snippeter.view;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;
import org.apache.log4j.Logger;
import ru.kopylov.snippeter.context.Context;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class TextViewerWebViewImpl implements TextViewer{

    private static Logger logger = Logger.getLogger(TextViewerWebViewImpl.class);
    WebEngine engine;
    private WebView webView = new WebView();
    private AnchorPane root;
    private Button back;
    private Button forward;



    public TextViewerWebViewImpl() {
        webView.setContextMenuEnabled(false);
        engine = webView.getEngine();
        createContextMenu(webView);


        back = new Button("<-");
        forward = new Button("->");
        makeButtonResponsive();



        root = new AnchorPane();

        root.getChildren().addAll(webView,back, forward);
        setAlignment(webView, 30., 0.,0.,0.);
        setAlignment(back,0.,0.);
        setAlignment(forward, 0.,42.);
    }

    private void makeButtonResponsive() {
        back.setOnAction(event ->{
            try {
                WebHistory history = engine.getHistory();
                if(history!=null) {
                    history.go(-1);
                }
            }catch (IndexOutOfBoundsException e){
                logger.info("No back in history");
            }
        });
        forward.setOnAction(event -> {
            try {
                WebHistory history = engine.getHistory();
                if(history!=null) {
                    history.go(1);
                }
            } catch (IndexOutOfBoundsException e){
                logger.info("No forward in history");
            }
        });
    }


    @Override
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
        return root;
    }

    public void sendToSnippet(String snip){
        ResearchView researchView = Context.getInstance().get(ResearchView.class);
        researchView.setText(snip);
        researchView.show();
    }

    private void createContextMenu(WebView webView) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem send = new MenuItem(Labels.TextView_SendCtxtMenuItem);
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

    private void setAlignment(Node child, Double top, Double left, Double bottom, Double right){
        AnchorPane.setTopAnchor(child, top);
        AnchorPane.setLeftAnchor(child, left);
        AnchorPane.setBottomAnchor(child, bottom);
        AnchorPane.setRightAnchor(child, right);
    }

    private void setAlignment(Node child, Double top, Double left,  Double right){
        AnchorPane.setTopAnchor(child, top);
        AnchorPane.setLeftAnchor(child, left);
        AnchorPane.setRightAnchor(child, right);
    }
    private void setAlignment(Node child, Double top, Double left){
        AnchorPane.setTopAnchor(child, top);
        AnchorPane.setLeftAnchor(child, left);

    }
}
