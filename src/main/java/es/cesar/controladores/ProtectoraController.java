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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/protectora")
public class ProtectoraController {

    @Autowired
    ProtectoraRepositorio protectoraRepositorio;

    @Autowired
    AdoptanteRepositorio adoptanteRepositorio;

    @Autowired
    ProtectoraServicio protectoraServicio;

    @Autowired
    PublicacionRepositorio publicacionRepositorio;

    @GetMapping("/{id}")
    public String getPerfil(@PathVariable int id, HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("Usuario");
        Adoptante adoptante = adoptanteRepositorio.findByEmail(usuario.getEmail());
        Optional<Protectora> protectoraOptional = protectoraRepositorio.findById(Long.valueOf(id));
        Protectora protectora = protectoraOptional.get();
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

        List<Publicacion> publicaciones = publicacionRepositorio.findByProtectora(protectora);
        if (publicaciones.size() != 0|| publicaciones != null) {
            Long likes = Long.valueOf(0);
            for (int i = 0; i < publicaciones.size(); i++) {
                likes = likes + publicaciones.get(i).getLikesRecibidos().size();
            }
            session.setAttribute("numeroLikes", likes);
        }

        List<Publicacion> publicacionesNoAdoptados = publicacionRepositorio.findByAnimal_AdoptadoAndProtectora(false, protectora);
        List<Publicacion> publicacionesAdoptados = publicacionRepositorio.findByAnimal_AdoptadoAndProtectora(true, protectora);

        long numeroSeguidores = protectora.getSeguidores().size();
        long numeroPublicaciones = publicacionesNoAdoptados.size() + publicacionesAdoptados.size();

        session.setAttribute("numeroPublicaciones", numeroPublicaciones);
        session.setAttribute("numeroSeguidores", numeroSeguidores);
        session.setAttribute("publicacionesNoAdoptados", publicacionesNoAdoptados);
        session.setAttribute("publicacionesAdoptados", publicacionesAdoptados);
        session.setAttribute("protectora", protectora);

        return "perfil";
    }

    @GetMapping("/seguir/{id}")
    public String getSeguir(@PathVariable int id, HttpSession session){

        Usuario usuario = (Usuario) session.getAttribute("Usuario");
        Adoptante adoptante = adoptanteRepositorio.findByEmail(usuario.getEmail());
        Optional<Protectora> protectoraId = protectoraRepositorio.findById(Long.valueOf(id));
        Protectora protectora = protectoraId.get();
        List<Adoptante> seguidores = protectora.getSeguidores();
        List<Protectora> siguiendo = adoptante.getSiguiendo();
        session.setAttribute("sigue", false);
        if (seguidores.size() == 0){
            seguidores.add(adoptante);
            siguiendo.add(protectora);
            session.setAttribute("sigue", true);
        } else {
            for (int i = 0; i < seguidores.size(); i++) {
                if (seguidores.get(i) != adoptante) {
                    siguiendo.add(protectora);
                    seguidores.add(adoptante);
                    session.setAttribute("sigue", true);
                    break;
                } else {
                    session.setAttribute("sigue", false);
                    return "perfil";
                }
            }
        }

        adoptante.setSiguiendo(siguiendo);
        protectora.setSeguidores(seguidores);
        adoptanteRepositorio.save(adoptante);
        protectoraRepositorio.save(protectora);

        long numeroSeguidores = protectora.getSeguidores().size();
        long numeroPublicaciones = protectora.getPublicacion().size();
        List<Publicacion> publicacionesNoAdoptados = publicacionRepositorio.findByAnimal_AdoptadoAndProtectora(false, protectora);
        List<Publicacion> publicacionesAdoptados = publicacionRepositorio.findByAnimal_AdoptadoAndProtectora(true, protectora);

        session.setAttribute("numeroPublicaciones", numeroPublicaciones);
        session.setAttribute("numeroSeguidores", numeroSeguidores);
        session.setAttribute("publicacionesNoAdoptados", publicacionesNoAdoptados);
        session.setAttribute("publicacionesAdoptados", publicacionesAdoptados);
        session.setAttribute("protectora", protectora);

        return "perfil";

    }

    @GetMapping("/noseguir/{id}")
    public String getNoseguir(@PathVariable int id, HttpSession session){

        Usuario usuario = (Usuario) session.getAttribute("Usuario");
        Adoptante adoptante = adoptanteRepositorio.findByEmail(usuario.getEmail());
        Optional<Protectora> protectoraId = protectoraRepositorio.findById(Long.valueOf(id));
        Protectora protectora = protectoraId.get();
        session.setAttribute("protectora", protectora);
        List<Adoptante> seguidores = protectora.getSeguidores();
        List<Protectora> siguiendo = adoptante.getSiguiendo();

        for (int i = 0; i < seguidores.size(); i++) {
            if (seguidores.get(i) == adoptante) {
                seguidores.remove(adoptante);
                siguiendo.remove(protectora);
                session.setAttribute("sigue", false);
                break;
            }
        }

        adoptante.setSiguiendo(siguiendo);
        protectora.setSeguidores(seguidores);
        adoptanteRepositorio.save(adoptante);
        protectoraRepositorio.save(protectora);

        long numeroSeguidores = protectora.getSeguidores().size();
        long numeroPublicaciones = protectora.getPublicacion().size();
        List<Publicacion> publicacionesNoAdoptados = publicacionRepositorio.findByAnimal_AdoptadoAndProtectora(false, protectora);
        List<Publicacion> publicacionesAdoptados = publicacionRepositorio.findByAnimal_AdoptadoAndProtectora(true, protectora);

        session.setAttribute("numeroPublicaciones", numeroPublicaciones);
        session.setAttribute("numeroSeguidores", numeroSeguidores);
        session.setAttribute("publicacionesNoAdoptados", publicacionesNoAdoptados);
        session.setAttribute("publicacionesAdoptados", publicacionesAdoptados);
        session.setAttribute("protectora", protectora);

        return "perfil";
    }
}
