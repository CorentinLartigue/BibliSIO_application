package bibliosio.emprunt;
import bibliosio.exceptions.ResourceNotFoundException;
import bibliosio.exceptions.ResourceAlreadyExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Qualifier("jpa")
public class EmpruntJPAService implements EmpruntService {
    private EmpruntRepository empruntRepository;

    @Autowired
    public EmpruntJPAService(EmpruntRepository empruntRepository){
        this.empruntRepository=empruntRepository;
    }

    @Override
    public List<Emprunt> getAll() {
        return empruntRepository.findAll();
    }

    @Override
    public Emprunt getById(Long id) {
        Optional<Emprunt> emprunt = empruntRepository.findById(id);
        if (emprunt.isPresent()) {
            return emprunt.get();
        } else {
            throw new ResourceNotFoundException("Emprunt", id);
        }
    }

    @Override
    public Emprunt create(Emprunt newEmprunt) throws ResourceAlreadyExistsException {
        try {
            empruntRepository.findById(newEmprunt.getId());
            throw new ResourceAlreadyExistsException("Emprunt", newEmprunt.getId());
        } catch (ResourceNotFoundException e) {
            empruntRepository.save(newEmprunt);
            return newEmprunt;
        }
    }


    @Override
    public Emprunt update(Long id, Emprunt updatedEmprunt) throws ResourceNotFoundException {
        Optional<Emprunt> found = empruntRepository.findById(id);
        if(found.isPresent()){
            Emprunt emprunt = found.get();
            empruntRepository.delete(emprunt);
            empruntRepository.save(updatedEmprunt);
            return updatedEmprunt;
        }
        else{
            throw new ResourceNotFoundException("Emprunt", updatedEmprunt.getId());
        }


    }

    @Override
    public boolean delete(Long id) throws ResourceNotFoundException {
        Optional<Emprunt> found = empruntRepository.findById(id);
        if(found.isPresent()){
            empruntRepository.deleteById(id);
            return true;
        }
        else {
            throw new ResourceNotFoundException("Emprunt", id);
        }

    }
}
