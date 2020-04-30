package ru.kopylov.snippeter.model.bunches;

import lombok.Getter;
import lombok.Setter;
import ru.kopylov.snippeter.model.Snippet;
import ru.kopylov.snippeter.model.Style;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class SnippetStyleBunch {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional=false, cascade= CascadeType.ALL)
    @JoinColumn(name="snippet_id")
    private Snippet snippet;

    @ManyToOne(optional=false, cascade= CascadeType.ALL)
    @JoinColumn(name="style_id")
    private Style style;
}
