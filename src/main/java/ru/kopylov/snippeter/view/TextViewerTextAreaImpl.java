package ru.kopylov.snippeter.view;

import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import org.apache.log4j.Logger;
import ru.kopylov.snippeter.context.Context;
import ru.kopylov.snippeter.text.PlainTextProcessor;

/**
 * Класс для просмотра текста и выбора отрывков на базе TextArea
 */
public class TextViewerTextAreaImpl implements TextViewer {

    private static Logger logger = Logger.getLogger(TextViewerTextAreaImpl.class);
    PlainTextProcessor textProcessor;

    private TextArea textArea;
    private AnchorPane root;
    ResearchView researchView = Context.getInstance().<ResearchView>get(ResearchView.class);

    public TextViewerTextAreaImpl() {
        textArea = new TextArea();
        textArea.setWrapText(true);
        textArea.setStyle(Styles.TextView_TextParams);

        createContextMenu(textArea);
        root = new AnchorPane();
        root.getChildren().add(textArea);
        setAlignment(textArea, 0., 0.,0.,0.);
    }

    @Override
    public void setByURL(String path) {
        textProcessor = new PlainTextProcessor(path);
        textArea.setText(textProcessor.getText());
        textArea.setEditable(false);

        logger.info("Файл загружен: " + path);
    }

    @Override
    public void sendToSnippet(String snip) {
        researchView.setText(snip);
        researchView.show();
    }

    @Override
    public void applySettings() {

    }

    @Override
    public Node getView() {
        return root;
    }

    private void setAlignment(Node child, Double top, Double left, Double bottom, Double right){
        AnchorPane.setTopAnchor(child, top);
        AnchorPane.setLeftAnchor(child, left);
        AnchorPane.setBottomAnchor(child, bottom);
        AnchorPane.setRightAnchor(child, right);
    }


    private void createContextMenu(TextArea textArea) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem send = new MenuItem(Labels.TextView_SendCtxtMenuItem);
        send.setOnAction(e -> {
            String selection = textArea.getSelectedText();
            sendToSnippet(selection);
            logger.debug(selection);
        });
        contextMenu.getItems().addAll(send);
        textArea.setContextMenu(contextMenu);

    }
}
