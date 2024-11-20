package bibliosio.revue;

import bibliosio.revue.Revue;
import bibliosio.revue.RevueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/revues")
@CrossOrigin(origins = "*")
public class RevueController {

    private RevueService revueService;

    @Autowired
    public RevueController(RevueService revueService) {
        this.revueService = revueService;
    }




    public RevueController() {

    }


    @GetMapping("")
    public List<Revue> getAll(){
        return revueService.getAll();
    }

    @GetMapping("{id}")
    public Revue getRevueById(@PathVariable Long id) {

        return revueService.getById(id);
    }

    @PostMapping("")
    public ResponseEntity createRevue(Revue revue){
        Revue created = revueService.create(revue);
        return ResponseEntity.created(URI.create("/revues/"+ created.getId())).build();
    }

    @PutMapping("{id}")
    public ResponseEntity updateRevue(@PathVariable Long id, @RequestBody Revue revue){
        revueService.update(id, revue);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteRevue(@PathVariable Long id){
        revueService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
}
