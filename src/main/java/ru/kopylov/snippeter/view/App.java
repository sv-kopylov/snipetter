package ru.kopylov.snippeter.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import ru.kopylov.snippeter.utils.Context;
import ru.kopylov.snippeter.utils.EntityManagerHolder;


public class App extends Application {
    private static Logger logger = Logger.getLogger(App.class);


    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Snipetter");
        EntityManagerHolder.getInstance().init("snip_pu");



        SnippetSaveView snippetSaveView = new SnippetSaveView();
        MainMenuBar mainMenuBar = new MainMenuBar(primaryStage);
        TextViewer textViewer = new TextViewer();
        Context.getInstance().put("textViewer",textViewer);

        GridPane mainPane = new GridPane();
        mainPane.setPadding(new Insets(20));
        mainPane.setHgap(10);
        mainPane.setVgap(10);
        ColumnConstraints left = new ColumnConstraints(660);

        ColumnConstraints right = new ColumnConstraints();
        right.setHgrow(Priority.ALWAYS);

        mainPane.getColumnConstraints().addAll(left, right);

        RowConstraints first = new RowConstraints(20);
        RowConstraints sec = new RowConstraints();
        sec.setVgrow(Priority.ALWAYS);

        mainPane.getRowConstraints().addAll(first, sec);



        GridPane.setFillHeight(snippetSaveView.getView(), true);
        GridPane.setFillHeight(textViewer.getView(), true);

        mainPane.add(mainMenuBar.getMenuBar(), 0, 0);
        mainPane.add(snippetSaveView.getView(), 0, 1 );
        mainPane.add(textViewer.getView(), 1, 0, 2, 2);








        primaryStage.setOnCloseRequest(e -> shutDown());

        primaryStage.setScene(new Scene((Pane)mainPane, 800, 600));
        primaryStage.show();

    }



    private void shutDown(){
        logger.debug("Stopping");
        System.out.println("Stopping");
        EntityManagerHolder.getInstance().close();

    }
}
