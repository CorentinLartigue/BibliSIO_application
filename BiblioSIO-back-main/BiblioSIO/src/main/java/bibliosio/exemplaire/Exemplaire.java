package bibliosio.exemplaire;

import bibliosio.article.Article;
import bibliosio.emprunt.Emprunt;
import bibliosio.revue.Revue;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Exemplaire {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Revue revue;
    private String titre;
    private String moisParution;
    private String anneeParution;
    @OneToMany(mappedBy = "exemplaire")
    private List<Article> articles;

    @OneToMany(mappedBy = "exemplaire")
    private List<Emprunt> emprunts;
    private Boolean statut;

    public Exemplaire(Long id, Revue revue, String titre, String moisParution, String anneeParution, Boolean statut) {
        this.id = id;
        this.revue = revue;
        this.titre = titre;
        this.moisParution = moisParution;
        this.anneeParution = anneeParution;
        this.articles = new ArrayList<>();
        this.emprunts = new ArrayList<>();
        this.statut = statut;
    }

    public Exemplaire() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Revue getRevue() {
        return revue;
    }

    public void setRevue(Revue revue) {
        this.revue = revue;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getMoisParution() {
        return moisParution;
    }

    public void setMoisParution(String moisParution) {
        this.moisParution = moisParution;
    }

    public String getAnneeParution() {
        return anneeParution;
    }

    public void setAnneeParution(String anneeParution) {
        this.anneeParution = anneeParution;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public Boolean getStatut() {
        return statut;
    }

    public void setStatut(Boolean statut) {
        this.statut = statut;
    }

    public List<Emprunt> getEmprunts() {
        return emprunts;
    }

    public void setEmprunts(List<Emprunt> emprunts) {
        this.emprunts = emprunts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exemplaire that = (Exemplaire) o;
        return Objects.equals(id, that.id) && Objects.equals(revue, that.revue) && Objects.equals(titre, that.titre) && Objects.equals(moisParution, that.moisParution) && Objects.equals(anneeParution, that.anneeParution) && Objects.equals(articles, that.articles) && Objects.equals(statut, that.statut);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, revue, titre, moisParution, anneeParution, articles, statut);
    }
}