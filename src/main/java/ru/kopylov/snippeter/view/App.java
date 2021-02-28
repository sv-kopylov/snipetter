package ru.kopylov.snippeter.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import ru.kopylov.snippeter.context.ApplicationInitializer;
import ru.kopylov.snippeter.context.Context;
import ru.kopylov.snippeter.utils.EntityManagerHolder;


public class App extends Application {
    private static Logger logger = Logger.getLogger(App.class);


    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("snipeter");
        Context ctx = Context.getInstance();
        ctx.setPrimaryStage(primaryStage);

        ApplicationInitializer.init();

        MainMenuBar mainMenuBar = new MainMenuBar(primaryStage);

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


        GridPane.setFillHeight(ctx.<TextViewer>get(TextViewer.class).getView(), true);

        mainPane.add(mainMenuBar.getMenuBar(), 0, 0, 2, 1);

        // конкретная реализация зависит от того, что мы добавили в ApplicationInitializer
        mainPane.add(ctx.<TextViewer>get(TextViewer.class).getView(), 0, 1, 2, 1);


        primaryStage.setOnCloseRequest(e -> shutDown());

        primaryStage.setScene(new Scene((Pane)mainPane, 1000, 600));
        primaryStage.show();

    }

    private void completeMainScene(Stage primaryStage){

    }


    private void shutDown(){
        logger.debug("Stopping");
        System.out.println("Stopping");
        EntityManagerHolder.getInstance().close();

    }
}
