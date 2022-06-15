package es.cesar.controladores;

import es.cesar.modelos.Adoptante;
import es.cesar.modelos.Animal;
import es.cesar.repositorios.AdoptanteRepositorio;
import es.cesar.repositorios.AdoptanteRepositorioCRUD;
import es.cesar.repositorios.AnimalRepositorio;
import es.cesar.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/adopcion")
public class AdopcionController {

    @Autowired
    AnimalRepositorio animalRepositorio;

    @Autowired
    UsuarioRepositorio usuarioRepositorio;

    @Autowired
    AdoptanteRepositorio adoptanteRepositorio;

    @Autowired
    AdoptanteRepositorioCRUD adoptanteRepositorioCRUD;

    @GetMapping(value = "/{id}")
    public String getId(Model model, @PathVariable("id") int id, HttpSession session) {
        Optional<Animal> animal = animalRepositorio.findById(Long.valueOf(id));
        Animal animal1 = animal.get();
        model.addAttribute("animal", animal1);
        session.setAttribute("animal", animal1);
        session.setAttribute("adoptante", "null");
        return "adopcion";
    }

    @GetMapping(value = "/buscarAdoptante/{term}", produces = {"application/json"})
    public @ResponseBody List<Adoptante> getAdoptante(@PathVariable String term){
        List<Adoptante> adoptante = adoptanteRepositorioCRUD.findByNombre(term);
        return adoptante;
    }

    @PostMapping(value = "/busquedaAdoptante")
    public String postId(Model model, @RequestParam("searchInput") String nombreCorreo, HttpSession session) {

        String[] infoPartes = nombreCorreo.split(", ");

        if (infoPartes.length != 2) {
            return "adopcion";
        } else {

            String nombre = infoPartes[0];
            String correo = infoPartes[1];
            String[] nombreApellido = nombre.split(" ");

            String nombrefin;
            String apellidofin;

            if (nombreApellido.length == 2) {
                nombrefin = nombreApellido[0];
                apellidofin = nombreApellido[1];
            } else {
                nombrefin = nombreApellido[0];
                apellidofin = nombreApellido[1] + " " + nombreApellido[2];
            }

            Adoptante adoptante = adoptanteRepositorio.findByEmailAndAndNombreAndApellidos(correo, nombrefin, apellidofin);
            session.setAttribute("adoptante", adoptante);

            return "adopcion";
        }
    }

    @PostMapping("/adoptar")
    public String adoptar (HttpSession session){
        Animal animal = (Animal) session.getAttribute("animal");
        Adoptante adoptante = (Adoptante) session.getAttribute("adoptante");
        animal.setAdoptante(adoptante);
        animal.setAdoptado(true);
        animalRepositorio.save(animal);
        return "redirect:/darDeAlta/";
    }
}
