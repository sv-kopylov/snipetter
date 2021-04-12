package ru.kopylov.snippeter.view;

import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class DialogShower {
    private String title;
    private String content;

    public DialogShower(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Optional<String> showInputTextDialog(){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(title);
        dialog.setContentText(content);
        return dialog.showAndWait();
    }
}
