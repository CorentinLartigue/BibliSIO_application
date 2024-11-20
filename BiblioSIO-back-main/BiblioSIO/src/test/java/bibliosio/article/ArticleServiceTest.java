package bibliosio.article;

import bibliosio.exceptions.ResourceAlreadyExistsException;
import bibliosio.exceptions.ResourceNotFoundException;
import bibliosio.exemplaire.Exemplaire;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
@SpringBootTest(classes=ArticleJPAService.class)
public class ArticleServiceTest {

    @Autowired
    private ArticleService articleService;
    @MockBean
    private ArticleRepository articleRepository;
    private List<Article> articles;

    private Exemplaire exemplaire1,exemplaire2;


    @BeforeEach
    void setUp() {

        exemplaire1 = mock(Exemplaire.class);
        exemplaire2 = mock(Exemplaire.class);

        articles = new ArrayList<>(){{
            add(new Article(1L, "Machin", "dcience", exemplaire1));
            add(new Article(2L, "Chose", "people", exemplaire1));
            add(new Article(3L, "Truc", "musique", exemplaire2));
        }};
        when(articleRepository.findAll()).thenReturn(articles);
    }

    @Test
    void whenGettingAll_shouldReturn3() {
        assertEquals(3, articleService.getAll().size());
    }

    @Test
    void whenGettingById_shouldReturnIfExists_andBeTheSame() {
        when(articleRepository.findById(1L)).thenReturn((Optional.of(articles.get(0))));
        when(articleRepository.findById(12L)).thenReturn((Optional.empty()));
        assertAll(
                () -> assertEquals(articles.get(0), articleService.getById(1L)),
                () -> assertThrows(ResourceNotFoundException.class, () -> articleService.getById(12L))
        );
    }

    @Test
    void whenCreating_ShouldReturnSame() {
        Article toCreate = new Article(8L, "Bidule", "sport", exemplaire2);
        assertEquals(toCreate, articleService.create(toCreate));
    }

    @Test
    void whenCreatingWithSameId_shouldReturnEmpty() {
        Article same_article = articles.get(0);

        assertThrows(ResourceAlreadyExistsException.class, ()->articleService.create(same_article));
    }

    @Test
    void whenUpdating_shouldModifyArticle() {
        Article initial_article = articles.get(2);
        Article new_article = new Article(initial_article.getId(),initial_article.getTitre(),initial_article.getDescription(),initial_article.getExemplaire());

        articleService.update(new_article.getId(), new_article);
        Article updated_article = articleService.getById(initial_article.getId());
        assertEquals(new_article, updated_article);
        assertTrue(articleService.getAll().contains(new_article));
    }

    @Test
    void whenUpdatingNonExisting_shouldThrowException() {
        Article unarticle = articles.get(2);

        assertThrows(ResourceNotFoundException.class, ()->articleService.update(75L, unarticle));
    }

    @Test
    void whenDeletingExistingArticle_shouldNotBeInArticlesAnymore() {
        Article article = articles.get(1);
        Long id = article.getId();

        articleService.delete(id);
        assertFalse(articleService.getAll().contains(article));
    }

    @Test
    void whenDeletingNonExisting_shouldThrowException() {
        Long id = 68L;

        assertThrows(ResourceNotFoundException.class, ()->articleService.delete(id));
    }
}
