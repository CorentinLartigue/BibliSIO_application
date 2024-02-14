package bibliosio.revue;

import bibliosio.exemplaire.Exemplaire;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Revue {

    @Id
    @GeneratedValue
    private long id;

    private String titre;

    @OneToMany(mappedBy = "revue")
    private List<Exemplaire> exemplaire;

    public Revue(long id, String titre) {
        this.id = id;
        this.titre = titre;
        this.exemplaire = new ArrayList<>();
    }

    public Revue() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public List<Exemplaire> getExemplaire() {
        return exemplaire;
    }

    public void setExemplaire(List<Exemplaire> exemplaire) {
        this.exemplaire = exemplaire;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Revue revue = (Revue) o;
        return id == revue.id && Objects.equals(titre, revue.titre) && Objects.equals(exemplaire, revue.exemplaire);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, titre, exemplaire);
    }
}
