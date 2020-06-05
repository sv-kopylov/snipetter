package ru.kopylov.snippeter.view;

import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainMenuBar {
    private MenuBar menuBar = new MenuBar();
    private Menu sources = new Menu("Источники");
    private MenuItem newSource = new MenuItem("Новый источник");
    private MenuItem chooseSource = new MenuItem("Выбрать источник");

    private NewSourceView newSourceView;


    public MainMenuBar(Stage parent) {
        sources.getItems().addAll(newSource, chooseSource);
        menuBar.getMenus().add(sources);

        newSource.setOnAction(event->{
            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(parent);
            newSourceView = new NewSourceView(dialog);
            Scene dialogScene = new Scene((Pane)newSourceView.getView(), 300, 420);
            dialog.setScene(dialogScene);
            dialog.show();
        });
    }

    public MenuBar getMenuBar() {
        return menuBar;
    }
}
