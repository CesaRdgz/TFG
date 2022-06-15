package es.cesar.repositorios;

import es.cesar.modelos.Animal;
import es.cesar.modelos.Protectora;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ProtectoraRepositorioCRUD extends CrudRepository<Protectora, Long> {

    @Query("select p from Protectora p where p.nombre_protectora like %?1%")
    List<Protectora> findByNombre(String term);

}
