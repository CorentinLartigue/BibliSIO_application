package bibliosio.article;

import com.fasterxml.jackson.databind.ObjectMapper;
import bibliosio.exceptions.ExceptionHandlingAdvice;
import bibliosio.exceptions.ResourceAlreadyExistsException;
import bibliosio.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest
@ContextConfiguration(classes = ArticleController.class)
@Import(ExceptionHandlingAdvice.class)
public class ArticleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ArticleService articleService;

    private List<Article> articles;

    @BeforeEach
    void setUp() {
        articles = new ArrayList<>() {{
            add(new Article(1L, "Machin", "dcience", null));
            add(new Article(2L, "Chose", "people", null));
            add(new Article(3L, "Truc", "musique", null));
            add(new Article(14L, "higher", "geographie", null));
            add(new Article(7L, "lower", "politique", null));
            add(new Article(28L, "way higher", "random", null));
        }};
        when(articleService.getAll()).thenReturn(articles);
        when(articleService.getById(7L)).thenReturn(articles.get(4));
        when(articleService.getById(49L)).thenThrow(ResourceNotFoundException.class);
    }

    @Test
    void whenGettingAll_shouldGet6_andBe200() throws Exception {
        mockMvc.perform(get("/articles")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()
        ).andExpect(jsonPath("$", hasSize(6))
        ).andDo(print());
    }

    @Test
    void whenGettingId7L_shouldReturnSame() throws Exception{
        mockMvc.perform(get("/articles/7")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()
        ).andExpect(jsonPath("$.id", is(7))
        ).andExpect(jsonPath("$.nom", is("lower"))
        ).andExpect(jsonPath("$.fonction", is("USER"))
        ).andReturn();
    }

    @Test
    void whenGettingUnexistingId_should404() throws Exception {
        mockMvc.perform(get("/articles/49")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound()
        ).andDo(print());
    }

    @Test
    void whenCreatingNew_shouldReturnLink_andShouldBeStatusCreated() throws Exception {
        Article new_article = new Article(89L, "nouveau","sport", null);
        ArgumentCaptor<Article> article_received = ArgumentCaptor.forClass(Article.class);
        when(articleService.create(any())).thenReturn(new_article);

        mockMvc.perform(post("/articles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(new_article))
        ).andExpect(status().isCreated()
        ).andExpect(header().string("Location", "/articles/"+new_article.getId())
        ).andDo(print());

        verify(articleService).create(article_received.capture());
        assertEquals(new_article, article_received.getValue());
    }

    @Test
    void whenCreatingWithExistingId_should404() throws Exception {
        when(articleService.create(any())).thenThrow(ResourceAlreadyExistsException.class);
        mockMvc.perform(post("/articles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(this.articles.get(2)))
        ).andExpect(status().isConflict()
        ).andDo(print());
    }

    @Test
    void whenUpdating_shouldReceiveArticleToUpdate_andReturnNoContent() throws Exception {
        Article initial_article = articles.get(1);
        Article updated_article = new Article(initial_article.getId(), "updated", initial_article.getDescription(), null);
        ArgumentCaptor<Article> article_received = ArgumentCaptor.forClass(Article.class);

        mockMvc.perform(put("/articles/"+initial_article.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updated_article))
        ).andExpect(status().isNoContent());

        verify(articleService).update(anyLong(), article_received.capture());
        assertEquals(updated_article, article_received.getValue());
    }

    @Test
    void whenDeletingExisting_shouldCallServiceWithCorrectId_andSendNoContent() throws Exception {
        Long id = 28L;

        mockMvc.perform(delete("/articles/"+id)
        ).andExpect(status().isNoContent()
        ).andDo(print());

        ArgumentCaptor<Long> id_received = ArgumentCaptor.forClass(Long.class);
        Mockito.verify(articleService).delete(id_received.capture());
        assertEquals(id, id_received.getValue());
    }

}