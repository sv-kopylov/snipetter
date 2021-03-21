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

    private Stage viewerSettingsDialog;
    private Stage newSourceViewDialog;
    private Stage choseSourceViewDialog;


    private ChooseSourceView chooseSourceView;
    private ViewerSettingsView viewerSettingsView;

    private Menu settings = new Menu("Настройки");
    private MenuItem viewerSettings = new MenuItem("Настройки просмотра текста");


    public MainMenuBar(Stage parent) {
        sources.getItems().addAll(newSource, chooseSource);
        utils.getItems().addAll(toCSV);
        settings.getItems().addAll(viewerSettings);
        menuBar.getMenus().addAll(sources, utils, settings);

        newSourceViewDialog = createStage(parent); // создаем диалог
        Pane newSourceViewPane = (Pane)new NewSourceView(newSourceViewDialog).getView(); // создаем вид (панель) для диалога
        setScene(newSourceViewDialog, newSourceViewPane); // устанавливаем вид в диалог

        choseSourceViewDialog = createStage(parent); // создаем диалог
        chooseSourceView = new ChooseSourceView(choseSourceViewDialog); // отдельно создаем класс выбора источника для управления внутренним состоянием
        Pane chooseViewPane = (Pane) chooseSourceView.getView();// создаем вид (панель) для диалога
        setScene(choseSourceViewDialog, chooseViewPane); // устанавливаем вид в диалог

        viewerSettingsView = new ViewerSettingsView();
        viewerSettingsDialog = createStageAndSetScene((Pane)viewerSettingsView.getView(), parent);


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
            viewerSettingsView.updateSettings();
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
