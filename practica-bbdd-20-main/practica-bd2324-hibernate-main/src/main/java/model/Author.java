package model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

// @TODO Realiza todas las anotaciones necesarias en esta clase para que
// que sus instancias sean guardadas en la base de datos utilizando
// Hibernate. Respecta las restricciones de modelado impuestas en el
// enunciado de la práctica. No es necesario modificar el código de esta
// clase, únicamente debes hacer las anotaciones que consideres
// necesarias.

@Entity
@Table(name = "author")
public class Author {

    @Id
    @GeneratedValue
    @Column(name = "author_id", unique = true, nullable = false, length = 100 )
    private Long author_id;

    @Column(name = "author_name", unique = false, nullable = false, length = 400)
    private String author_name;

    @Column(name = "importance", unique = false, nullable = true, length = 100)
    private Double importance;

    @ManyToMany()
    @JoinTable(name ="author_article")  //nombre relacion
    private Set<Article> articles;

    @ManyToMany()
    @JoinTable(name ="author_affiliation")
    private Set<Affiliation> affiliations;

    public Author() {

    }

    public Author(String author_name, double importance) {
        this.author_name = author_name;
        this.importance = importance;
        this.articles = new HashSet<>();
        this.affiliations = new HashSet<>();
    }

    public Long getAuthor_id() {
        return author_id;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public Double getImportance() {
        return importance;
    }

    public Set<Affiliation> getAffiliations () {
        return this.affiliations;
    }

    public Set<Article> getArticles () {
        return this.articles;
    }
}
