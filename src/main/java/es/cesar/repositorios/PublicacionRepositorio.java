package es.cesar.repositorios;

import es.cesar.modelos.Adoptante;
import es.cesar.modelos.Animal;
import es.cesar.modelos.Protectora;
import es.cesar.modelos.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface PublicacionRepositorio extends JpaRepository<Publicacion, Long> {
    public List<Publicacion> findByProtectora(Protectora protectora);
    public List<Publicacion> findByAnimal_AdoptadoAndProtectora(Boolean adoptado, Protectora protectora);
    public List<Publicacion> findByAndAnimal_AdoptadoAndLikesRecibidos(Boolean adoptado, Adoptante likesRecibidos);


    @Transactional
    int deleteByAnimal(Animal animal);

    @Override
    void deleteById(Long aLong);
}
