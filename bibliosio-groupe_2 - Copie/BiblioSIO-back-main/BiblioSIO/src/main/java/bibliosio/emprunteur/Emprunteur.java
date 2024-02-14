package bibliosio.emprunteur;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import bibliosio.emprunt.Emprunt;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class Emprunteur {
    @Id
    @GeneratedValue
    private Long id;
    private String nom;
    private String prenom;
    private String mail;
    private String promo;

    private Classe classe;
    @OneToMany(mappedBy = "emprunteur")
    private List<Emprunt> emprunts;

    public Emprunteur(Long id, String nom, String prenom, String mail, String promo, Classe classe) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.promo = promo;
        this.classe = classe;
        this.emprunts=new ArrayList<>();
    }

    public Emprunteur() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPromo() {
        return promo;
    }

    public void setPromo(String promo) {
        this.promo = promo;
    }

    public Classe getClasse() {
        return classe;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
    }

    public List<Emprunt> getEmprunts() {
        return emprunts;
    }
    public void addEmprunt(Emprunt emprunt) {
        this.emprunts.add(emprunt);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Emprunteur that = (Emprunteur) o;
        return Objects.equals(id, that.id) && Objects.equals(nom, that.nom) && Objects.equals(prenom, that.prenom) && Objects.equals(mail, that.mail) && Objects.equals(promo, that.promo) && classe == that.classe && Objects.equals(emprunts, that.emprunts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, prenom, mail, promo, classe, emprunts);
    }

}
