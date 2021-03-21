package ru.kopylov.snippeter.context;


import javafx.scene.web.WebView;
import ru.kopylov.snippeter.controllers.FeaturesBank;
import ru.kopylov.snippeter.management.SettingsManager;
import ru.kopylov.snippeter.utils.EntityManagerHolder;
import ru.kopylov.snippeter.view.FeaturesView;
import ru.kopylov.snippeter.view.ResearchView;
import ru.kopylov.snippeter.view.TextViewer;
import ru.kopylov.snippeter.view.TextViewerWebViewImpl;

/**
 * Здесь должны создаваться все постоянные объекты, инициализироваться, если тербуется, и помеещаться в контекст.
 * порядок имеет значение так как одни компоненты могут зависеть от других
 */
public class ApplicationInitializer {


    public static void init() {
        Context ctx = Context.getInstance();

// инициализация механизма работы с базой
        EntityManagerHolder.getInstance().init("snip_pu");



//  добавление всех компонентов в контекст


        ctx.put(new FeaturesBank()); // перед FeaturesView и ResearchView
        ctx.put(new FeaturesView()); // перед ResearchView
        ctx.put(new ResearchView());

        ctx.put(new WebView()); // сначала движок WebView а потом компонент его использующий TextViewer
        TextViewerWebViewImpl textViewerWebView = new TextViewerWebViewImpl();
        ctx.put(TextViewer.class.getName(),textViewerWebView); // добавляем TextViewer по интерфейсу
//    фетчинг настроек если они есть, если нет то создается новый экземпляр
        SettingsManager settingsManager = new SettingsManager();
        ctx.put(settingsManager); // менеджер настроек
        ctx.put(settingsManager.getDefaultSettings()); // сами настройки
        textViewerWebView.applySettings(); // применяем настройки как только они получены из базы






    }

    private static void initSettings(){
        Context ctx = Context.getInstance();

    }
}
