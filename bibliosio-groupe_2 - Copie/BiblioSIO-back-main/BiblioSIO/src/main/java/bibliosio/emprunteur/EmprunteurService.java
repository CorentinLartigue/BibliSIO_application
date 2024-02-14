package bibliosio.emprunteur;

import java.util.List;

public interface EmprunteurService {
    List<Emprunteur> getAll();

    Emprunteur getById(Long id);

    Emprunteur create(Emprunteur emprunteur);

    Emprunteur update(Long id, Emprunteur emprunteur);

    boolean delete(Long id);
}
