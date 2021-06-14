package ru.kopylov.snippeter.view.snippetstable;

import javafx.beans.property.SimpleStringProperty;
import lombok.Getter;
import ru.kopylov.snippeter.model.Category;
import ru.kopylov.snippeter.model.Feature;
import ru.kopylov.snippeter.model.Snippet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class SnippetRepresentation {
    @Getter
    private Snippet snippet;
    private SimpleStringProperty snippetText = new SimpleStringProperty();

    private SimpleStringProperty type= new SimpleStringProperty();
    private SimpleStringProperty substance= new SimpleStringProperty();
    private SimpleStringProperty connotation= new SimpleStringProperty();
    private SimpleStringProperty emotion= new SimpleStringProperty();
    private SimpleStringProperty affect= new SimpleStringProperty();
    private SimpleStringProperty style= new SimpleStringProperty();
    private SimpleStringProperty context= new SimpleStringProperty();
    private SimpleStringProperty personage= new SimpleStringProperty();

    public void setValues(Snippet snippet, ArrayList<Feature> features){
        this.snippet = snippet;
        this.snippetText.set(snippet.getSnippet());
        HashMap<Category, List<String>> map = new HashMap<>();
        for(Feature feature: features){
            if(map.get(feature.getCategory())==null){
                map.put(feature.getCategory(), new ArrayList<String>());
            }
            map.get(feature.getCategory()).add(feature.getValue());
        }
        for(Category cat: map.keySet()){
            setProperty(cat, map.get(cat));
        }
    }

    private void setProperty(Category cat, List<String> list){
        Collections.sort(list);

        switch (cat){
            case TYPE:
                type.set(list.toString());
                break;
            case SUBSTANCE:
                substance.set(list.toString());
                break;
            case CONNOTATION:
                connotation.set(list.toString());
                break;
            case EMOTION:
                emotion.set(list.toString());
                break;
            case AFFECT:
                affect.set(list.toString());
                break;
            case STYLE:
                style.set(list.toString());
                break;
            case CONTEXT:
                context.set(list.toString());
                break;
            case PERSONAGE:
                personage.set(list.toString());
        }

    }

    public String getSnippetText() {
        return snippetText.get();
    }

    public SimpleStringProperty snippetTextProperty() {
        return snippetText;
    }

    public String getType() {
        return type.get();
    }

    public SimpleStringProperty typeProperty() {
        return type;
    }

    public String getSubstance() {
        return substance.get();
    }

    public SimpleStringProperty substanceProperty() {
        return substance;
    }

    public String getConnotation() {
        return connotation.get();
    }

    public SimpleStringProperty connotationProperty() {
        return connotation;
    }

    public String getEmotion() {
        return emotion.get();
    }

    public SimpleStringProperty emotionProperty() {
        return emotion;
    }

    public String getAffect() {
        return affect.get();
    }

    public SimpleStringProperty affectProperty() {
        return affect;
    }

    public String getStyle() {
        return style.get();
    }

    public SimpleStringProperty styleProperty() {
        return style;
    }

    public String getContext() {
        return context.get();
    }

    public SimpleStringProperty contextProperty() {
        return context;
    }

    public String getPersonage() {
        return personage.get();
    }

    public SimpleStringProperty personageProperty() {
        return personage;
    }
}
