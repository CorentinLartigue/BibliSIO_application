package bibliosio.article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/articles")
@CrossOrigin(origins = "*")
public class ArticleController {
    private ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    public ArticleController(){

    }
    @GetMapping("")
    public List<Article> getAll(){
        return articleService.getAll();
    }

    @GetMapping("{id}")
    public Article getArticleById(@PathVariable Long id) {

        return articleService.getById(id);
    }

    @PostMapping("")
    public ResponseEntity createArticle(Article article){
        Article created = articleService.create(article);
        return ResponseEntity.created(URI.create("/articles/"+ created.getId())).build();
    }

    @PutMapping("{id}")
    public ResponseEntity updateArticle(@PathVariable Long code, @RequestBody Article article){
        articleService.update(code, article);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteArticle(@PathVariable Long id){
        articleService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
}
