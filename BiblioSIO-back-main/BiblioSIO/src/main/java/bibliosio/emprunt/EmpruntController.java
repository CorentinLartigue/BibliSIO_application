package bibliosio.emprunt;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/emprunts")
@CrossOrigin(origins = "*")
public class EmpruntController {
    private EmpruntService empruntService;
    @Autowired
    public EmpruntController(EmpruntService empruntService) {
        this.empruntService = empruntService;
    }

    public EmpruntController(){
    }

    @GetMapping("")
    public List<Emprunt> getAll() {
        return empruntService.getAll();
    }

    @GetMapping("/{id}")
    public Emprunt getById(@PathVariable Long id) {
        return empruntService.getById(id);
    }

    @PostMapping("")
    public ResponseEntity createEmprunt(@RequestBody Emprunt emprunt) {
        Emprunt created_emprunt = empruntService.create(emprunt);
        return ResponseEntity.created(URI.create("/emprunts/"+ created_emprunt.getId().toString())).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity updateEmprunt(@PathVariable Long id, @RequestBody Emprunt emprunt) {
        empruntService.update(id, emprunt);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteEmprunt(@PathVariable Long id) {
        empruntService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
