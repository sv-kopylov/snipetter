package ru.kopylov.snippeter.view;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import org.apache.log4j.Logger;
import ru.kopylov.snippeter.controllers.OneCategoryFeatureController;
import ru.kopylov.snippeter.controllers.FeaturesBank;
import ru.kopylov.snippeter.management.FeatureManager;
import ru.kopylov.snippeter.model.Category;

import javax.persistence.EntityManager;

/**
 * Класс отвечает за весь блок с фичами
 */
public class FeaturesView implements Viewable{

    private VBox rootLayout;

    public FeaturesView(FeaturesBank featuresBank) {

        FeatureManager featureManager = new FeatureManager();
        rootLayout = new VBox();
        rootLayout.setSpacing(4);
        OneCategoryFeatureView view;
        OneCategoryFeatureController control;
        for(Category category: Category.values()){
            control = new OneCategoryFeatureController(category, featureManager);
            view = new OneCategoryFeatureView(control, featuresBank);

            rootLayout.getChildren().add(new Label(category.description()));
            rootLayout.getChildren().add(view.getView());
            rootLayout.getChildren().add(new Separator());
        }




    }

    @Override
    public Node getView() {
        return rootLayout;
    }
}
