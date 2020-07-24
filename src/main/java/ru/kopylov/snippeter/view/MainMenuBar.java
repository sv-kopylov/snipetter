package ru.kopylov.snippeter.view;

import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.kopylov.snippeter.fetching.CSVSaver;

public class MainMenuBar {
    private MenuBar menuBar = new MenuBar();
    private Menu sources = new Menu("Источники");
    private MenuItem newSource = new MenuItem("Новый источник");
    private MenuItem chooseSource = new MenuItem("Выбрать источник");

    private Menu utils = new Menu("Экспорт");
    private MenuItem toCSV = new MenuItem("Сохранить в CSV");




    public MainMenuBar(Stage parent) {
        sources.getItems().addAll(newSource, chooseSource);
        utils.getItems().addAll(toCSV);
        menuBar.getMenus().addAll(sources, utils);

        newSource.setOnAction(event->{
            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(parent);
            Scene dialogScene = new Scene((Pane)new NewSourceView(dialog).getView(), 300, 420);
            dialog.setScene(dialogScene);
            dialog.show();
        });

        chooseSource.setOnAction(event ->{
            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(parent);
            Scene dialogScene = new Scene((Pane)new ChooseSourceView(dialog).getView(), 300, 420);
            dialog.setScene(dialogScene);
            dialog.show();
        });

        toCSV.setOnAction(event -> {
            CSVSaver saver = new CSVSaver();
            saver.saveDBToCSV("./snippets.csv");
        });
    }

    public MenuBar getMenuBar() {
        return menuBar;
    }
}
