package bibliosio.emprunt;

import java.util.List;

public interface EmpruntService {
    List<Emprunt> getAll();

    Emprunt getById(Long id);

    Emprunt create(Emprunt emprunt);

    Emprunt update(Long id, Emprunt emprunt);

    boolean delete(Long id);
}
