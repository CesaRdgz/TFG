package es.cesar.controladores;

import es.cesar.modelos.Animal;
import es.cesar.repositorios.*;
import es.cesar.servicios.PerroServicio;
import es.cesar.servicios.ProtectoraServicio;
import es.cesar.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/darDeBaja")
public class BajaController {

    @Autowired
    AnimalRepositorio animalRepositorio;

    @Autowired
    PublicacionRepositorio publicacionRepositorio;
    @GetMapping("/animal/{id}")
    public String bajaAnimal (@PathVariable("id") int id, Model model, HttpSession session){
        Optional<Animal> animal = animalRepositorio.findById(Long.valueOf(id));
        Animal animal1 = animal.get();
        publicacionRepositorio.deleteByAnimal(animal1);
        animalRepositorio.delete(animal1);
        return "redirect:/darDeAlta/";
    }
}
