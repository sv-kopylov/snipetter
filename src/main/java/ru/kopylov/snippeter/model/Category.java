package ru.kopylov.snippeter.model;

public enum Category {
    TYPE("Тип отрывка (диалог, монолог, описание, действие и т.п.)"), // тип отрывка (диалог, монолог, описание, действие и т.п.)

    SUBSTANCE("Суть отрывка основной смысл"), //  суть отрывка основной смысл

    CONNOTATION("Дополнительные смыслы в отрывке"), // дополнительные смыслы в отрывке

    EMOTION("Эмоция автора или героя"), // Эмоция автора или героя

    AFFECT("Эффект производимый на читателя"), // эффект производимый на читателя

    STYLE("Стиль ироничный, патетический и пр"), //  стиль ироничны, патетический и пр

    CONTEXT("Контекст отрывка"); // Контекст отрывка (не знаю нужен ли)

    private final String description;


    Category(String description) {
        this.description = description;
    }

    public String description(){
        return description;
    }
}
