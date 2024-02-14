package bibliosio.article;

import bibliosio.exemplaire.Exemplaire;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import java.util.Objects;
@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Article {

    @Id
    @GeneratedValue
    private Long id;
    private String titre;
    private String description;
    @ManyToOne
    private Exemplaire exemplaire;

    public Article(Long id, String titre, String description, Exemplaire exemplaire) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.exemplaire = exemplaire;
    }

    public Article() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Exemplaire getExemplaire() {
        return exemplaire;
    }

    public void setExemplaire(Exemplaire exemplaire) {
        this.exemplaire = exemplaire;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return Objects.equals(id, article.id) && Objects.equals(titre, article.titre) && Objects.equals(description, article.description) && Objects.equals(exemplaire, article.exemplaire);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, titre, description, exemplaire);
    }


}
