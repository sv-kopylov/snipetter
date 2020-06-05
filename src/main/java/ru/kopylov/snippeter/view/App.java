package ru.kopylov.snippeter.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import ru.kopylov.snippeter.controllers.SnippetController;
import ru.kopylov.snippeter.utils.EntityManagerHolder;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


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

        GridPane mainPane = new GridPane();
        mainPane.add(mainMenuBar.getMenuBar(), 0, 0, 2, 1);
        mainPane.add(snippetSaveView.getView(), 0, 1, 2, 1);




        primaryStage.setOnCloseRequest(e -> shutDoun());

        primaryStage.setScene(new Scene((Pane)mainPane, 600, 500));
        primaryStage.show();

    }



    private void shutDoun(){
        logger.debug("Stopping");
        System.out.println("Stopping");
        EntityManagerHolder.getInstance().close();

    }
}
