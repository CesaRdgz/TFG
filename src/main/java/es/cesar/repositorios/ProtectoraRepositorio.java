package es.cesar.repositorios;

import es.cesar.modelos.Protectora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProtectoraRepositorio extends JpaRepository<Protectora, Long> {

    Protectora findByEmail(String email);

    @Query("SELECT p FROM Protectora p WHERE p.email like :email AND  p.forma_juridica like :forma_juridica AND p.nombre_protectora like :nombre_protectora")
    Protectora findByEmailAndFormajuridicaAndNombreprotectora(String email, String forma_juridica, String nombre_protectora);


}
