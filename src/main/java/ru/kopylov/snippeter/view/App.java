package ru.kopylov.snippeter.view;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import ru.kopylov.snippeter.controllers.FeatureCategorySubController;
import ru.kopylov.snippeter.controllers.FeaturesBank;
import ru.kopylov.snippeter.management.FeatureManager;
import ru.kopylov.snippeter.model.Category;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.apache.log4j.Logger;


public class App extends Application {
    private static Logger logger = Logger.getLogger(App.class);


    public static void main(String[] args) {
        launch(args);
    }
    private EntityManager em;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Snipetter");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory( "snip_pu" );
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        FeatureManager featureManager = new FeatureManager(em);
        FeaturesBank featuresBank = new FeaturesBank();
        FeatureCategorySubController control = new FeatureCategorySubController(Category.AFFECT, featureManager);
        OneCategoryFeatureView oneTypeView = new OneCategoryFeatureView(control, featuresBank);


        StackPane root = new StackPane();
        root.getChildren().add(oneTypeView.getRoot());
        primaryStage.setOnCloseRequest(e -> shutDoun());

        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();

    }



    private void shutDoun(){
        logger.debug("Stopping");
        System.out.println("Stopping");
        if (em!=null) {
            if(em.getTransaction().isActive()){
                em.getTransaction().commit();
            }
            EntityManagerFactory emf = em.getEntityManagerFactory();
            if(emf!=null){
                emf.close();
            }
            em.close();

        } else {
            System.out.println("entity man is null");
        }

    }
}
