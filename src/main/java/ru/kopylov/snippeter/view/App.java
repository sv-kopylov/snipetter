package ru.kopylov.snippeter.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import ru.kopylov.snippeter.controllers.FeaturesBank;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


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
        em = emf.createEntityManager();

        FeaturesBank featuresBank = new FeaturesBank();

        FeaturesView featuresView = new FeaturesView(em,featuresBank);


        StackPane root = new StackPane();
        root.getChildren().add(featuresView.getView());
        primaryStage.setOnCloseRequest(e -> shutDoun());

        primaryStage.setScene(new Scene(root, 380, 420));
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
