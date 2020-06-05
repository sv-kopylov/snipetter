package ru.kopylov.snippeter.controllers;

import ru.kopylov.snippeter.management.SnippetDTO;

import javax.persistence.EntityManager;

public class SnippetController {
    private FeaturesBank featuresBank;
    private EntityManager entityManager;
    private SnippetDTO snippetDTO;

    private String currentSnippetString;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public SnippetController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
