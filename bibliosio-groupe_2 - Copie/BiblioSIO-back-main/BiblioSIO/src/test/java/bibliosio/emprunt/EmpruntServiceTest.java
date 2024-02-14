package bibliosio.emprunt;

import bibliosio.emprunteur.Emprunteur;
import bibliosio.exceptions.ResourceAlreadyExistsException;
import bibliosio.exceptions.ResourceNotFoundException;
import bibliosio.exemplaire.Exemplaire;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
@SpringBootTest(classes=EmpruntJPAService.class)
public class EmpruntServiceTest {

    @Autowired
    private EmpruntService empruntService;
    @MockBean
    private EmpruntRepository empruntRepository;
    private List<Emprunt> emprunts;

    private Emprunteur emprunteur1,emprunteur2;

    private Exemplaire exemplaire1, exemplaire2;

    @BeforeEach
    void setUp() {

        emprunteur1 = mock(Emprunteur.class);
        emprunteur2 = mock(Emprunteur.class);

        exemplaire1 = mock(Exemplaire.class);
        exemplaire2 = mock(Exemplaire.class);
        emprunts = new ArrayList<>(){{
            add(new Emprunt(1L, emprunteur1, exemplaire1, LocalDate.of(2023, 12, 1), LocalDate.of(2024, 12, 1), StatutEmprunt.EN_COURS));
            add(new Emprunt(2L, emprunteur2, exemplaire1,LocalDate.of(2023, 12, 1), LocalDate.of(2024, 12, 1), StatutEmprunt.EN_COURS));
            add(new Emprunt(3L, emprunteur2, exemplaire2,LocalDate.of(2023, 12, 1), LocalDate.of(2024, 12, 1), StatutEmprunt.EN_COURS));
        }};
        when(empruntRepository.findAll()).thenReturn(emprunts);
    }

    @Test
    void whenGettingAll_shouldReturn3() {
        assertEquals(3, empruntService.getAll().size());
    }

    @Test
    void whenGettingById_shouldReturnIfExists_andBeTheSame() {
        when(empruntRepository.findById(1L)).thenReturn((Optional.of(emprunts.get(0))));
        when(empruntRepository.findById(12L)).thenReturn((Optional.empty()));
        assertAll(
                () -> assertEquals(emprunts.get(0), empruntService.getById(1L)),
                () -> assertThrows(ResourceNotFoundException.class, () -> empruntService.getById(12L))
        );
    }

    @Test
    void whenCreating_ShouldReturnSame() {
        Emprunt toCreate = new Emprunt(3L, emprunteur2, exemplaire2,LocalDate.of(2023, 12, 1), LocalDate.of(2024, 12, 1), StatutEmprunt.EN_COURS);
        assertEquals(toCreate, empruntService.create(toCreate));
    }

    @Test
    void whenCreatingWithSameId_shouldReturnEmpty() {
        Emprunt same_emprunt = emprunts.get(0);

        assertThrows(ResourceAlreadyExistsException.class, ()->empruntService.create(same_emprunt));
    }

    @Test
    void whenUpdating_shouldModifyEmprunt() {
        Emprunt initial_emprunt = emprunts.get(2);
        Emprunt new_emprunt = new Emprunt(initial_emprunt.getId(),initial_emprunt.getEmprunteur(),initial_emprunt.getExemplaire(),initial_emprunt.getDateEmprunt(),initial_emprunt.getDateEcheance(),initial_emprunt.getStatut());

        empruntService.update(new_emprunt.getId(), new_emprunt);
        Emprunt updated_emprunt = empruntService.getById(initial_emprunt.getId());
        assertEquals(new_emprunt, updated_emprunt);
        assertTrue(empruntService.getAll().contains(new_emprunt));
    }

    @Test
    void whenUpdatingNonExisting_shouldThrowException() {
        Emprunt unemprunt = emprunts.get(2);

        assertThrows(ResourceNotFoundException.class, ()->empruntService.update(75L, unemprunt));
    }

    @Test
    void whenDeletingExistingEmprunt_shouldNotBeInEmpruntsAnymore() {
        Emprunt emprunt = emprunts.get(1);
        Long id = emprunt.getId();

        empruntService.delete(id);
        assertFalse(empruntService.getAll().contains(emprunt));
    }

    @Test
    void whenDeletingNonExisting_shouldThrowException() {
        Long id = 68L;

        assertThrows(ResourceNotFoundException.class, ()->empruntService.delete(id));
    }
}
