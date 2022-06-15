package es.cesar.repositorios;


import es.cesar.modelos.Perro;
import es.cesar.modelos.Protectora;
import es.cesar.modelos.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerroRepositorio extends JpaRepository<Perro, Long> {
    public Perro findByPublicacion(Publicacion publicacion);

}
