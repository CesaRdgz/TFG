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
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/registro")
public class RegistroController {

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

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @GetMapping("/adoptante")
    public String registroPersonaGet(Model model) {
        if (model.getAttribute("adoptante") == null) {
            return "registroPersona";
        } else {
            model.addAttribute("adoptante", new Adoptante());
            return "redirect:/";
        }
    }

    @PostMapping("/adoptante")
    public String registroPersonaPost(@Validated @ModelAttribute("adoptante") Adoptante adoptante, @RequestParam(value = "file", required = false) MultipartFile imagen, BindingResult result, Model model, @RequestParam("fecha_nacimiento") String fecha) throws MessagingException, IOException {

        if (model.getAttribute("adoptante") != null) {

            String regexp = "\\d{1,2}/\\d{1,2}/\\d{4}";
            if (!Pattern.matches(regexp, fecha)){
                return "redirect:/registro/adoptante/#openModalFecha";
            }

            List<Usuario> usuarios = usuarioRepositorio.findAll();

                for (int i = 0; i < usuarios.size(); i++) {
                    if (adoptante.getEmail().equals(usuarios.get(i).getEmail())){
                        return "redirect:/registro/adoptante/#openModal";
                    }
                }

            if (result.hasErrors()) {
                return "redirect:/adoptante/#openModal";
            } else {
                if (!imagen.isEmpty()){

                    String ruta = "C://Temp//uploads//adoptante";
                    String rutaCorreo = ruta + "//" + adoptante.getEmail();
                    File correo = new File(rutaCorreo);
                    correo.mkdir();

                    try{
                        byte[] bytesImg = imagen.getBytes();
                        Path rutaCompleta = Paths.get(rutaCorreo + "//" + imagen.getOriginalFilename());
                        Files.write(rutaCompleta, bytesImg);
                        adoptante.setFoto_perfil(imagen.getOriginalFilename());
                    } catch (Exception e){
                        System.out.println("Error");
                    }
                } else {

                    String ruta1 = "C://Temp//uploads//perrogafas.png";
                    File imagen1 = new File(ruta1);

                    String ruta2 = "C://Temp//uploads//adoptante";
                    String rutaCorreo2 = ruta2 + "//" + adoptante.getEmail();
                    File rutaFiles2 = new File(rutaCorreo2);
                    rutaFiles2.mkdirs();

                    FileInputStream input = new FileInputStream(imagen1);
                    MultipartFile multipartFile = new MockMultipartFile("file", imagen1.getName(), "text/plain", IOUtils.toByteArray(input));

                    byte[] bytesImg = multipartFile.getBytes();
                    Path rutaCompleta = Paths.get(rutaCorreo2 + "//" + multipartFile.getOriginalFilename());
                    Files.write(rutaCompleta, bytesImg);
                    adoptante.setFoto_perfil(multipartFile.getOriginalFilename());

                }
                model.addAttribute("adoptante", adoptanteServicio.registrar(adoptante, passwordEncoder));
                return "redirect:/#openModal";
            }
        } else {
            return "redirect:/";

        }
    }

    @GetMapping("/protectora")
    public String registroProtectoraGet(Model model) {
        if (model.getAttribute("protectora") == null) {
            return "registroProtectora";
        } else {
            model.addAttribute("protectora", new Protectora());
            return "redirect:/";
        }
    }

    @PostMapping("/protectora")
    public String registroProtectoraPost(@Validated @ModelAttribute("protectora") Protectora protectora, @RequestParam(value = "file", required = false) MultipartFile imagen, Model model) throws MessagingException {
        List<Usuario> usuarios = usuarioRepositorio.findAll();

        if (model.getAttribute("protectora") != null) {

            for (int i = 0; i < usuarios.size(); i++) {
                if (protectora.getEmail().equals(usuarios.get(i).getEmail())){
                    return "redirect:/registro/protectora/#openModal";
                }
            }

            if (!imagen.isEmpty()) {

                String ruta = "C://Temp//uploads//protectora";
                String rutaCorreo = ruta + "//" + protectora.getCIF();
                File correo = new File(rutaCorreo);
                correo.mkdir();

                try {
                    byte[] bytesImg = imagen.getBytes();
                    Path rutaCompleta = Paths.get(rutaCorreo + "//" + imagen.getOriginalFilename());
                    Files.write(rutaCompleta, bytesImg);
                    protectora.setLogotipo_protectora(imagen.getOriginalFilename());
                } catch (Exception e) {
                    System.out.println("Error");
                }
            }
            model.addAttribute("protectora", protectoraServicio.registrar(protectora, passwordEncoder));
            return "redirect:/#openModal";

        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/login")
    public String loginUsuarioGet(Model model){
        model.addAttribute("Usuario", new Usuario());
        return "redirect:/#openModal";
    }

    @PostMapping("/login")
    public String loginUsuarioPost(@RequestParam("email") String email, @RequestParam("contrasena") String contrasenaUncoded, HttpSession session) {

        session.setAttribute("Usuario", new Usuario());

        if (session.getAttribute("Usuario") != null) {
            String contrasenaCoded;
            if (adoptanteRepositorio.findByEmail(email) != null ) {

                Adoptante adoptante = adoptanteRepositorio.findByEmail(email);

                contrasenaCoded = adoptante.getContrasena();

                if (passwordEncoder.matches(contrasenaUncoded, contrasenaCoded)) {
                    session.setAttribute("Usuario", usuarioRepositorio.findByEmail(email));
                    return "redirect:/indexAdoptante";
                } else {
                    return "redirect:/#openModalError";
                }

            } else if (protectoraRepositorio.findByEmail(email) != null){

                Protectora protectora = protectoraRepositorio.findByEmail(email);
                contrasenaCoded = protectora.getContrasena();

                if (passwordEncoder.matches(contrasenaUncoded, contrasenaCoded)) {
                    session.setAttribute("Usuario", usuarioRepositorio.findByEmail(email));
                    return "redirect:/indexProtectora";
                } else {
                    return "redirect:/#openModal";
                }
            }
        }

        return "redirect:/#openModalError";

    }

    @GetMapping("/ccontrasena")
    public String postccontrasena(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("Usuario");
        String email = usuario.getEmail();
        Adoptante adoptante = adoptanteRepositorio.findByEmail(email);
        Protectora protectora = protectoraRepositorio.findByEmail(email);

        if(adoptante != null){
            session.setAttribute("protectora", null);
            session.setAttribute("adoptante", adoptante);
            return "perfil-cambiarContrasena";
        }else if (protectora != null){
            session.setAttribute("adoptante", null);
            session.setAttribute("protectora", protectora);
            return "perfil-cambiarContrasena";
        }else{
            return "redirect:/cerrarSesion";
        }
    }

    @PostMapping("/ccontrasena")
    public String getccontrasena(Model model, HttpSession session, @RequestParam("contrasenaNueva") String contrasenaNueva, @RequestParam("contrasenaAntigua") String contrasenaAntigua) throws MessagingException {
        Usuario usuario = (Usuario) session.getAttribute("Usuario");

        if (passwordEncoder.matches(contrasenaAntigua, usuario.getContrasena())) {
            String contrasenaNuevaCoded = passwordEncoder.encode(contrasenaNueva);
            usuario.setContrasena(contrasenaNuevaCoded);
            usuarioRepositorio.save(usuario);
            usuarioServicio.enviarEmail(usuario);
            return "redirect:/cerrarSesion";
        } else {
            return "perfil-cambiarContrasena";
        }
    }
}
