package es.cesar.repositorios;


import es.cesar.modelos.Adoptante;
import es.cesar.modelos.Animal;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AdoptanteRepositorioCRUD extends CrudRepository<Animal, Long> {

    @Query("select a from Adoptante a where a.nombre like %?1%")
    List<Adoptante> findByNombre(String term);

}
