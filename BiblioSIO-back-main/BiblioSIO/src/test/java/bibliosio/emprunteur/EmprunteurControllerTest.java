package bibliosio.emprunteur;

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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@ExtendWith(SpringExtension.class)
@WebMvcTest
@ContextConfiguration(classes = EmprunteurController.class)
@Import(ExceptionHandlingAdvice.class)

public class EmprunteurControllerTest {
    

    @Autowired
    MockMvc mockMvc;

    @MockBean
    EmprunteurService emprunteurService;
    private List<Emprunteur> emprunteurs;

    @BeforeEach
    void setUp() {
        emprunteurs = new ArrayList<>() {{
            add(new Emprunteur(1L,"Jean", "Paul", "jean.paul@gmail.com", "2022-2024" ,Classe.SIO1B,"Etudiant"));
            add(new Emprunteur(2L,"Pierre", "Feuille", "pierre.feuille@gmail.com","2022-2024" ,Classe.SIO2B,"Etudiant"));
            add(new Emprunteur(3L,"Emprunter3", "Benoit", "Benoit@gmail.com", "2022-2024" ,Classe.SIO1A,"Etudiant"));
            add(new Emprunteur(4L, "Emprunter4", "Didier", "Didier@gmail.com", "" ,Classe.PROF,"Professeur"));
            add(new Emprunteur(5L,"Emprunter5", "Pedro", "Pedro@gmail.com", "2022-2024" ,Classe.SIO2A,"Etudiant"));
            add(new Emprunteur(28L,"Emprunter28", "Loan", "Loan@gmail.com","" ,Classe.PROF,"Professeur"));
        }};
        when(emprunteurService.getAll()).thenReturn(emprunteurs);
        when(emprunteurService.getById(5L)).thenReturn(emprunteurs.get(4));
        when(emprunteurService.getById(49L)).thenThrow(ResourceNotFoundException.class);
    }

    @Test
    void whenGettingAll_shouldGet6_andBe200() throws Exception {

        mockMvc.perform(get("/emprunteurs")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()
        ).andExpect(jsonPath("$", hasSize(6))
        ).andDo(print());
    }

    @Test
    void whenGettingId7L_shouldReturnSame() throws Exception{
        mockMvc.perform(get("/emprunteurs/5")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()
        ).andExpect(jsonPath("$.id", is(5))
        ).andReturn();
    }

    @Test
    void whenGettingUnexistingId_should404() throws Exception {
        mockMvc.perform(get("/emprunteurs/49")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound()
        ).andDo(print());
    }

    @Test
    void whenCreatingNew_shouldReturnLink_andShouldBeStatusCreated() throws Exception {
        Emprunteur new_emprunteur = new Emprunteur(89L, "Emprunter89", "inconnu", "inconnu@gmail.com", "" ,Classe.SIO2B,"Professeur");
        ArgumentCaptor<Emprunteur> emprunteur_received = ArgumentCaptor.forClass(Emprunteur.class);
        when(emprunteurService.create(any())).thenReturn(new_emprunteur);

        mockMvc.perform(post("/emprunteurs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(new_emprunteur))
        ).andExpect(status().isCreated()
        ).andExpect(header().string("Location", "/emprunteurs/"+new_emprunteur.getId())
        ).andDo(print());

        verify(emprunteurService).create(emprunteur_received.capture());
        assertEquals(new_emprunteur, emprunteur_received.getValue());
    }

    @Test
    void whenCreatingWithExistingId_should404() throws Exception {
        when(emprunteurService.create(any())).thenThrow(ResourceAlreadyExistsException.class);
        mockMvc.perform(post("/emprunteurs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(this.emprunteurs.get(2)))
        ).andExpect(status().isConflict()
        ).andDo(print());
    }

//Test whenUpdating_shouldReceiveUserToUpdate_andReturnNoContent

    @Test
    void whenUpdating_shouldReceiveUserToUpdate_andReturnNoContent() throws Exception {
        Emprunteur initial_emprunteur = emprunteurs.get(1);
        Emprunteur updated_emprunteur = new Emprunteur(initial_emprunteur.getId(),initial_emprunteur.getNom(),initial_emprunteur.getPrenom(),initial_emprunteur.getMail(),initial_emprunteur.getPromo(),initial_emprunteur.getClasse(),initial_emprunteur.getRole());
        ArgumentCaptor<Emprunteur> emprunteur_received = ArgumentCaptor.forClass(Emprunteur.class);

        mockMvc.perform(put("/emprunteurs/"+initial_emprunteur.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updated_emprunteur))
        ).andExpect(status().isNoContent());

        verify(emprunteurService).update(anyLong(), emprunteur_received.capture());
        assertEquals(updated_emprunteur, emprunteur_received.getValue());
    }

    @Test
    void whenDeletingExisting_shouldCallServiceWithCorrectId_andSendNoContent() throws Exception {
        Long id = 28L;

        mockMvc.perform(delete("/emprunteurs/"+id)
        ).andExpect(status().isNoContent()
        ).andDo(print());

        ArgumentCaptor<Long> id_received = ArgumentCaptor.forClass(Long.class);
        Mockito.verify(emprunteurService).delete(id_received.capture());
        assertEquals(id, id_received.getValue());
    }

}
    
