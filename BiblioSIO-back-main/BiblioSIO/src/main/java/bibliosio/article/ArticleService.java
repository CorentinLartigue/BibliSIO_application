package bibliosio.article;

import java.util.List;

public interface ArticleService {
    List<Article> getAll();

    Article getById(Long value);

    Article create(Article article);

    void update(Long code, Article article);

    boolean delete(Long id);
}
