package ru.kopylov.snippeter.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Alias {


    private String name;
    private List<Feature> features;

    @Override
    public String toString() {
        return name;
    }
}
