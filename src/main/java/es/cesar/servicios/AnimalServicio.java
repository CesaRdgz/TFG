package es.cesar.servicios;

import es.cesar.modelos.Animal;
import es.cesar.modelos.Protectora;
import es.cesar.repositorios.AnimalRepositorio;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AnimalServicio {

    public List<Animal> busqueda (AnimalRepositorio animalRepositorio, Protectora protectora, String nombre, String edad, String tipo){

        List<Animal> animal = new ArrayList<>();

        if (nombre == "" || nombre == null && edad == "" || edad == null){
            animal = animalRepositorio.findByProtectoraAndTipo(protectora, tipo);
        } else if (nombre != null && edad == "" || edad == null){
            animal = animalRepositorio.findByNombreAndProtectoraAndTipo(nombre, protectora, tipo);
        } else if (nombre == "" || nombre == null && edad != null){
            animal = animalRepositorio.findByEdadAndProtectoraAndTipo(Integer.parseInt(edad), protectora, tipo);
        } else if (nombre != null && edad != null){
            animal = animalRepositorio.findByNombreAndEdadAndProtectoraAndTipo(nombre, Integer.parseInt(edad), protectora, tipo);
        }

        return animal;
    }

}
