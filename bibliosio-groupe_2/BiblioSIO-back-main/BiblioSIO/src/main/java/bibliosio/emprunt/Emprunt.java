
package bibliosio.emprunt;

import bibliosio.emprunt.StatutEmprunt;
import bibliosio.emprunteur.Emprunteur;
import bibliosio.exemplaire.Exemplaire;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class Emprunt {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Emprunteur emprunteur;
    @ManyToOne
    private Exemplaire exemplaire;
    private LocalDate dateEmprunt;
    private LocalDate dateEcheance;
    private LocalDate dateRetour;
    private StatutEmprunt statut;

    public Emprunt(Long id, Emprunteur emprunteur, Exemplaire exemplaire, LocalDate dateEmprunt, LocalDate dateEcheance, StatutEmprunt statut) {
        this.id = id;
        this.emprunteur = emprunteur;
        this.exemplaire = exemplaire;
        this.dateEmprunt = dateEmprunt;
        this.dateEcheance = dateEcheance;
        this.dateRetour = null;
        this.statut = statut;
    }

    public Emprunt() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Emprunteur getEmprunteur() {
        return emprunteur;
    }

    public void setEmprunteur(Emprunteur emprunteur) {
        this.emprunteur = emprunteur;
    }

    public Exemplaire getExemplaire() {
        return exemplaire;
    }

    public void setExemplaire(Exemplaire exemplaire) {
        this.exemplaire = exemplaire;
    }

    public LocalDate getDateEmprunt() {
        return dateEmprunt;
    }

    public void setDateEmprunt(LocalDate dateEmprunt) {
        this.dateEmprunt = dateEmprunt;
    }

    public LocalDate getDateEcheance() {
        return dateEcheance;
    }

    public void setDateEcheance(LocalDate dateEcheance) {
        this.dateEcheance = dateEcheance;
    }

    public LocalDate getDateRetour() {
        return dateRetour;
    }

    public void setDateRetour(LocalDate dateRetour) {
        this.dateRetour = dateRetour;
    }

    public StatutEmprunt getStatut() {
        return statut;
    }

    public void setStatut(StatutEmprunt statut) {
        this.statut = statut;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Emprunt emprunt = (Emprunt) o;
        return Objects.equals(id, emprunt.id) && Objects.equals(emprunteur, emprunt.emprunteur) && Objects.equals(exemplaire, emprunt.exemplaire) && Objects.equals(dateEmprunt, emprunt.dateEmprunt) && Objects.equals(dateEcheance, emprunt.dateEcheance) && Objects.equals(dateRetour, emprunt.dateRetour) && statut == emprunt.statut;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, emprunteur, exemplaire, dateEmprunt, dateEcheance, dateRetour, statut);
    }

}

