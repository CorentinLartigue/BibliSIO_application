package bibliosio.article;
import bibliosio.emprunt.Emprunt;
import bibliosio.exceptions.ResourceNotFoundException;
import bibliosio.exceptions.ResourceAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Qualifier("jpa")
public class ArticleJPAService implements ArticleService {

    private ArticleRepository articleRepository;
    @Autowired
    public ArticleJPAService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public List<Article> getAll() {
        return articleRepository.findAll();
    }

    @Override
    public Article getById(Long id) {
        Optional<Article> article = articleRepository.findById(id);
        if (article.isPresent()) {
            return article.get();
        } else {
            throw new ResourceNotFoundException("Article", id);
        }
    }

    @Override
    public Article create(Article newArticle) {
        if(articleRepository.existsById(newArticle.getId())){
            throw new ResourceAlreadyExistsException("Article",newArticle.getId());
        }
        else {
            return articleRepository.save(newArticle);
        }
    }

    @Override
    public void update(Long id, Article updatedArticle) {
        if(articleRepository.existsById(id)){
            throw new ResourceNotFoundException("Article",id);
        }
        else {
            articleRepository.save(updatedArticle);
        }

    }

    @Override
    public boolean delete(Long id) throws ResourceNotFoundException {
        Optional<Article> found = articleRepository.findById(id);
        if(found.isPresent()){
            articleRepository.deleteById(id);
            return true;
        }
        else {
            throw new ResourceNotFoundException("Article", id);
        }

    }
}
