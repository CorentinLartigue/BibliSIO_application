package bibliosio.revue;
import bibliosio.exceptions.ResourceNotFoundException;
import bibliosio.exceptions.ResourceAlreadyExistsException;

import bibliosio.exemplaire.Exemplaire;
import bibliosio.exemplaire.ExemplaireService;
import bibliosio.revue.Revue;
import bibliosio.revue.RevueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@Qualifier("jpa")
public class RevueJPAService implements RevueService {

    private RevueRepository revueRepository;
    @Autowired

    public RevueJPAService(RevueRepository revueRepository) {
        this.revueRepository = revueRepository;
    }

    @Override
    public List<Revue> getAll() {
        return revueRepository.findAll();
    }

    @Override
    public Revue getById(Long id) {
        Optional<Revue> revue = revueRepository.findById(id);
        if (revue.isPresent()) {
            return revue.get();
        } else {
            throw new ResourceNotFoundException("Revue", id);
        }
    }

    @Override
    public Revue create(Revue newRevue) {
        if(revueRepository.existsById(newRevue.getId())){
            throw new ResourceAlreadyExistsException("Revue",newRevue.getId());
        }
        else {
            return revueRepository.save(newRevue);
        }
    }

    @Override
    public void update(Long id, Revue updatedRevue) {
        if(revueRepository.existsById(id)){
            throw new ResourceNotFoundException("Revue",id);
        }
        else {
            revueRepository.save(updatedRevue);
        }

    }

    @Override
    public boolean delete(Long id) {
        Optional<Revue> found = revueRepository.findById(id);
        if(found.isPresent()){
            revueRepository.deleteById(id);
            return true;
        }
        else {
            throw new ResourceNotFoundException("Revue", id);
        }
    }
}
