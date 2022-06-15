package es.cesar.servicios;

import es.cesar.modelos.Animal;
import es.cesar.modelos.Perro;
import es.cesar.modelos.Protectora;
import es.cesar.repositorios.PerroRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerroServicio {

    public Perro conversion (Animal animal){

        String nombre = animal.getNombre();
        String raza = animal.getRaza();
        String castrado = animal.getCastrado();
        String genero = animal.getGenero();
        String chip = animal.getChip();
        Protectora protectora = animal.getProtectora();

        Perro perro = new Perro();
        perro.setNombre(nombre);
        perro.setRaza(raza);
        perro.setCastrado(castrado);
        perro.setGenero(genero);
        perro.setChip(chip);
        perro.setProtectora(protectora);

        return perro;
    }

}
