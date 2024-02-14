package bibliosio.emprunteur;

import bibliosio.emprunt.*;
import bibliosio.exceptions.ResourceAlreadyExistsException;
import bibliosio.exceptions.ResourceNotFoundException;
import bibliosio.exemplaire.Exemplaire;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest(classes= EmprunteurJPAService.class)
public class EmprunteurServiceTest {
    @Autowired
    private EmprunteurService emprunteurService;
    @MockBean
    private EmprunteurRepository emprunteurRepository;
    private List<Emprunteur> emprunteurs;


    @BeforeEach
    void setUp() {
        emprunteurs = new ArrayList<>(){{
            add(new Emprunteur(1L,"Jean", "Paul", "jean.paul@gmail.com", "" ,Classe.SIO1B));
            add(new Emprunteur(2L,"Pierre", "Feuille", "pierre.feuille@gmail.com", "" ,Classe.SIO2B));
            add(new Emprunteur(3L,"Emprunter3", "Benoit", "Benoit@gmail.com", "" ,Classe.SIO1A));
        }};
        when(emprunteurRepository.findAll()).thenReturn(emprunteurs);
    }

    @Test
    void whenGettingAll_shouldReturn3() {
        assertEquals(3, emprunteurService.getAll().size());
    }

    @Test
    void whenGettingById_shouldReturnIfExists_andBeTheSame() {
        when(emprunteurRepository.findById(1L)).thenReturn((Optional.of(emprunteurs.get(0))));
        when(emprunteurRepository.findById(12L)).thenReturn((Optional.empty()));
        assertAll(
                () -> assertEquals(emprunteurs.get(0), emprunteurService.getById(1L)),
                () -> assertThrows(ResourceNotFoundException.class, () -> emprunteurService.getById(12L))
        );
    }

    @Test
    void whenCreating_ShouldReturnSame() {
        Emprunteur toCreate = new Emprunteur(3L,"Emprunter3", "Benoit", "Benoit@gmail.com", "" ,Classe.SIO1A);
        assertEquals(toCreate, emprunteurService.create(toCreate));
    }

    @Test
    void whenCreatingWithSameId_shouldReturnEmpty() {
        Emprunteur same_emprunteur = emprunteurs.get(0);

        assertThrows(ResourceAlreadyExistsException.class, ()->emprunteurService.create(same_emprunteur));
    }

    @Test
    void whenUpdating_shouldModifyEmprunteur() {
        Emprunteur initial_emprunteur = emprunteurs.get(2);
        Emprunteur new_emprunteur = new Emprunteur(initial_emprunteur.getId(),initial_emprunteur.getNom(),initial_emprunteur.getPrenom(),initial_emprunteur.getMail(),initial_emprunteur.getPromo(),initial_emprunteur.getClasse());

        emprunteurService.update(new_emprunteur.getId(), new_emprunteur);
        Emprunteur updated_emprunteur = emprunteurService.getById(initial_emprunteur.getId());
        assertEquals(new_emprunteur, updated_emprunteur);
        assertTrue(emprunteurService.getAll().contains(new_emprunteur));
    }

    @Test
    void whenUpdatingNonExisting_shouldThrowException() {
        Emprunteur unEmprunteur = emprunteurs.get(2);

        assertThrows(ResourceNotFoundException.class, ()->emprunteurService.update(75L, unEmprunteur));
    }

    @Test
    void whenDeletingExistingEmprunt_shouldNotBeInEmpruntsAnymore() {
        Emprunteur emprunteur = emprunteurs.get(1);
        Long id = emprunteur.getId();

        emprunteurService.delete(id);
        assertFalse(emprunteurService.getAll().contains(emprunteur));
    }

    @Test
    void whenDeletingNonExisting_shouldThrowException() {
        Long id = 68L;

        assertThrows(ResourceNotFoundException.class, ()->emprunteurService.delete(id));
    }
}
