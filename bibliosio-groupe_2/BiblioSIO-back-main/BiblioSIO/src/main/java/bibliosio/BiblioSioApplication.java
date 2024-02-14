package bibliosio;

import bibliosio.article.Article;
import bibliosio.article.ArticleRepository;
import bibliosio.emprunt.Emprunt;
import bibliosio.emprunt.EmpruntRepository;
import bibliosio.emprunt.StatutEmprunt;
import bibliosio.emprunteur.Classe;
import bibliosio.emprunteur.Emprunteur;
import bibliosio.emprunteur.EmprunteurRepository;
import bibliosio.exemplaire.Exemplaire;
import bibliosio.exemplaire.ExemplaireRepository;
import bibliosio.revue.Revue;
import bibliosio.revue.RevueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class BiblioSioApplication {

	public static void main(String[] args) {
		SpringApplication.run(BiblioSioApplication.class, args);
	}

	@Autowired
	private EmprunteurRepository emprunteurRepository;


	@Autowired
	private EmpruntRepository empruntRepository;

	@Autowired
	private ExemplaireRepository exemplaireRepository;

	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	private RevueRepository revueRepository;

	@Bean
	public CommandLineRunner setUpBDD() {
		return (args) -> {


			Emprunteur ER1 = new Emprunteur (1L,"Jean", "Paul", "jean.paul@gmail.com", "2022-2024" ,Classe.SIO1B,"Etudiant");
			Emprunteur ER2 = new Emprunteur(2L,"Pierre", "Feuille", "pierre.feuille@gmail.com", "2022-2024" ,Classe.SIO2B,"Etudiant");
			Emprunteur ER3 = new Emprunteur(3L,"Emprunter3", "Benoit", "Benoit@gmail.com", "2022-2024" ,Classe.SIO1A,"Etudiant");
			Emprunteur ER4 = new Emprunteur(4L,"Emprunter4", "Gimenez", "Gimenez@gmail.com", "" ,Classe.PROF,"Professeur");

			List<Emprunteur> emprunteurs = new ArrayList<>(){{
				add(ER1);
				add(ER2);
				add(ER3);
				add(ER4);


			}};
			emprunteurRepository.saveAll(emprunteurs);


			Revue R1=new Revue(1L,"Revue1");
			Revue R2=new Revue(2L,"Revue2");
			List<Revue> revues = new ArrayList<>(){{
				add(R1);
				add(R2);
				add(new Revue(3L, "Revue3"));


			}};
			revueRepository.saveAll(revues);


			Exemplaire EX1=new Exemplaire(1L, R1 ,"Exemplaire1", "Mars","2023", Boolean.TRUE);
			Exemplaire EX2=new Exemplaire(2L, R2 ,"Exemplaire2", "Mars","2023", Boolean.TRUE);
			List<Exemplaire> exemplaires = new ArrayList<>(){{
				add(EX1);
				add(EX2);
				add(new Exemplaire(3L, R1 ,"Exemplaire3", "Mars","2023", Boolean.TRUE));;


			}};
			exemplaireRepository.saveAll(exemplaires);


			Article A1=new Article(1L, "machin", "c'est le monde de machin", EX1);
			Article A2=new Article(2L, "truc", "c'est le monde de truc", EX2);
			List<Article> articles = new ArrayList<>(){{
				add(A1);
				add(A2);
				add(new Article(3L,"bidule","c'est le monde de bidule",EX1));


			}};
			articleRepository.saveAll(articles);

			Emprunt E1 = new Emprunt (1L, ER1, EX1,LocalDate.of(2023,12,1), LocalDate.of(2024,2,1), StatutEmprunt.EN_COURS);
			Emprunt E2 = new Emprunt(2L, ER2, EX1, LocalDate.of(2023, 12, 1), LocalDate.of(2024,2,1), StatutEmprunt.EN_COURS);
			List<Emprunt> emprunts = new ArrayList<>(){{
				add(E1);
				add(E2);
				add(new Emprunt(3L, ER3, EX2,LocalDate.of(2023, 12, 1), LocalDate.of(2024, 12, 1), StatutEmprunt.EN_COURS));
			}};
			empruntRepository.saveAll(emprunts);

		};
	}


}
