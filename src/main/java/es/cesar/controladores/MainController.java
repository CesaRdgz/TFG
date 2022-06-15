package es.cesar.controladores;

import es.cesar.modelos.Adoptante;
import es.cesar.modelos.Protectora;
import es.cesar.modelos.Publicacion;
import es.cesar.modelos.Usuario;
import es.cesar.repositorios.AdoptanteRepositorio;
import es.cesar.repositorios.ProtectoraRepositorio;
import es.cesar.repositorios.PublicacionRepositorio;
import es.cesar.servicios.ProtectoraServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
public class MainController {

    @Autowired
    ProtectoraRepositorio protectoraRepositorio;

    @Autowired
    ProtectoraServicio protectoraServicio;

    @Autowired
    PublicacionRepositorio publicacionRepositorio;

    @Autowired
    AdoptanteRepositorio adoptanteRepositorio;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/indexAdoptante")
    public String indexAdoptante(HttpSession session) {
        session.setAttribute("protectora", null);
        Usuario usuario = (Usuario) session.getAttribute("Usuario");
        Adoptante adoptante = adoptanteRepositorio.findByEmail(usuario.getEmail());

        List<Protectora> protectoras = protectoraRepositorio.findAll();
        List<Protectora> protectorasSeguidas = adoptante.getSiguiendo();
        List<Protectora> presugerencias = new ArrayList<>();

        int size = protectorasSeguidas.size();

        for (Protectora protectora: protectoras) {
            if (!protectorasSeguidas.contains(protectora)){
                presugerencias.add(protectora);
            }
        }

        List<Protectora> sugerencias = new ArrayList<>();
        if (presugerencias.size() < 5){

            session.setAttribute("sugerencias", presugerencias);

        } else {

            for (int i = 0; i < 5; i++) {
                int random = (int) ((Math.random() * (presugerencias.size())));
                if (!sugerencias.contains(presugerencias.get(random))){
                    sugerencias.add(presugerencias.get(random));
                } else {
                    i--;
                }
            }


            session.setAttribute("sugerencias", sugerencias);
        }

        List<Protectora> protectoraSiguiendo = adoptante.getSiguiendo();
        List<Publicacion> publicacionesNoAdoptados = new ArrayList<>();
        List<Publicacion> publicacionesAdoptados = new ArrayList<>();

        for (int i = 0; i < protectoraSiguiendo.size(); i++) {
            List<Publicacion> publicacionesNoAdoptadosSinJuntar = publicacionRepositorio.findByAnimal_AdoptadoAndProtectora(false, protectoraSiguiendo.get(i));
            List<Publicacion> publicacionesAdoptadosSinJuntar = publicacionRepositorio.findByAnimal_AdoptadoAndProtectora(true, protectoraSiguiendo.get(i));
            for (int j = 0; j < publicacionesNoAdoptadosSinJuntar.size(); j++) {
                publicacionesNoAdoptados.add(publicacionesNoAdoptadosSinJuntar.get(j));
            }
            for (int j = 0; j < publicacionesAdoptadosSinJuntar.size(); j++) {
                publicacionesAdoptados.add(publicacionesAdoptadosSinJuntar.get(j));
            }
        }



        session.setAttribute("publicacionesNoAdoptados", publicacionesNoAdoptados);
        session.setAttribute("publicacionesAdoptados", publicacionesAdoptados);

        return "indexAdoptante";
    }

    @GetMapping("/indexProtectora")
    public String indexProtectora(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("Usuario");
        Protectora protectora = protectoraRepositorio.findByEmail(usuario.getEmail());
        long numeroSeguidores = protectora.getSeguidores().size();
        long numeroPublicaciones = protectora.getPublicacion().size();
        List<Publicacion> publicacionesNoAdoptados = publicacionRepositorio.findByAnimal_AdoptadoAndProtectora(false, protectora);
        List<Publicacion> publicacionesAdoptados = publicacionRepositorio.findByAnimal_AdoptadoAndProtectora(true, protectora);

        List<Publicacion> publicaciones = protectora.getPublicacion();
        Long likes = Long.valueOf(0);
        for (int i = 0; i < publicaciones.size(); i++) {
            likes = likes + publicaciones.get(i).getLikesRecibidos().size();
        }

        session.setAttribute("numeroPublicaciones", numeroPublicaciones);
        session.setAttribute("numeroSeguidores", numeroSeguidores);
        session.setAttribute("numeroLikes", likes);
        session.setAttribute("publicacionesNoAdoptados", publicacionesNoAdoptados);
        session.setAttribute("publicacionesAdoptados", publicacionesAdoptados);
        session.setAttribute("protectora", protectora);
        return "indexProtectora";
    }

    @GetMapping("/cerrarSesion")
    public String cerrarSesion(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }


}
