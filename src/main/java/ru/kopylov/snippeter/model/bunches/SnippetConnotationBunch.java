package ru.kopylov.snippeter.model.bunches;

import lombok.Getter;
import lombok.Setter;
import ru.kopylov.snippeter.model.Connotation;
import ru.kopylov.snippeter.model.Snippet;

import javax.persistence.*;


@Getter
@Setter
@Entity
public class SnippetConnotationBunch {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional=false, cascade= CascadeType.ALL)
    @JoinColumn(name="snippet_id")
    private Snippet snippet;

    @ManyToOne(optional=false, cascade= CascadeType.ALL)
    @JoinColumn(name="connotation_id")
    private Connotation connotation;
}
