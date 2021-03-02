package ru.kopylov.snippeter.context;


import ru.kopylov.snippeter.controllers.FeaturesBank;
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
// инициализация механизма работы с базой
        EntityManagerHolder.getInstance().init("snip_pu");

//  добавление всех компонентов в контекст
        Context ctx = Context.getInstance();

        ctx.put(new FeaturesBank()); // перед FeaturesView и ResearchView
        ctx.put(new FeaturesView()); // перед ResearchView
        ctx.put(new ResearchView());
        ctx.put(TextViewer.class.getName(),new TextViewerWebViewImpl()); // добавляем по интерфейсу

    }
}
