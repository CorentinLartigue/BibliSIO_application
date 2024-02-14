package bibliosio.exemplaire;

import bibliosio.article.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExemplaireRepository extends JpaRepository<Exemplaire, Long> {
}
