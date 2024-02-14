package bibliosio.exemplaire;

import bibliosio.article.Article;
import bibliosio.exceptions.ResourceAlreadyExistsException;
import bibliosio.exceptions.ResourceNotFoundException;
import bibliosio.revue.Revue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ContextConfiguration;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes=ExemplaireJPAService.class)
@SpringBootTest
public class ExemplaireServiceTest {

    @Qualifier("jpa")

    @Autowired
    private ExemplaireService exemplaireService;
    @MockBean
    private ExemplaireRepository exemplaireRepository;
    private List<Exemplaire> exemplaires;


    @BeforeEach
    void setUp() {

        exemplaires = new ArrayList<>(){{
            add(new Exemplaire(1L,null, "Machin", "novembre", "2023",null));
            add(new Exemplaire(2L,null, "Chose", "novembre", "2023",null));
            add(new Exemplaire(3L,null, "Truc", "novembre", "2023",null));
        }};
        Exemplaire exemplaire =exemplaires.get(0);
        when(exemplaireRepository.findById(1L)).thenReturn(Optional.of(exemplaire));
    }

    @Test
    void testGetAll_renvoie3(){
        when(exemplaireRepository.findAll()).thenReturn(exemplaires);
        assertEquals(3 , exemplaireService.getAll().size());
    }

    @Test
    void testGetById(){
        when(exemplaireRepository.findById(1L)).thenReturn(Optional.of(exemplaires.get(0)));
        when(exemplaireRepository.findById(12L)).thenReturn(Optional.empty());
        assertAll(
                ()-> assertEquals(exemplaires.get(0), exemplaireService.getById(1L)),
                ()-> assertThrows(ResourceNotFoundException.class, ()-> exemplaireService.getById(12L))
        );


    }

    @Test
    void testCreation(){
        Revue revue = new Revue(3L,"titre");
        Exemplaire exemplaire = new Exemplaire(5L ,null, "Titre1", "novembre 2010","statut",null);
        Exemplaire exemplaire1 = new Exemplaire(3L ,null, "Titre1", "novembre 2010","statut", null);
        when(exemplaireRepository.save(any(Exemplaire.class))).thenReturn(exemplaire);
        when(exemplaireRepository.existsById(exemplaire1.getId())).thenReturn(true);
        assertAll(
                ()-> assertEquals(exemplaire, exemplaireService.create(exemplaire)),
                ()-> assertThrows(ResourceAlreadyExistsException.class, ()-> exemplaireService.create(exemplaire1))
        );
    }

    @Test
    void testUpdate(){
        Exemplaire exemplaire = exemplaires.get(0);
        exemplaire.setTitre("newTitre");

        when(exemplaireRepository.existsById(exemplaire.getId())).thenReturn(true);
        when(exemplaireRepository.save(any(Exemplaire.class))).thenReturn(exemplaire);

        assertEquals(exemplaire, exemplaireService.update(exemplaire.getId(), exemplaire));
    }



    @Test
    void testUpdateError(){
        Revue revue = new Revue(3L,"titre");
        Exemplaire exemplaire = new Exemplaire(5L ,revue, "Titre1", "novembre 2010","statut", null);
        exemplaire.setId(3L);
        when(exemplaireRepository.exists(Example.of(exemplaire))).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> exemplaireService.update(exemplaire.getId(), exemplaire));
    }

    @Test
    void testDelete(){
        Exemplaire toDelete = exemplaires.get(0);
        when(exemplaireRepository.existsById(toDelete.getId())).thenReturn(true);
        exemplaireService.delete(toDelete.getId());
        verify(exemplaireRepository).deleteById(toDelete.getId());
    }

    @Test
    void testDeleteError(){
        Revue revue = new Revue(3L,"titre");
        Exemplaire toDelete = new Exemplaire(51L ,null, "Titre1", "novembre 2010","statut",null);
        doThrow(ResourceNotFoundException.class).when(exemplaireRepository).deleteById(any());

        assertThrows(ResourceNotFoundException.class, () -> exemplaireService.delete(toDelete.getId()));
    }
}
