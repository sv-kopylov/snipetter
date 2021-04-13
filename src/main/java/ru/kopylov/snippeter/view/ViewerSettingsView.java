package ru.kopylov.snippeter.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
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


    private Label fontScaleLabel = new Label("Шрифт масштаб");
    private TextField fontScaleTextField = new TextField();

    private Label textViewWidthLabel = new Label("Ширина страницы");
    private TextField textViewWidthTextField = new TextField();

    private Label leftAlignmentLabel = new Label("Отступ слева");
    private TextField leftAlignmentTextField = new TextField();

     EventHandler<ActionEvent> applyActionHandler = event -> {
        applySettings();
        updateSettings(); // апдейтим потому, что в полях могут быть некорректные значения, которые не попадут в настройки
    };
    EventHandler<ActionEvent> saveActionHandler = event ->{
        applySettings();
        updateSettings();
        SettingsManager manager = context.get(SettingsManager.class);
        manager.saveSettings(getSettings());
    };

    private ViewerSettings getSettings(){
        return context.get(ViewerSettings.class);
    }

    public ViewerSettingsView(){
        root.setPadding(new Insets(20));
        root.setHgap(10);
        root.setVgap(10);


        root.add(fontScaleLabel,0,1);
        root.add(fontScaleTextField, 1,1);

        root.add(textViewWidthLabel, 0, 2);
        root.add(textViewWidthTextField, 1, 2);

        root.add(leftAlignmentLabel, 0, 3);
        root.add(leftAlignmentTextField, 1, 3);

        root.add(applyButton,0,4);
        root.add(saveButton,1,4);



        applyButton.setOnAction(applyActionHandler);
        saveButton.setOnAction(saveActionHandler);
    }

//    обновляем значение полей в менюшке
    public void updateSettings(){
        fontScaleTextField.setText(new Double(getSettings().getFontScale()).toString());

        textViewWidthTextField.setText(new Double(getSettings().getTextViewWidth()).toString());
        leftAlignmentTextField.setText(new Double(getSettings().getLeftAlignment()).toString());
    }

//    берем настройки и помещаем их в объект настроек (теперь он отличается от базы), апдейтим вид
    private void applySettings(){
        double left = getSettings().getLeftAlignment();
        double width = getSettings().getTextViewWidth();
        double fontScale = getSettings().getFontScale();

        left = convertSafely(leftAlignmentTextField.getText(), left);
        width = convertSafely(textViewWidthTextField.getText(), width);
        fontScale = convertSafely(fontScaleTextField.getText(), fontScale);

        getSettings().setLeftAlignment(left);
        getSettings().setTextViewWidth(width);
        getSettings().setFontScale(fontScale);

        TextViewer textViewer = context.get(TextViewer.class);
        textViewer.applySettings();
    }


    private double convertSafely(String strValue, double defaultValue){
        double res = defaultValue;
        try {
            res = Double.valueOf(strValue);
        } catch (NumberFormatException e) {
//            do nothing
        }
        return res;
    }


    @Override
    public Node getView() {
        return root;
    }
}
