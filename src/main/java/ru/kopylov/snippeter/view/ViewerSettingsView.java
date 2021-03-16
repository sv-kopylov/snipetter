package ru.kopylov.snippeter.view;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import ru.kopylov.snippeter.context.Context;
import ru.kopylov.snippeter.management.SettingsManager;
import ru.kopylov.snippeter.model.ViewerSettings;


public class ViewerSettingsView implements Viewable{
    Context context = Context.getInstance();
    private GridPane root = new GridPane();

    private Button applyButton = new Button("Применить");
    private Button saveButton = new Button("Сохранить");

    private Label isAllScreenWideLabel = new Label("На всю ширину");
    private CheckBox isAllScreenWideCheckBox = new CheckBox();

    private Label fontScaleLabel = new Label("Шрифт масштаб");
    private TextField fontScaleTextField = new TextField();

    private Label textViewWidthLabel = new Label("Ширина страницы");
    private TextField textViewWidthTextField = new TextField();

    private Label leftAlignmentLabel = new Label("Отступ слева");
    private TextField leftAlignmentTextField = new TextField();

    public ViewerSettingsView(){
        root.setPadding(new Insets(20));
        root.setHgap(10);
        root.setVgap(10);

        root.add(isAllScreenWideLabel, 0, 0);
        root.add(isAllScreenWideCheckBox, 1, 0);

        root.add(fontScaleLabel,0,1);
        root.add(fontScaleTextField, 1,1);

        root.add(textViewWidthLabel, 0, 2);
        root.add(textViewWidthTextField, 1, 2);

        root.add(leftAlignmentLabel, 0, 3);
        root.add(leftAlignmentTextField, 1, 3);

        root.add(applyButton,0,4);
        root.add(saveButton,1,4);

    }




    private ViewerSettings getSettings(){
        return context.get(ViewerSettings.class);
    }
    private SettingsManager settingsManager(){
        return context.get(SettingsManager.class);
    }





    @Override
    public Node getView() {
        return root;
    }
}
