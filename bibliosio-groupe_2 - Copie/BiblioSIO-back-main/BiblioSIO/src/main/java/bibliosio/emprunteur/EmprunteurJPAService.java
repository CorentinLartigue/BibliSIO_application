package bibliosio.emprunteur;

import bibliosio.exceptions.ResourceNotFoundException;
import bibliosio.exceptions.ResourceAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Qualifier("jpa")
public class EmprunteurJPAService implements EmprunteurService{
    private EmprunteurRepository emprunteurRepository;

    @Autowired
    public EmprunteurJPAService(EmprunteurRepository emprunteurRepository){
        this.emprunteurRepository=emprunteurRepository;
    }

    @Override
    public List<Emprunteur> getAll() {
        return emprunteurRepository.findAll();
    }

    @Override
    public Emprunteur getById(Long id) {
        Optional<Emprunteur> emprunteur = emprunteurRepository.findById(id);
        if (emprunteur.isPresent()) {
            return emprunteur.get();
        } else {
            throw new ResourceNotFoundException("Emprunteur", id);
        }
    }

    @Override
    public Emprunteur create(Emprunteur newEmprunteur) throws ResourceAlreadyExistsException {
        try {
            emprunteurRepository.findById(newEmprunteur.getId());
            throw new ResourceAlreadyExistsException("Emprunteur", newEmprunteur.getId());
        } catch (ResourceNotFoundException e) {
            emprunteurRepository.save(newEmprunteur);
            return newEmprunteur;
        }
    }


    @Override
    public Emprunteur update(Long id, Emprunteur updatedEmprunteur) throws ResourceNotFoundException {
        Optional<Emprunteur> found = emprunteurRepository.findById(id);
        if(found.isPresent()){
            Emprunteur emprunteur = found.get();
            emprunteurRepository.delete(emprunteur);
            emprunteurRepository.save(updatedEmprunteur);
            return updatedEmprunteur;
        }
        else{
            throw new ResourceNotFoundException("Emprunteur", updatedEmprunteur.getId());
        }


    }

    @Override
    public boolean delete(Long id) throws ResourceNotFoundException {
        Optional<Emprunteur> found = emprunteurRepository.findById(id);
        if(found.isPresent()){
            emprunteurRepository.deleteById(id);
            return true;
        }
        else {
            throw new ResourceNotFoundException("Emprunteur", id);
        }

    }
}
