package ru.kopylov.snippeter.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class ViewerSettings {

    public static double MAX_FONT_SCALE = 10;

    @Id
    @Getter
    @Setter
    private String name = "default";

    @Getter
    private double fontScale = 1.;


    @Getter
    private double textViewWidth = 960.;

    @Getter
    private double leftAlignment = 0.;


    public void setFontScale(double fontScale) {
        if(fontScale>0 && fontScale<=MAX_FONT_SCALE){
            this.fontScale = fontScale;
        }

    }

    public void setTextViewWidth(double textViewWidth) {
        if(textViewWidth>0)
        this.textViewWidth = textViewWidth;
    }

    public void setLeftAlignment(double leftAlignment) {
        if(leftAlignment>0)
        this.leftAlignment = leftAlignment;
    }
}
