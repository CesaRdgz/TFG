package es.cesar.servicios;

import es.cesar.modelos.Animal;
import es.cesar.modelos.Gato;
import es.cesar.modelos.Perro;
import es.cesar.modelos.Protectora;
import es.cesar.repositorios.GatoRepositorio;
import es.cesar.repositorios.PerroRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GatoServicio {

    public Gato conversion (Animal animal){

        String nombre = animal.getNombre();
        String raza = animal.getRaza();
        String castrado = animal.getCastrado();
        String genero = animal.getGenero();
        String chip = animal.getChip();
        Protectora protectora = animal.getProtectora();

        Gato gato = new Gato();
        gato.setNombre(nombre);
        gato.setRaza(raza);
        gato.setCastrado(castrado);
        gato.setGenero(genero);
        gato.setChip(chip);
        gato.setProtectora(protectora);

        return gato;
    }

}
