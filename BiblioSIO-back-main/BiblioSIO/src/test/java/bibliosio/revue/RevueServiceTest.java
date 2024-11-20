package bibliosio.revue;

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
@SpringBootTest(classes=RevueJPAService.class)
public class RevueServiceTest {

    @Autowired
    private RevueService revueService;
    @MockBean
    private RevueRepository revueRepository;

    private Exemplaire exemplaire1,exemplaire2;
    private List<Revue> revues;


    @BeforeEach
    void setUp() {

        exemplaire1 = mock(Exemplaire.class);
        exemplaire2 = mock(Exemplaire.class);

        ArrayList<Revue> revues = new ArrayList<>() {{
            add(new Revue(1L, "Machin"));
            add(new Revue(2L, "Chose"));
            add(new Revue(3L, "Truc"));
        }};
        when(revueRepository.findAll()).thenReturn(revues);
    }

    @Test
    void whenGettingAll_shouldReturn3() {
        assertEquals(3, revueService.getAll().size());
    }

    @Test
    void whenGettingById_shouldReturnIfExists_andBeTheSame() {
        when(revueRepository.findById(1L)).thenReturn((Optional.of(revues.get(0))));
        when(revueRepository.findById(12L)).thenReturn((Optional.empty()));
        assertAll(
                () -> assertEquals(revues.get(0), revueService.getById(1L)),
                () -> assertThrows(ResourceNotFoundException.class, () -> revueService.getById(12L))
        );
    }

    @Test
    void whenCreating_ShouldReturnSame() {
        Revue toCreate = new Revue(4L, "higher");
        assertEquals(toCreate, revueService.create(toCreate));
    }

    @Test
    void whenCreatingWithSameId_shouldReturnEmpty() {
        Revue same_revue = revues.get(0);

        assertThrows(ResourceAlreadyExistsException.class, ()->revueService.create(same_revue));
    }

    @Test
    void whenUpdating_shouldModifyRevue() {
        Revue initial_revue = revues.get(2);
        Revue new_revue = new Revue(initial_revue.getId(),initial_revue.getTitre());

        revueService.update(new_revue.getId(), new_revue);
        Revue updated_revue = revueService.getById(initial_revue.getId());
        assertEquals(new_revue, updated_revue);
        assertTrue(revueService.getAll().contains(new_revue));
    }

    @Test
    void whenUpdatingNonExisting_shouldThrowException() {
        Revue unerevue = revues.get(2);

        assertThrows(ResourceNotFoundException.class, ()->revueService.update(75L, unerevue));
    }

    @Test
    void whenDeletingExistingRevue_shouldNotBeInRevuesAnymore() {
        Revue revue = revues.get(1);
        Long id = revue.getId();

        revueService.delete(id);
        assertFalse(revueService.getAll().contains(revue));
    }

    @Test
    void whenDeletingNonExisting_shouldThrowException() {
        Long id = 68L;

        assertThrows(ResourceNotFoundException.class, ()->revueService.delete(id));
    }
}
