package ru.kopylov.snippeter.view;

import com.sun.webkit.WebPage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;
import org.apache.log4j.Logger;
import ru.kopylov.snippeter.context.Context;
import ru.kopylov.snippeter.model.ViewerSettings;

import java.lang.reflect.Field;

public class TextViewerMenuPanel implements Viewable {
    private static Logger logger = Logger.getLogger(TextViewerMenuPanel.class);
    Context ctx = Context.getInstance();

    private WebView webView;
    private WebEngine engine;
    private WebHistory history;
    private WebPage page;

    private HBox topPanel;

    //    кнопки
    private Button backButton;
    private Button forwardButton;
    private Button searchButton;
    private Button toTopButton;
    private Button fontBiggerButton;
    private Button fontSmallerButton;


    private MenuButton bookmarksMenuButton;
    private BookmarksView bookmarksView;


    //    текстовые поля
    private TextField searchText;

    //     ctor
    public TextViewerMenuPanel() {
//      получаем необходимый бэк и настраиваем его
        webView = ctx.get(WebView.class);
        engine = webView.getEngine();
        history = engine.getHistory();
        history.setMaxSize(2);
        initPage();


//      инициализируем визуальные компоненты
        backButton = new Button("<-");
        backButton.setDisable(true);  //  при запуске  кнопки навигации по истории отключены
        forwardButton = new Button("->");
        forwardButton.setDisable(true);  //  при запуске  кнопки навигации по истории отключены
        searchButton = new Button("поиск");
        searchText = new TextField();
        toTopButton = new Button("top");
        fontBiggerButton = new Button("+");
        fontBiggerButton.setPrefWidth(32);
        fontSmallerButton = new Button("-");
        fontSmallerButton.setPrefWidth(32);



//      добавляем обработку событий к визуальным компонентам
        makeButtonsDisableAble(); // отключение кнопок навигации по истории если идти некуда, порядок имеет значение, должно вызываться после инициализации history
        setActionProcessingToAllElements();

//      добавление всего добра на панель
        topPanel = new HBox();
        topPanel.getChildren().add(backButton);
        topPanel.getChildren().add(forwardButton);
        topPanel.getChildren().add(getSeparator());
        topPanel.getChildren().add(searchText);
        topPanel.getChildren().add(searchButton);
        topPanel.getChildren().add(getSeparator());
        topPanel.getChildren().add(toTopButton);
        topPanel.getChildren().add(getSeparator());
        topPanel.getChildren().add(fontBiggerButton);
        topPanel.getChildren().add(fontSmallerButton);



    }

//     грязный хак с использованием рефлексии, но этой самы простой способ включить поиск на странице
    private void initPage() {
        try {
            Field pageField = engine.getClass().getDeclaredField("page");
            pageField.setAccessible(true);
            page = (com.sun.webkit.WebPage) pageField.get(engine);
        } catch (Exception e) {

        }
    }

    /**
     * Объединяет установку действий для всех элементов
     */
    private void setActionProcessingToAllElements() {
//        навигация по истории назад
        backButton.setOnAction(event -> {
            try {
                if (history != null) {
                    history.go(-1);
                }
            } catch (IndexOutOfBoundsException e) {
                logger.info("No back in history");
            }
        });
//        навигация по истории вперед
        forwardButton.setOnAction(event -> {
            try {
                if (history != null) {
                    history.go(1);
                }
            } catch (IndexOutOfBoundsException e) {
                logger.info("No forward in history");
            }
        });
//      поиск на странице
        searchButton.setOnAction(event -> {
            if (page != null) {
                page.find(searchText.getText(), true, true, false);
            }
        });

//        кнопка наверх
        toTopButton.setOnAction(event->{
            engine.executeScript("window.scrollTo( 0, 0);");
        });

//      кнопки размера шрифта
        fontBiggerButton.setOnAction(event -> {
            double currentFontScale = getSettings().getFontScale();
            currentFontScale+=0.1;
            getSettings().setFontScale(currentFontScale);
// если прошло валидацию сеттером
            currentFontScale = getSettings().getFontScale();
            webView.setFontScale(currentFontScale);
//            TODO сохранять ли сразу в базу ?
        });

        fontSmallerButton.setOnAction(event -> {
            double currentFontScale = getSettings().getFontScale();
            currentFontScale-=0.1;
            getSettings().setFontScale(currentFontScale);
// если прошло валидацию сеттером
            currentFontScale = getSettings().getFontScale();
            webView.setFontScale(currentFontScale);
//            TODO сохранять ли сразу в базу ?
        });
    }

    public int getYPos(){
        return (int)engine.executeScript("window.scrollY;");
    }


//  делаем разделители
    private Separator getSeparator(){
        Separator sep = new Separator();
        sep.setOrientation(Orientation.VERTICAL);
        sep.setStyle(Styles.TextViewerMenuPanel_BorderStyle);
        return sep;
    }

    @Override
    public Node getView() {
        return topPanel;
    }
// отключение кнопок навигации по истории если идти некуда
    private void makeButtonsDisableAble() {
        history.currentIndexProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                final Number oldvalue, final Number newvalue) {
                int currentIndex = newvalue.intValue();

                if (currentIndex <= 0) {
                    backButton.setDisable(true);
                } else {
                    backButton.setDisable(false);
                }

                if (currentIndex >= history.getEntries().size() - 1) {
                    forwardButton.setDisable(true);
                } else {
                    forwardButton.setDisable(false);
                }
            }
        });
    }


    private ViewerSettings getSettings(){
        return ctx.get(ViewerSettings.class);
    }
}
