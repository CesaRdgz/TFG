package es.cesar.repositorios;

import es.cesar.modelos.Adoptante;
import es.cesar.modelos.Publicacion;
import es.cesar.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdoptanteRepositorio extends JpaRepository<Adoptante, Long> {

    public Adoptante findByEmail(String email);
    public Adoptante findByEmailAndAndNombreAndApellidos(String email, String nombre, String apellidos);

}
