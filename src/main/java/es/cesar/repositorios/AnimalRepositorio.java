package es.cesar.repositorios;

import es.cesar.modelos.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnimalRepositorio extends JpaRepository<Animal, Long> {

    public List<Animal> findByProtectora(Protectora protectora);
    public List<Animal> findByProtectoraAndTipo(Protectora protectora, String tipo);
    public List<Animal> findByNombreAndProtectoraAndTipo(String nombre, Protectora protectora, String tipo);
    public List<Animal> findByEdadAndProtectoraAndTipo(int edad, Protectora protectora, String tipo);
    public List<Animal> findByNombreAndEdadAndProtectoraAndTipo(String nombre, int edad, Protectora protectora, String tipo);
    public Animal findByNombreAndEdadAndRazaAndProtectora (String nombre, int edad, String raza, Protectora protectora);
}
