package ru.kopylov.snippeter.model;

import javax.persistence.*;
@Entity
public class SnippetFeatureBunch {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional=false)
    @JoinColumn(name="snippet_id")
    private Snippet snippet;

    @ManyToOne(optional=false)
    @JoinColumn(name="feature_id")
    private Feature feature;

    public SnippetFeatureBunch() {
    }

    public SnippetFeatureBunch(Snippet snippet, Feature feature) {
        this.snippet = snippet;
        this.feature = feature;
    }

    public Long getId() {
        return id;
    }

    public Snippet getSnippet() {
        return snippet;
    }

    public Feature getFeature() {
        return feature;
    }
}
