package ru.kopylov.snippeter.view;

import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.apache.log4j.Logger;
import ru.kopylov.snippeter.context.Context;
import ru.kopylov.snippeter.model.ViewerSettings;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class TextViewerWebViewImpl implements TextViewer{

    private final double gap4menuPanel = 30.;
//
    private static Logger logger = Logger.getLogger(TextViewerWebViewImpl.class);
    TextViewerMenuPanel menuPanel;

//    веб - движок и прибамбасы
    private Context ctx = Context.getInstance();
    private WebEngine engine;
    private WebView webView;
//    панели
    private AnchorPane root;

    public TextViewerWebViewImpl() {
        webView = ctx.get(WebView.class);
        webView.setContextMenuEnabled(false);
        engine = webView.getEngine();
        createContextMenu(webView);
        webView.setPrefWidth(960);

        menuPanel = new TextViewerMenuPanel();
        root = new AnchorPane();

        root.getChildren().addAll(webView, menuPanel.getView());
        setAlignment(webView, gap4menuPanel, 0. , 0.);
        setAlignment(menuPanel.getView(),0.,0.);


    }
    @Override
    public void applySettings(){

        webView.setPrefWidth(getSettings().getTextViewWidth());
        setAlignment(webView, gap4menuPanel, getSettings().getLeftAlignment() , 0.);
        webView.setFontScale(getSettings().getFontScale());

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
        researchView.prepareForNewMode(snip);
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

    private void setAlignment(Node child, Double top, Double left,  Double bottom){
        AnchorPane.setTopAnchor(child, top);
        AnchorPane.setLeftAnchor(child, left);
        AnchorPane.setBottomAnchor(child, bottom);
    }
    private void setAlignment(Node child, Double top, Double left){
        AnchorPane.setTopAnchor(child, top);
        AnchorPane.setLeftAnchor(child, left);

    }

    private ViewerSettings getSettings(){
        return ctx.get(ViewerSettings.class);
    }
    public int getYPos(){
        return (int)engine.executeScript("window.scrollY;");
    }
    public int getMaxYPos(){
        return (int)engine.executeScript("document.body.scrollHeight;");
    }
    public void scrollTo(int pos){
        String script = String.format("window.scrollTo(0, %d);", pos);
        engine.executeScript(script);

    }

}
