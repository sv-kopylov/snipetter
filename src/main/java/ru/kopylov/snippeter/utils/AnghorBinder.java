package ru.kopylov.snippeter.utils;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

public class AnghorBinder {


    /**
     * Привяка с четырех сторон, если указанн null с той сторны привязка не применяется
     * @param child узел для привязки
     * @param top
     * @param left
     * @param bottom
     * @param right
     */
    public static void bind(Node child, Double top, Double left, Double bottom, Double right){
        if (top!=null) AnchorPane.setTopAnchor(child, top);
        if (left!=null)  AnchorPane.setLeftAnchor(child, left);
        if (bottom!=null) AnchorPane.setBottomAnchor(child, bottom);
        if (right!=null) AnchorPane.setRightAnchor(child, right);
    }
    /**
     * Привяка сверху слева
     * @param child узел для привязки
     * @param top
     * @param left
     */
    public static void bind(Node child, Double top, Double left){
        AnchorPane.setTopAnchor(child, top);
        AnchorPane.setLeftAnchor(child, left);
    }
}
