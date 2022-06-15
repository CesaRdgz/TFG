package es.cesar.repositorios;

import es.cesar.modelos.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GatoRepositorio extends JpaRepository<Gato, Long> {
    public Gato findByPublicacion(Publicacion publicacion);
}
