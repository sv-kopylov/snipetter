package ru.kopylov.snippeter.model.bunches;

import lombok.Getter;
import lombok.Setter;
import ru.kopylov.snippeter.model.Snippet;
import ru.kopylov.snippeter.model.Substance;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class SnippetSubstanceBunch {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional=false, cascade= CascadeType.ALL)
    @JoinColumn(name="snippet_id")
    private Snippet snippet;

    @ManyToOne(optional=false, cascade= CascadeType.ALL)
    @JoinColumn(name="substance_id")
    private Substance substance;
}
