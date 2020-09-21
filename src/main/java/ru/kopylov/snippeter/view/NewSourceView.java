package ru.kopylov.snippeter.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import ru.kopylov.snippeter.management.GenreManager;
import ru.kopylov.snippeter.management.SourceManager;
import ru.kopylov.snippeter.model.Genre;
import ru.kopylov.snippeter.utils.SimpleTransformer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;


public class NewSourceView implements Viewable {
    private static Logger logger = Logger.getLogger(NewSourceView.class);
    private Stage stage;
    private SourceManager sourceManager;

    private GridPane root;

    private final Label titleLabel = new Label("название");
    private TextField titleTF = new TextField();


    private final Label authorLabel = new Label("автор") ;
    private TextField authorTF = new TextField();


    private final Label yearLabel = new Label("год");
    private TextField yearTF = new TextField();


    private final Label genreLabel = new Label("жанр");
    private ChoiceBox<Genre> genreChoiceBox;
    private GenreManager genreManager;

    private final Label translatorLabel = new Label("переводчик");
    private TextField translatorTF = new TextField();

    private final Label linkToFileLabel = new Label("");
    private final Button chooseFileButton = new Button("выбрать файл");

    private final Button newGenreButton = new Button("новый жанр");

    private FileChooser textFileChooser = new FileChooser();
    private CheckBox isTranslationCB = new CheckBox("перевод");

    private Button saveBookButton = new Button("сохранить");

    private Label infoLabel = new Label("");


    public NewSourceView(Stage stage) {
        this.stage =stage;
        sourceManager = new SourceManager();
        genreManager = new GenreManager();
        genreChoiceBox = new ChoiceBox<>(genreManager.getGenres());

        root = new GridPane();
        root.setPadding(new Insets(20));
        root.setHgap(10);
        root.setVgap(10);

        root.add(chooseFileButton, 0, 0);
        root.add(linkToFileLabel, 0, 1,2,1);

        root.add(titleLabel, 0, 2);
        root.add(titleTF, 1, 2);

        root.add(authorLabel, 0, 3);
        root.add(authorTF, 1, 3);

        root.add(yearLabel, 0, 4);
        root.add(yearTF, 1, 4);

        root.add(genreLabel, 0, 5);

        root.add(genreChoiceBox, 1, 5);
        GridPane.setFillWidth(genreChoiceBox, true);
        root.add(newGenreButton, 1, 6);


        root.add(isTranslationCB, 0, 7);
        root.add(translatorTF, 1, 8);
        root.add(translatorLabel, 0, 8);




        root.add(saveBookButton, 0, 9);
        infoLabel.setWrapText(true);
        infoLabel.setTextFill(Color.web("#ff0000", 0.8));
        root.add(infoLabel, 0, 10, 2,1);

//        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Fiction book format v2.0 *.fb2", "*.fb2");
//        textFileChooser.getExtensionFilters().addAll(extensionFilter);

        newGenreButton.setOnAction(eventAction ->{
            Optional<String> item = showInputTextDialog();
            if(item.isPresent()){
                if(!genreManager.isExist(item.get())){
                    genreManager.addGenre(item.get());
                } else {
                    logger.warn("genre "+item.get()+" already exists.");
                }
            }
        });

        String fb2ext = "fb2";
        String htmlext = "html";
        String textext = "txt";
        String dir = "books";
        chooseFileButton.setOnAction(eventAction->{
            File booksDir = new File(dir);
            booksDir.mkdirs();
            File file =  textFileChooser.showOpenDialog(stage);
            if(file!=null){
            String filename = file.getName();
            String filePath = file.getAbsolutePath();
            if(filename.endsWith("fb2")){
                SimpleTransformer transformer = new SimpleTransformer();
                String newFileName = booksDir.getPath()+ File.separator+filename.replace(fb2ext, htmlext);
                String textFileName = booksDir.getPath()+ File.separator+filename.replace(fb2ext, textext);
                logger.debug(newFileName);
                transformer.transform2html(filePath, newFileName);
                transformer.transform2text(filePath, textFileName);
                linkToFileLabel.setText(newFileName);

            } else {

                try {
                    Path newPath = Paths.get(booksDir.getPath(), filename);
                    Files.copy(file.toPath(), newPath);
                    linkToFileLabel.setText(newPath.toString());
                } catch (IOException e) {
                    logger.warn("I can't copy file "+e.getMessage());
                    e.printStackTrace();
                }

            }

            }

        });

        saveBookButton.setOnAction(event -> {saveSource(); stage.close();});




        translatorTF.setDisable(true);

        isTranslationCB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                    translatorTF.setDisable(!isTranslationCB.isSelected());
            }
        });

    }
    private  Optional<String>  showInputTextDialog() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Новый жанр");
        dialog.setContentText("Название:");
        return dialog.showAndWait();
    }

    private String saveSource(){
        StringBuilder sb = new StringBuilder();
        boolean criticalAttrEmpty = false;
// сначала проверяем критичные
        String title=titleTF.getText();
        if(!isOk(title)){
            criticalAttrEmpty = true;
            sb.append("title is empty/ ");
        }
        String author=authorTF.getText();
        if(!isOk(author)){
            criticalAttrEmpty = true;
            sb.append("autor is empty/ ");
        }
        boolean isTranslation = isTranslationCB.isSelected();
        String translator=translatorTF.getText();
        if(!isOk(translator)&&isTranslation){
            criticalAttrEmpty = true;
            sb.append("translator is empty/ ");

        }

        Genre genre = genreChoiceBox.getValue();
        if(genre==null){
            criticalAttrEmpty = true;
            sb.append("genre is null ");
        }
        logger.warn(sb.toString());
        infoLabel.setText(sb.toString());
//   если критично ничего не создаем а возвращаем перечень косяков
        if(criticalAttrEmpty) return sb.toString();


//   если ничего критичного, то добро пожаловать
        int year = 0;
        try {
            year = Integer.parseInt(yearTF.getText());
        } catch (NumberFormatException e) {
            logger.warn(e.getMessage());
            sb.append(e.getMessage());
        }
        if(year==0){
            sb.append("perhaps year is incorrect");
        }

        String linkToFile=linkToFileLabel.getText();
        if(!isOk(linkToFile)){
            sb.append("link to file is empty/ ");
        }
        sourceManager.createSource(title, author, year, isTranslation, translator, genre, linkToFile);
        if(sb.length()<1){
            sb.append("ok");
        }
        cleanFelds();
        return sb.toString();
    }

    private void cleanFelds() {
        titleTF.setText("");
        authorTF.setText("");
        isTranslationCB.setSelected(false);
        translatorTF.setText("");
        genreChoiceBox.setValue(null);
        yearTF.setText("");
        linkToFileLabel.setText("");
        infoLabel.setText("");
    }

    private boolean isOk(String str){
        if(str==null) return false;
        if(str.length()<1) return false;
        return true;
    }


    @Override
    public Node getView() {
        return root;
    }
}
