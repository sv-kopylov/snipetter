package ru.kopylov.snippeter.controllers;

import ru.kopylov.snippeter.management.SnippetDTO;

import javax.persistence.EntityManager;

public class SnippetController {
    private FeaturesBank featuresBank;
    private EntityManager em;
    private SnippetDTO snippetDTO;

    private String currentSnippetString;

}
