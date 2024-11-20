package bibliosio.revue;

import java.util.List;

public interface RevueService {
    List<Revue> getAll();

    Revue getById(Long value);

    Revue create(Revue revue);

    void update(Long code, Revue revue);

    boolean delete(Long id);
}
