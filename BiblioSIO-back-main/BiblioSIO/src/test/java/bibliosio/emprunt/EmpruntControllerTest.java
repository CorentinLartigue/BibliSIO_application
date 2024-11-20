package bibliosio.emprunt;

import bibliosio.emprunteur.Emprunteur;
import bibliosio.emprunteur.EmprunteurService;
import bibliosio.exceptions.ExceptionHandlingAdvice;
import bibliosio.exceptions.ResourceAlreadyExistsException;
import bibliosio.exceptions.ResourceNotFoundException;
import bibliosio.exemplaire.Exemplaire;
import com.fasterxml.jackson.databind.ObjectMapper;
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


import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
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
@ContextConfiguration(classes = EmpruntController.class)
@Import(ExceptionHandlingAdvice.class)

public class EmpruntControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    EmpruntService empruntService;
    private List<Emprunt> emprunts;
    private Emprunteur emprunteur = Mockito.mock(Emprunteur.class);
    private Exemplaire exemplaire = Mockito.mock(Exemplaire.class);

    @BeforeEach
    void setUp() {
        emprunts = new ArrayList<>() {{
            add(new Emprunt(1L, emprunteur, exemplaire,LocalDate.of(2023, 12, 1), LocalDate.of(2024, 12, 1), StatutEmprunt.EN_COURS));
            add(new Emprunt(2L, emprunteur, exemplaire,LocalDate.of(2023, 12, 1), LocalDate.of(2024, 12, 1), StatutEmprunt.EN_COURS));
            add(new Emprunt(3L, emprunteur, exemplaire,LocalDate.of(2023, 12, 1), LocalDate.of(2024, 12, 1), StatutEmprunt.EN_COURS));
            add(new Emprunt(4L, emprunteur, exemplaire,LocalDate.of(2024, 12, 1),  LocalDate.of(2024, 12, 1), StatutEmprunt.EN_COURS));
            add(new Emprunt(5L, emprunteur, exemplaire,LocalDate.of(2023, 12, 1),  LocalDate.of(2024, 12, 1), StatutEmprunt.EN_COURS));
            add(new Emprunt(28L,emprunteur, exemplaire,LocalDate.of(2023, 12, 1), LocalDate.of(2024, 12, 1), StatutEmprunt.EN_COURS));
        }};
        when(empruntService.getAll()).thenReturn(emprunts);
        when(empruntService.getById(5L)).thenReturn(emprunts.get(4));
        when(empruntService.getById(49L)).thenThrow(ResourceNotFoundException.class);
    }

    @Test
    void whenGettingAll_shouldGet6_andBe200() throws Exception {

        mockMvc.perform(get("/emprunts")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()
        ).andExpect(jsonPath("$", hasSize(6))
        ).andDo(print());
    }

    @Test
    void whenGettingId7L_shouldReturnSame() throws Exception{
        mockMvc.perform(get("/emprunts/5")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()
        ).andExpect(jsonPath("$.id", is(5))
        ).andReturn();
    }

    @Test
    void whenGettingUnexistingId_should404() throws Exception {
        mockMvc.perform(get("/emprunts/49")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound()
        ).andDo(print());
    }

//Test failled whenCreatingNew_shouldReturnLink_andShouldBeStatusCreated(
    @Test
    void whenCreatingNew_shouldReturnLink_andShouldBeStatusCreated() throws Exception {
        Emprunt new_emprunt = new Emprunt(89L, emprunteur, exemplaire,LocalDate.of(2023, 12, 1), LocalDate.of(2024, 12, 1), StatutEmprunt.EN_COURS);
        when(empruntService.create(any())).thenReturn(new_emprunt);

        mockMvc.perform(post("/emprunts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(new_emprunt))
        ).andExpect(header().string("Location", "/emprunts/"+new_emprunt.getId())
        ).andDo(print());
    }
   //Test failled whenCreatingWithExistingId_should404()
    @Test
    void whenCreatingWithExistingId_should404() throws Exception {
        when(empruntService.create(any())).thenThrow(ResourceAlreadyExistsException.class);
        mockMvc.perform(post("/emprunts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(this.emprunts.get(2)))
        ).andExpect(status().isConflict()
        ).andDo(print());
    }

//Test whenUpdating_shouldReceiveUserToUpdate_andReturnNoContent

    @Test
    void whenUpdating_shouldReceiveUserToUpdate_andReturnNoContent() throws Exception {
        Emprunt initial_emprunt = emprunts.get(1);
        Emprunt updated_emprunt = new Emprunt(initial_emprunt.getId(),initial_emprunt.getEmprunteur(),initial_emprunt.getExemplaire(),initial_emprunt.getDateEmprunt(),initial_emprunt.getDateEcheance(),initial_emprunt.getStatut());
        ArgumentCaptor<Emprunt> emprunt_received = ArgumentCaptor.forClass(Emprunt.class);

        mockMvc.perform(put("/emprunts/"+initial_emprunt.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updated_emprunt))
        ).andExpect(status().isNoContent());

        verify(empruntService).update(anyLong(), emprunt_received.capture());
        assertEquals(updated_emprunt, emprunt_received.getValue());
    }

    @Test
    void whenDeletingExisting_shouldCallServiceWithCorrectId_andSendNoContent() throws Exception {
        Long id = 28L;

        mockMvc.perform(delete("/emprunts/"+id)
        ).andExpect(status().isNoContent()
        ).andDo(print());

        ArgumentCaptor<Long> id_received = ArgumentCaptor.forClass(Long.class);
        Mockito.verify(empruntService).delete(id_received.capture());
        assertEquals(id, id_received.getValue());
    }

}
