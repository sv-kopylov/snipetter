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
    Context ctx = Context.getInstance();
    private MenuBar menuBar = new MenuBar();

    private Menu sourcesMenu = new Menu("Источники");
    private MenuItem newSourceMenuItem = new MenuItem("Новый источник");
    private MenuItem chooseSourceMenuItem = new MenuItem("Выбрать источник");

    private Menu utilsMenu = new Menu("Экспорт");
    private MenuItem toCSVMenuItem = new MenuItem("Сохранить в CSV");

    private Menu bookmarksMenu = new Menu("Закладки");
    private MenuItem bookmarksMenuItem = new MenuItem("Закладки");
    private MenuItem addBbookmarkMenuItem = new MenuItem("Добавить");



    private Stage viewerSettingsDialog;
    private Stage newSourceViewDialog;
    private Stage choseSourceViewDialog;
    private Stage bookmarksDialog;


    private ChooseSourceView chooseSourceView;
    private ViewerSettingsView viewerSettingsView;
    private BookmarksView bookmarksView;


    private Menu settingsMenu = new Menu("Настройки");
    private MenuItem viewerSettingsMenuItem = new MenuItem("Настройки просмотра текста");


    public MainMenuBar(Stage parent) {
        sourcesMenu.getItems().addAll(newSourceMenuItem, chooseSourceMenuItem);
        utilsMenu.getItems().addAll(toCSVMenuItem);
        settingsMenu.getItems().addAll(viewerSettingsMenuItem);
        bookmarksMenu.getItems().addAll(addBbookmarkMenuItem, bookmarksMenuItem);

        menuBar.getMenus().addAll(sourcesMenu, utilsMenu, settingsMenu, bookmarksMenu);

        newSourceViewDialog = createStage(parent); // создаем диалог
        Pane newSourceViewPane = (Pane)new NewSourceView(newSourceViewDialog).getView(); // создаем вид (панель) для диалога
        setScene(newSourceViewDialog, newSourceViewPane); // устанавливаем вид в диалог

        choseSourceViewDialog = createStage(parent); // создаем диалог
        chooseSourceView = new ChooseSourceView(choseSourceViewDialog); // отдельно создаем класс выбора источника для управления внутренним состоянием
        Pane chooseViewPane = (Pane) chooseSourceView.getView();// создаем вид (панель) для диалога
        setScene(choseSourceViewDialog, chooseViewPane); // устанавливаем вид в диалог

        bookmarksDialog = createStage(parent);
        bookmarksView = new BookmarksView(bookmarksDialog);
        Pane bookmarksPane = (Pane) bookmarksView.getView();
        setScene(bookmarksDialog, bookmarksPane);

        viewerSettingsView = new ViewerSettingsView();
        viewerSettingsDialog = createStageAndSetScene((Pane)viewerSettingsView.getView(), parent);


        newSourceMenuItem.setOnAction(event->{
            newSourceViewDialog.show();
        });

        chooseSourceMenuItem.setOnAction(event ->{
            chooseSourceView.updateSources();
            choseSourceViewDialog.show();
        });

        toCSVMenuItem.setOnAction(event -> {
            CSVSaver saver = new CSVSaver();
            saver.saveDBToCSV("./snippets.csv");
        });

        viewerSettingsMenuItem.setOnAction(event -> {
            viewerSettingsView.updateSettings();
            viewerSettingsDialog.show();
        });

        bookmarksMenuItem.setOnAction(event -> {
            bookmarksView.updateBookmarks();
            bookmarksDialog.show();
        });

        addBbookmarkMenuItem.setOnAction(bookmarksView.getAddBookmarkEventHandler());
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
