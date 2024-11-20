package bibliosio.exemplaire;

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
@ContextConfiguration(classes = ExemplaireController.class)
@Import(ExceptionHandlingAdvice.class)
public class ExemplaireControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ExemplaireService exemplaireService;

    private List<Exemplaire> exemplaires;

    @BeforeEach
    void setUp() {
        exemplaires = new ArrayList<>() {{
            add(new Exemplaire(1L,null, "Machin", "novembre", "2023",null));
            add(new Exemplaire(2L,null, "Chose", "novembre", "2023",null));
            add(new Exemplaire(3L,null, "Truc", "novembre", "2023",null));
            add(new Exemplaire(14L,null, "higher", "novembre", "2023",null));
            add(new Exemplaire(7L,null, "lower", "novembre", "2023",null));
            add(new Exemplaire(28L,null, "way higher", "novembre", "2023",null));
        }};
        when(exemplaireService.getAll()).thenReturn(exemplaires);
        when(exemplaireService.getById(7L)).thenReturn(exemplaires.get(4));
        when(exemplaireService.getById(49L)).thenThrow(ResourceNotFoundException.class);
    }

    @Test
    void whenGettingAll_shouldGet6_andBe200() throws Exception {

        mockMvc.perform(get("/exemplaires")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()
        ).andExpect(jsonPath("$", hasSize(6))
        ).andDo(print());
    }

    @Test
    void whenGettingId7L_shouldReturnSame() throws Exception{
        mockMvc.perform(get("/exemplaires/7")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()
        ).andExpect(jsonPath("$.id", is(7))
        ).andReturn();
    }

    @Test
    void whenGettingUnexistingId_should404() throws Exception {
        mockMvc.perform(get("/exemplaires/49")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound()
        ).andDo(print());
    }

//Test failled whenCreatingNew_shouldReturnLink_andShouldBeStatusCreated(
    @Test
    void whenCreatingNew_shouldReturnLink_andShouldBeStatusCreated() throws Exception {
        Exemplaire new_exemplaire = new Exemplaire(23L,null, "bidule", "juin","2023", null);
        ArgumentCaptor<Exemplaire> exemplaire_received = ArgumentCaptor.forClass(Exemplaire.class);
        when(exemplaireService.create(any())).thenReturn(new_exemplaire);

        mockMvc.perform(post("/exemplaires")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(new_exemplaire))
        ).andExpect(status().isCreated()
        ).andExpect(header().string("Location", "/exemplaires/"+new_exemplaire.getId())
        ).andDo(print());

        verify(exemplaireService).create(exemplaire_received.capture());
        assertEquals(new_exemplaire, exemplaire_received.getValue());
    }

    @Test
    void whenCreatingWithExistingId_should404() throws Exception {
        when(exemplaireService.create(any())).thenThrow(ResourceAlreadyExistsException.class);
        mockMvc.perform(post("/exemplaires")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(this.exemplaires.get(1)))
        ).andExpect(status().isConflict()
        ).andDo(print());
    }

//Test whenUpdating_shouldReceiveUserToUpdate_andReturnNoContent

    @Test
    void whenUpdating_shouldReceiveExemplaireToUpdate_andReturnNoContent() throws Exception {
        Exemplaire initial_exemplaire = exemplaires.get(1);
        Exemplaire updated_exemplaire = new Exemplaire(initial_exemplaire.getId(),initial_exemplaire.getRevue(),initial_exemplaire.getTitre(),initial_exemplaire.getMoisParution(),initial_exemplaire.getAnneeParution(),initial_exemplaire.getStatut());
        ArgumentCaptor<Exemplaire> exemplaire_received = ArgumentCaptor.forClass(Exemplaire.class);

        mockMvc.perform(put("/exemplaires/"+initial_exemplaire.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updated_exemplaire))
        ).andExpect(status().isNoContent());

        verify(exemplaireService).update(anyLong(), exemplaire_received.capture());
        assertEquals(updated_exemplaire, exemplaire_received.getValue());
    }

    @Test
    void whenDeletingExisting_shouldCallServiceWithCorrectId_andSendNoContent() throws Exception {
        Long id = 28L;

        mockMvc.perform(delete("/exemplaires/"+id)
        ).andExpect(status().isNoContent()
        ).andDo(print());

        ArgumentCaptor<Long> id_received = ArgumentCaptor.forClass(Long.class);
        Mockito.verify(exemplaireService).delete(id_received.capture());
        assertEquals(id, id_received.getValue());
    }

}