package ru.kopylov.snippeter.model;

import ru.kopylov.snippeter.model.Feature;
import ru.kopylov.snippeter.model.Snippet;

import javax.persistence.*;

public class SnippetFeatureBunch {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional=false, cascade= CascadeType.ALL)
    @JoinColumn(name="snippet_id")
    private Snippet snippet;

    @ManyToOne(optional=false, cascade= CascadeType.ALL)
    @JoinColumn(name="feature_id")
    private Feature feature;
}
