package es.cesar.repositorios;

import es.cesar.modelos.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AnimalRepositorioCRUD extends CrudRepository<Animal, Long> {

    @Query("select a from Animal a where a.nombre like %?1%")
    List<Animal> findByNombre(String term);

}
