package es.cesar.controladores;

import es.cesar.modelos.*;
import es.cesar.repositorios.*;
import es.cesar.servicios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/buscar")

public class BusquedaController {

    @Autowired
    ProtectoraRepositorio protectoraRepositorio;

    @Autowired
    ProtectoraServicio protectoraServicio;

    @Autowired
    AnimalRepositorio animalRepositorio;

    @Autowired
    AnimalServicio animalServicio;

    @Autowired
    ProtectoraRepositorioCRUD protectoraRepositorioCRUD;

    @Autowired
    PublicacionRepositorio publicacionRepositorio;

    @Autowired
    AdoptanteRepositorio adoptanteRepositorio;

    @PostMapping("/animal")
    public String buscar(Model model, HttpSession session, @RequestParam("selectBusqueda") String tipo, @RequestParam("nombreBusqueda") String nombre, @RequestParam("edadBusqueda") String edad){

        Usuario usuario = (Usuario) session.getAttribute("Usuario");
        String email = usuario.getEmail();
        Protectora protectora = protectoraRepositorio.findByEmail(email);

        List<Animal> animal = animalServicio.busqueda(animalRepositorio, protectora, nombre, edad, tipo);
        model.addAttribute("animal", animal);
        session.setAttribute("animales", animal);

        return "darDeAlta";
    }

    @GetMapping(value = "/desplegable/{term}", produces = {"application/json"})
    public @ResponseBody List<Protectora> getPublicacion(@PathVariable String term){
        List<Protectora> protectoras = protectoraRepositorioCRUD.findByNombre(term);
        return protectoras;
    }

    @PostMapping( "/perfil")
    public String postPublicacion(@RequestParam("buscador") String parametro, HttpSession session) {

        Protectora protectora = new Protectora();

            if(parametro.split(", ")[0].equals("") || parametro.split(", ") == null){
                return "redirect:/indexAdoptante/#openModal";
            } else if (parametro.split(", ").length <= 2) {
                List<Protectora> protectoras = protectoraRepositorio.findByNombre(parametro);
                protectora = protectoras.get(0);
            } else {

                String[] infoPartes = parametro.split(", ");
                String formaJuridica = infoPartes[0];
                String nombreEmpresa = infoPartes[1];
                String correo = infoPartes[2];

            Usuario usuario = (Usuario) session.getAttribute("Usuario");
            Adoptante adoptante = adoptanteRepositorio.findByEmail(usuario.getEmail());
            protectora = protectoraRepositorio.findByEmailAndFormajuridicaAndNombreprotectora(correo, formaJuridica, nombreEmpresa);
            List<Adoptante> seguidores = protectora.getSeguidores();
            session.setAttribute("sigue", false);
            if (seguidores.size() != 0) {
                for (int i = 0; i < seguidores.size(); i++) {
                    if (seguidores.get(i) == adoptante) {
                        seguidores.add(adoptante);
                        session.setAttribute("sigue", true);
                        break;
                    } else {
                        session.setAttribute("sigue", false);
                    }
                }
            }
        }

        List<Publicacion> publicaciones = publicacionRepositorio.findByProtectora(protectora);

        Long likes = Long.valueOf(0);
        for (int i = 0; i < publicaciones.size(); i++) {
            likes = likes + publicaciones.get(i).getLikesRecibidos().size();
        }

        List<Publicacion> publicacionesNoAdoptados = publicacionRepositorio.findByAnimal_AdoptadoAndProtectora(false, protectora);
        List<Publicacion> publicacionesAdoptados = publicacionRepositorio.findByAnimal_AdoptadoAndProtectora(true, protectora);

        long numeroSeguidores = protectora.getSeguidores().size();
        long numeroPublicaciones = publicacionesNoAdoptados.size() + publicacionesAdoptados.size();

        session.setAttribute("numeroPublicaciones", numeroPublicaciones);
        session.setAttribute("numeroSeguidores", numeroSeguidores);
        session.setAttribute("numeroLikes", likes);
        session.setAttribute("publicacionesNoAdoptados", publicacionesNoAdoptados);
        session.setAttribute("publicacionesAdoptados", publicacionesAdoptados);
        session.setAttribute("protectora", protectora);

        return "perfil";
    }
}