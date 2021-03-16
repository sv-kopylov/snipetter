package ru.kopylov.snippeter.view;

import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.kopylov.snippeter.context.Context;
import ru.kopylov.snippeter.fetching.CSVSaver;

public class MainMenuBar {
    private MenuBar menuBar = new MenuBar();

    private Menu sources = new Menu("Источники");
    private MenuItem newSource = new MenuItem("Новый источник");
    private MenuItem chooseSource = new MenuItem("Выбрать источник");

    private Menu utils = new Menu("Экспорт");
    private MenuItem toCSV = new MenuItem("Сохранить в CSV");

    Stage viewerSettingsDialog;
    Stage newSourceViewDialog;
    Stage choseSourceViewDialog;
    ChooseSourceView chooseSourceView;
    private Menu settings = new Menu("Настройки");
    private MenuItem viewerSettings = new MenuItem("Настройки просмотра текста");


    public MainMenuBar(Stage parent) {
        sources.getItems().addAll(newSource, chooseSource);
        utils.getItems().addAll(toCSV);
        settings.getItems().addAll(viewerSettings);
        menuBar.getMenus().addAll(sources, utils, settings);

        newSourceViewDialog = createStage(parent);
        Pane newSourceViewPane = (Pane)new NewSourceView(newSourceViewDialog).getView();
        setScene(newSourceViewDialog, newSourceViewPane);

        choseSourceViewDialog = createStage(parent);
        chooseSourceView = new ChooseSourceView(choseSourceViewDialog);
        Pane chooseViewPane = (Pane) chooseSourceView.getView();
        setScene(choseSourceViewDialog, chooseViewPane);

        ViewerSettingsView vsw = Context.getInstance().get(ViewerSettingsView.class);
        viewerSettingsDialog = createStageAndSetScene((Pane)vsw.getView(), parent);


        newSource.setOnAction(event->{
            newSourceViewDialog.show();
        });

        chooseSource.setOnAction(event ->{
            chooseSourceView.updateSources();
            choseSourceViewDialog.show();
        });

        toCSV.setOnAction(event -> {
            CSVSaver saver = new CSVSaver();
            saver.saveDBToCSV("./snippets.csv");
        });

        viewerSettings.setOnAction(event -> {
            viewerSettingsDialog.show();
        });
    }

    private Stage createStageAndSetScene(Pane pane, Stage owner){
        Stage dialog = createStage(owner);
        Scene dialogScene = new Scene(pane);
        dialog.setScene(dialogScene);
        return dialog;
    }
// создаем стейдж, устанавливаем параметры и передаем ему владельца
    private Stage createStage(Stage owner){
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(owner);
        return dialog;
    }

    private void setScene(Stage dialog, Pane pane){
        Scene dialogScene = new Scene(pane);
        dialog.setScene(dialogScene);
    }

    public MenuBar getMenuBar() {
        return menuBar;
    }
}
