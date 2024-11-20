package bibliosio.exemplaire;
import bibliosio.exceptions.ResourceNotFoundException;
import bibliosio.exceptions.ResourceAlreadyExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Qualifier("jpa")
public class ExemplaireJPAService implements ExemplaireService {

    private ExemplaireRepository exemplaireRepository;
    @Autowired
    public ExemplaireJPAService(ExemplaireRepository exemplaireRepository) {
        this.exemplaireRepository = exemplaireRepository;
    }

    @Override
    public List<Exemplaire> getAll() {
        return exemplaireRepository.findAll();
    }

    @Override
    public Exemplaire getById(Long id) {
        Optional<Exemplaire> exemplaire = exemplaireRepository.findById(id);
        if (exemplaire.isPresent()) {
            return exemplaire.get();
        } else {
            throw new ResourceNotFoundException("Exemplaire", id);
        }
    }

    @Override
    public Exemplaire create(Exemplaire newExemplaire) throws ResourceAlreadyExistsException{
        if(exemplaireRepository.existsById(newExemplaire.getId())){
            throw new ResourceAlreadyExistsException("Exemplaire",newExemplaire.getId());
        }
        else {
            return exemplaireRepository.save(newExemplaire);
        }
    }

    @Override
    public Exemplaire update(Long id, Exemplaire updatedExemplaire) throws ResourceNotFoundException {
        if(exemplaireRepository.existsById(id)){
            throw new ResourceNotFoundException("Exemplaire",id);
        }
        else {
            return exemplaireRepository.save(updatedExemplaire);
        }

    }

    @Override
    public boolean delete(Long id) throws ResourceNotFoundException {
        Optional<Exemplaire> found = exemplaireRepository.findById(id);
        if(found.isPresent()){
            exemplaireRepository.deleteById(id);
            return true;
        }
        else {
            throw new ResourceNotFoundException("Exemplaire", id);
        }

    }
}
