package es.cesar.controladores;

import es.cesar.modelos.Adoptante;
import es.cesar.modelos.Protectora;
import es.cesar.modelos.Usuario;
import es.cesar.repositorios.AdoptanteRepositorio;
import es.cesar.repositorios.ProtectoraRepositorio;
import es.cesar.repositorios.UsuarioRepositorio;
import es.cesar.servicios.AdoptanteServicio;
import es.cesar.servicios.ProtectoraServicio;
import es.cesar.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;


@Controller
@RequestMapping("/perfil")
public class PerfilController {

    @Autowired
    AdoptanteRepositorio adoptanteRepositorio;

    @Autowired
    AdoptanteServicio adoptanteServicio;

    @Autowired
    ProtectoraRepositorio protectoraRepositorio;

    @Autowired
    ProtectoraServicio protectoraServicio;

    @Autowired
    UsuarioRepositorio usuarioRepositorio;

    @Autowired
    UsuarioServicio usuarioServicio;

    @GetMapping("/datos")
    public String getPerfil(HttpSession session){
        Usuario usuario = (Usuario) session.getAttribute("Usuario");
        String email = usuario.getEmail();
        Adoptante adoptante = adoptanteRepositorio.findByEmail(email);
        Protectora protectora = protectoraRepositorio.findByEmail(email);

        if(adoptante != null){
            session.setAttribute("protectora", null);
            session.setAttribute("adoptante", adoptante);
            return "perfil-datosAdoptante";
        }else if (protectora != null){
            session.setAttribute("protectora", protectora);
            return "perfil-datosProtectora";
        }else{
            return "redirect:/cerrarSesion";
        }
    }

    @PostMapping("/datosAdoptante")
    public String postPerfilAdoptante(Model model, HttpSession session, @RequestParam("nombre") String nombre, @RequestParam("apellidos") String apellidos, @RequestParam("telefono") String telefono, @RequestParam("file")MultipartFile imagen) {
        session.setAttribute("protectora", null);
        Usuario usuario = (Usuario) session.getAttribute("Usuario");
        String email = usuario.getEmail();
        Adoptante adoptante = adoptanteRepositorio.findByEmail(email);
        if (!imagen.isEmpty()) {

            String ruta = "C://Temp//uploads//adoptante";
            String rutaCorreo = ruta + "//" + adoptante.getEmail();
            File correo = new File(rutaCorreo);
            correo.mkdir();

            try {
                byte[] bytesImg = imagen.getBytes();
                Path rutaCompleta = Paths.get(rutaCorreo + "//" + imagen.getOriginalFilename());
                Files.write(rutaCompleta, bytesImg);
                adoptante.setFoto_perfil(imagen.getOriginalFilename());
            } catch (Exception e) {
                System.out.println("Error");
            }
            adoptanteRepositorio.save(adoptante);
            session.setAttribute("adoptante", adoptanteRepositorio.save(adoptanteServicio.setPerfil(adoptanteRepositorio.findByEmail(email), nombre, apellidos, email, telefono)));
            session.setAttribute("protectora", null);
            return "redirect:/perfil/datos";
        }
        session.setAttribute("protectora", null);
        return "redirect:/perfil/datos";
    }

    @PostMapping("/datosProtectora")
    public String postPerfilProtectora(HttpSession session, @RequestParam("emailProtectora") String emailProtectora, @RequestParam("nombreProtectora") String nombreProtectora, @RequestParam("CIF") String CIF, @RequestParam("formaJuridica") String formaJuridica, @RequestParam("telefonoProtectora") String telefonoProtectora, @RequestParam("domicilioSocial") String domicilioSocial, @RequestParam("NombrePersonaContacto") String nombrePersonaContacto, @RequestParam("ApellidosPersonaContacto") String apellidosPersonaContacto, @RequestParam("file")MultipartFile imagen){

        Usuario usuario = (Usuario) session.getAttribute("Usuario");
        String email = usuario.getEmail();
        Protectora protectora = protectoraRepositorio.findByEmail(email);

        if (!imagen.isEmpty()){

            String ruta = "C://Temp//uploads//protectora";
            String rutaCorreo = ruta + "//" + protectora.getCIF();
            File correo = new File(rutaCorreo);
            correo.mkdir();

            try{
                byte[] bytesImg = imagen.getBytes();
                Path rutaCompleta = Paths.get(rutaCorreo + "//" + imagen.getOriginalFilename());
                Files.write(rutaCompleta, bytesImg);
                protectora.setLogotipo_protectora(imagen.getOriginalFilename());
            } catch (Exception e){
                System.out.println("Error");
            }
        }

        protectoraRepositorio.save(protectora);

        session.setAttribute("adoptante", protectoraServicio.setProtectora(protectoraRepositorio.save(protectora), emailProtectora, nombreProtectora, CIF, formaJuridica, telefonoProtectora, domicilioSocial, nombrePersonaContacto, apellidosPersonaContacto));

        return "redirect:/perfil/datos";
    }

}
