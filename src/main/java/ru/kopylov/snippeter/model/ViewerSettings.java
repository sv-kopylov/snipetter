package ru.kopylov.snippeter.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class ViewerSettings {

    @Id
    private String name = "default";

    private double fontScale = 1.;
    private boolean isAllScreenWidth = false;
    private double textViewWidth = 960.;
    private double leftAlignment = 0.;

    private double startingWindowWidth = 0.;
    private double startingWindowHeight = 0.;

}
