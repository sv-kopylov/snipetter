package ru.kopylov.snippeter.model;

import ru.kopylov.snippeter.model.Feature;
import ru.kopylov.snippeter.model.Snippet;

import javax.persistence.*;
@Entity
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

    public SnippetFeatureBunch(Snippet snippet, Feature feature) {
        this.snippet = snippet;
        this.feature = feature;
    }
}
