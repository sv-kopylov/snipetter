package ru.kopylov.snippeter.view.snippetstable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class SnippetsTableView {

    private ObservableList<SnippetRepresentation> observableList = FXCollections.observableArrayList();
    private TableView<SnippetRepresentation> tableView = new TableView<>(observableList);

    public SnippetsTableView() {
        TableColumn<SnippetRepresentation, String> snippetColumn = new TableColumn<>("snippet");
        snippetColumn.setCellValueFactory(new PropertyValueFactory<>("snippetText"));
        snippetColumn.setPrefWidth(400);

        TableColumn<SnippetRepresentation, String> typeColumn = new TableColumn<>("type");
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<SnippetRepresentation, String> substanceColumn = new TableColumn<>("substance");
        substanceColumn.setCellValueFactory(new PropertyValueFactory<>("substance"));

        TableColumn<SnippetRepresentation, String> connotationColumn = new TableColumn<>("connotation");
        connotationColumn.setCellValueFactory(new PropertyValueFactory<>("connotation"));

        TableColumn<SnippetRepresentation, String> emotionColumn = new TableColumn<>("emotion");
        emotionColumn.setCellValueFactory(new PropertyValueFactory<>("emotion"));

        TableColumn<SnippetRepresentation, String> affectColumn = new TableColumn<>("affect");
        affectColumn.setCellValueFactory(new PropertyValueFactory<>("affect"));

        TableColumn<SnippetRepresentation, String> styleColumn = new TableColumn<>("style");
        styleColumn.setCellValueFactory(new PropertyValueFactory<>("style"));

        TableColumn<SnippetRepresentation, String> contextColumn = new TableColumn<>("context");
        contextColumn.setCellValueFactory(new PropertyValueFactory<>("context"));

        TableColumn<SnippetRepresentation, String> personageColumn = new TableColumn<>("personage");
        personageColumn.setCellValueFactory(new PropertyValueFactory<>("personage"));

        tableView.getColumns().addAll(snippetColumn,
                typeColumn,
                substanceColumn,
                connotationColumn,
                emotionColumn,
                affectColumn,
                styleColumn,
                contextColumn,
                personageColumn);



    }

    public TableView<SnippetRepresentation> getTableView() {
        return tableView;
    }
    public void addRow(SnippetRepresentation sr){
        observableList.add(sr);
    }

    public void clear(){
        observableList.clear();
    }
}
