package bibliosio.exemplaire;

import java.util.List;

public interface ExemplaireService {
    List<Exemplaire> getAll();

    Exemplaire getById(Long value);

    Exemplaire create(Exemplaire exemplaire);

    Exemplaire update(Long code, Exemplaire exemplaire);

    boolean delete(Long id);
}
