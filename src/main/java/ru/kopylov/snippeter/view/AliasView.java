package ru.kopylov.snippeter.view;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ru.kopylov.snippeter.context.Context;
import ru.kopylov.snippeter.controllers.FeaturesBank;
import ru.kopylov.snippeter.management.AliasManager;
import ru.kopylov.snippeter.model.Feature;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class AliasView implements Viewable{
    private ChoiceBox<String>  choiseBox = new ChoiceBox();
    private Button applyButton = new Button("-->");
    private Button addButton = new Button("Сохранить пресет");
    private Button remButton = new Button("Удалить пресет");

    private DialogShower dialogShower = new DialogShower("Ввод", "Название алиаса:");
    private Label boxName = new Label("Пресеты: ");

    private AliasManager aliasManager = new AliasManager();

    private HBox buttonsLine = new HBox();
    private VBox root = new VBox();

    private Map<String, List<Feature>> cachedMap;
    private EventHandler<ActionEvent> saveButtonEventHandler = event -> {
        ObservableList<Feature> features = getFeaturesBank().getAllFeatures();
        if(features.size()==0){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Предупреждение!");
            alert.setHeaderText("Не выбрано ни одной фичи!");
            alert.setContentText("Хоть одна фича должна быть правом окне!");
            alert.showAndWait();
            return;
        }
        Optional<String> name = dialogShower.showInputTextDialog();
        if(name.isPresent()){
            aliasManager.saveAliases(name.get(), features);
        }
        updateAliasesList();
    };
    private EventHandler<ActionEvent> remButtonEventHandler = event -> {
        String value = choiseBox.getValue();
        if(value!=null){
            aliasManager.removeAlias(value);
        }
        updateAliasesList();
    };
    private EventHandler<ActionEvent> applyButtonEventHandler = event -> {
        String value = choiseBox.getValue();
        if(value!=null){
            for(Feature feature: cachedMap.get(value)){
                getFeaturesBank().addFeature(feature);
            }

        }
    };
    public AliasView() {
        root.setSpacing(4);
        root.getChildren().add(new Separator());
        root.getChildren().add(boxName);

        choiseBox.setPrefWidth(315);
        root.getChildren().add(choiseBox);
        buttonsLine.setSpacing(3);
        buttonsLine.getChildren().addAll(addButton, remButton, applyButton);
        root.getChildren().add(buttonsLine);
        root.getChildren().add(new Separator());

        updateAliasesList();


        addButton.setOnAction(saveButtonEventHandler);
        remButton.setOnAction(remButtonEventHandler);
        applyButton.setOnAction(applyButtonEventHandler);
    }

    private FeaturesBank getFeaturesBank(){
        return Context.getInstance().get(FeaturesBank.class);
    }

    private void updateAliasesList(){
        choiseBox.getItems().clear();
        cachedMap = aliasManager.getNameFeatureMap();
        choiseBox.getItems().addAll(cachedMap
                .keySet()
                .stream()
                .sorted()
                .collect(
                        Collectors
                                .toList()));

    }

    @Override
    public Node getView() {
        return root;
    }
}
