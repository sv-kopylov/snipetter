package ru.kopylov.snippeter.view;

import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import org.apache.log4j.Logger;
import ru.kopylov.snippeter.text.PlainTextProcessor;


public class TextViewerTextAreaImpl implements TextViewer {

    private static Logger logger = Logger.getLogger(TextViewerTextAreaImpl.class);
    PlainTextProcessor textProcessor;

    private TextArea textArea;
    private AnchorPane root;

    public TextViewerTextAreaImpl() {
        textArea = new TextArea();
        textArea.setWrapText(true);
        textArea.setStyle(Styles.TextView_BorderColor);
        root = new AnchorPane();
        root.getChildren().add(textArea);
//        root.setStyle(Styles.TextView_BorderColor);
        setAlignment(textArea, 0., 0.,0.,0.);
    }

    @Override
    public void setByURL(String path) {
        textProcessor = new PlainTextProcessor(path);
        textArea.setText(textProcessor.getText());
        logger.info("Файл загружен: " + path);
    }

    @Override
    public void sendToSnippet(String snip) {

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
}
