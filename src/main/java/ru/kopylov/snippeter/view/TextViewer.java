package ru.kopylov.snippeter.view;

public interface TextViewer extends Viewable{
    void setByURL(String path);
    void sendToSnippet(String snip);
}
