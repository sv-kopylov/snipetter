package ru.kopylov.snippeter.view;

/**
 * Интерфейс для реализации   просмотра текста и выбора отрывков
 */
public interface TextViewer extends Viewable{
    void setByURL(String path);
    void sendToSnippet(String snip);
}
