package bibliosio.revue;

import bibliosio.article.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RevueRepository extends JpaRepository<Revue, Long> {
}
