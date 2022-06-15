package es.cesar.controladores;

import es.cesar.modelos.*;
import es.cesar.repositorios.*;
import es.cesar.servicios.ProtectoraServicio;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/publicacion")
public class PublicacionController {

    @Autowired
    ProtectoraRepositorio protectoraRepositorio;

    @Autowired
    ProtectoraServicio protectoraServicio;

    @Autowired
    AnimalRepositorio animalRepositorio;

    @Autowired
    PublicacionRepositorio publicacionRepositorio;

    @Autowired
    AdoptanteRepositorio adoptanteRepositorio;


    @GetMapping("/subirPublicacion")
    public String subirPublicacion (HttpSession session){
        session.setAttribute("animal", null);
        return "subirPublicacion";
    }

    @PostMapping("/buscarAnimal")
    public String postBusquedaPublicacion(HttpSession session, @RequestParam("searchInput") String info){
        Usuario usuario = (Usuario) session.getAttribute("Usuario");
        String email = usuario.getEmail();
        Protectora protectora = protectoraRepositorio.findByEmail(email);


        String [] infoPartes = info.split(", ");
        if(infoPartes.length != 3){
            return "subirPublicacion";
        } else {
            String nombre = infoPartes[0];
            String raza = infoPartes[1];
            String edad = infoPartes[2];
            int edadNumero = Integer.parseInt(edad);
            Animal animal = animalRepositorio.findByNombreAndEdadAndRazaAndProtectora(nombre, edadNumero, raza, protectora);
            session.setAttribute("animal", animal);
        }

        return "subirPublicacion";
    }

    @PostMapping("/publicar")
    public String postpublicar(Model model, @ModelAttribute("publicacion") Publicacion publicacion, HttpSession session, @RequestParam("historia") String historia, @RequestParam("file") MultipartFile file) throws IOException {
        Animal animal = (Animal) session.getAttribute("animal");
        Usuario usuario = (Usuario) session.getAttribute("Usuario");
        String email = usuario.getEmail();
        Protectora protectora = protectoraRepositorio.findByEmail(email);

        if (animal.getTipo().equals("Perro")){
            if (!file.isEmpty()){

                String ruta = "C://Temp//uploads//publicaciones";
                String rutaPublicacion = ruta + "//" + protectora.getCIF();
                File filePublicacion = new File(rutaPublicacion);
                filePublicacion.mkdirs();
                try{
                    file.getOriginalFilename();
                    byte[] bytesImg = file.getBytes();
                    Path rutaCompleta = Paths.get(rutaPublicacion + "//" + file.getOriginalFilename());
                    Files.write(rutaCompleta, bytesImg);
                    publicacion.setFoto(file.getOriginalFilename());

                } catch (Exception e){
                    System.out.println("Error");
                }
            } else {

                String ruta = "C://Temp//uploads//publicaciones";
                String rutaPublicacion = ruta + "//" + protectora.getCIF();
                File filePublicacion = new File(rutaPublicacion);
                filePublicacion.mkdirs();

                String rutaElse = "C://Temp//uploads//perros//" + animal.getFoto();
                File fileAnimal = new File(rutaElse);

                FileInputStream input = new FileInputStream(fileAnimal);
                MultipartFile multipartFile = new MockMultipartFile("file",
                        fileAnimal.getName(), "text/plain", IOUtils.toByteArray(input));

                try{

                    byte[] bytesImg = Files.readAllBytes(fileAnimal.toPath());
                    Path rutaCompleta = Paths.get(rutaPublicacion + "//" + multipartFile.getOriginalFilename());
                    Files.write(rutaCompleta, bytesImg);
                    publicacion.setFoto(multipartFile.getOriginalFilename());

                } catch (Exception e){
                    System.out.println("Error");
                }
            }
        } else {
            if (!file.isEmpty()){

                String ruta = "C://Temp//uploads//publicaciones";
                String rutaPublicacion = ruta + "//" + protectora.getCIF();
                File filePublicacion = new File(rutaPublicacion);
                filePublicacion.mkdirs();
                try{
                    file.getOriginalFilename();
                    byte[] bytesImg = file.getBytes();
                    Path rutaCompleta = Paths.get(rutaPublicacion + "//" + file.getOriginalFilename());
                    Files.write(rutaCompleta, bytesImg);
                    publicacion.setFoto(file.getOriginalFilename());

                } catch (Exception e){
                    System.out.println("Error");
                }
            } else {

                String ruta = "C://Temp//uploads//publicaciones";
                String rutaPublicacion = ruta + "//" + protectora.getCIF();
                File filePublicacion = new File(rutaPublicacion);
                filePublicacion.mkdirs();

                String rutaElse = "C://Temp//uploads//gatos//" + animal.getFoto();
                File fileAnimal = new File(rutaElse);

                FileInputStream input = new FileInputStream(fileAnimal);
                MultipartFile multipartFile = new MockMultipartFile("file",
                        fileAnimal.getName(), "text/plain", IOUtils.toByteArray(input));

                try{

                    byte[] bytesImg = Files.readAllBytes(fileAnimal.toPath());
                    Path rutaCompleta = Paths.get(rutaPublicacion + "//" + multipartFile.getOriginalFilename());
                    Files.write(rutaCompleta, bytesImg);
                    publicacion.setFoto(multipartFile.getOriginalFilename());

                } catch (Exception e){
                    System.out.println("Error");
                }
            }
        }


        SimpleDateFormat dtf = new SimpleDateFormat("yyyy/MM/dd");
        Calendar calendar = Calendar.getInstance();
        Date dateObj = calendar.getTime();

        publicacion.setProtectora(protectora);
        publicacion.setFecha_publicacion(dateObj);
        publicacion.setPie_foto(historia);
        publicacion.setAnimal(animal);


        model.addAttribute("publicacion", publicacionRepositorio.save(publicacion));

        return "redirect:/publicacion/subirPublicacion";
    }

    @GetMapping("/editar/{id}")
    public String getEditar(@PathVariable int id, HttpSession session) {
        Optional<Publicacion> publicacion = publicacionRepositorio.findById(Long.valueOf(id));
        Publicacion publicacion1 = publicacion.get();
        Long idanimal = publicacion1.getAnimal().getId();
        Optional<Animal> animal = animalRepositorio.findById(idanimal);
        Animal animal1 = animal.get();
        session.setAttribute("animal", animal1);
        session.setAttribute("publicacion", publicacion1);
        return "editarPublicacion";
    }

    @PostMapping("/editar")
    public String postEditar(Model model, HttpSession session, @RequestParam("file") MultipartFile file, @RequestParam("historia")String historia) throws IOException {

        Usuario usuario = (Usuario) session.getAttribute("Usuario");
        Protectora protectora = protectoraRepositorio.findByEmail(usuario.getEmail());
        Publicacion publicacion1 = (Publicacion) session.getAttribute("publicacion");
        Animal animal = (Animal) session.getAttribute("animal");

        if (animal.getTipo().equals("Perro")){
            if (!file.isEmpty()){

                String ruta = "C://Temp//uploads//publicaciones";
                String rutaPublicacion = ruta + "//" + protectora.getCIF();
                File filePublicacion = new File(rutaPublicacion);
                filePublicacion.mkdirs();
                try{
                    file.getOriginalFilename();
                    byte[] bytesImg = file.getBytes();
                    Path rutaCompleta = Paths.get(rutaPublicacion + "//" + file.getOriginalFilename());
                    Files.write(rutaCompleta, bytesImg);
                    publicacion1.setFoto(file.getOriginalFilename());

                } catch (Exception e){
                    System.out.println("Error");
                }
            } else {

                String ruta = "C://Temp//uploads//publicaciones";
                String rutaPublicacion = ruta + "//" + protectora.getCIF();
                File filePublicacion = new File(rutaPublicacion);
                filePublicacion.mkdirs();

                String rutaElse = "C://Temp//uploads//perros//" + animal.getFoto();
                File fileAnimal = new File(rutaElse);

                FileInputStream input = new FileInputStream(fileAnimal);
                MultipartFile multipartFile = new MockMultipartFile("file", fileAnimal.getName(), "text/plain", IOUtils.toByteArray(input));

                try{

                    byte[] bytesImg = Files.readAllBytes(fileAnimal.toPath());
                    Path rutaCompleta = Paths.get(rutaPublicacion + "//" + multipartFile.getOriginalFilename());
                    Files.write(rutaCompleta, bytesImg);
                    publicacion1.setFoto(multipartFile.getOriginalFilename());

                } catch (Exception e){
                    System.out.println("Error");
                }
            }
        } else {
            if (!file.isEmpty()){

                String ruta = "C://Temp//uploads//publicaciones";
                String rutaPublicacion = ruta + "//" + protectora.getCIF();
                File filePublicacion = new File(rutaPublicacion);
                filePublicacion.mkdirs();
                try{
                    file.getOriginalFilename();
                    byte[] bytesImg = file.getBytes();
                    Path rutaCompleta = Paths.get(rutaPublicacion + "//" + file.getOriginalFilename());
                    Files.write(rutaCompleta, bytesImg);
                    publicacion1.setFoto(file.getOriginalFilename());

                } catch (Exception e){
                    System.out.println("Error");
                }
            } else {

                String ruta = "C://Temp//uploads//publicaciones";
                String rutaPublicacion = ruta + "//" + protectora.getCIF();
                File filePublicacion = new File(rutaPublicacion);
                filePublicacion.mkdirs();

                String rutaElse = "C://Temp//uploads//gatos//" + animal.getFoto();
                File fileAnimal = new File(rutaElse);

                FileInputStream input = new FileInputStream(fileAnimal);
                MultipartFile multipartFile = new MockMultipartFile("file",
                        fileAnimal.getName(), "text/plain", IOUtils.toByteArray(input));

                try{

                    byte[] bytesImg = Files.readAllBytes(fileAnimal.toPath());
                    Path rutaCompleta = Paths.get(rutaPublicacion + "//" + multipartFile.getOriginalFilename());
                    Files.write(rutaCompleta, bytesImg);
                    publicacion1.setFoto(multipartFile.getOriginalFilename());

                } catch (Exception e){
                    System.out.println("Error");
                }
            }
        }

        model.addAttribute("publicacion", publicacionRepositorio.save(publicacion1));

        return "redirect:/indexProtectora";
    }

    @GetMapping("/borrar/{id}")
    public String getBorrar (Model model, @PathVariable int id){
        publicacionRepositorio.deleteById(Long.valueOf(id));
        return "redirect:/indexProtectora";
    }

    @GetMapping("/like/{id}")
    public String getlike (Model model, @PathVariable int id, HttpSession session){
        Usuario usuario = (Usuario) session.getAttribute("Usuario");
        Adoptante adoptante = adoptanteRepositorio.findByEmail(usuario.getEmail());
        Optional<Publicacion> publicacion = publicacionRepositorio.findById((long) id);
        Publicacion publicacion1 = publicacion.get();
        List<Adoptante> likesRecibidos = publicacion1.getLikesRecibidos();
        List<Publicacion> likesDados = adoptante.getLikesDados();
        Protectora protectora = publicacion1.getProtectora();

        if (likesRecibidos.size()  == 0){
            likesRecibidos.add(adoptante);
            likesDados.add(publicacion1);
        } else {

            for (int i = 0; i < likesRecibidos.size(); i++) {
                if (!likesRecibidos.contains(adoptante)) {
                    likesRecibidos.add(adoptante);
                    likesDados.add(publicacion1);
                    break;
                }
            }
        }

        publicacion1.setLikesRecibidos(likesRecibidos);
        adoptante.setLikesDados(likesDados);

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
        session.setAttribute("adoptante", adoptanteRepositorio.findById(((Usuario) session.getAttribute("Usuario")).getId()));

        publicacionRepositorio.save(publicacion1);
        adoptanteRepositorio.save(adoptante);

        return "perfil";
    }

    @GetMapping("/like/feed/{id}")
    public String getlikefeed (Model model, @PathVariable int id, HttpSession session){
        Usuario usuario = (Usuario) session.getAttribute("Usuario");
        Adoptante adoptante = adoptanteRepositorio.findByEmail(usuario.getEmail());
        Optional<Publicacion> publicacion = publicacionRepositorio.findById((long) id);
        Publicacion publicacion1 = publicacion.get();
        List<Adoptante> likesRecibidos = publicacion1.getLikesRecibidos();
        List<Publicacion> likesDados = adoptante.getLikesDados();

        if (likesRecibidos.size()  == 0){
            likesRecibidos.add(adoptante);
            likesDados.add(publicacion1);
        } else {

            for (int i = 0; i < likesRecibidos.size(); i++) {
                if (!likesRecibidos.contains(adoptante)) {
                    likesRecibidos.add(adoptante);
                    likesDados.add(publicacion1);
                    break;
                }
            }
        }

        publicacion1.setLikesRecibidos(likesRecibidos);
        adoptante.setLikesDados(likesDados);

        publicacionRepositorio.save(publicacion1);
        adoptanteRepositorio.save(adoptante);

        return "indexAdoptante";
    }


    @GetMapping("/likes")
    public String indexProtectora(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("Usuario");
        Adoptante adoptante = adoptanteRepositorio.findByEmail(usuario.getEmail());

        List<Publicacion> publicacionesNoAdoptados = publicacionRepositorio.findByAndAnimal_AdoptadoAndLikesRecibidos(false, adoptante);
        List<Publicacion> publicacionesAdoptados = publicacionRepositorio.findByAndAnimal_AdoptadoAndLikesRecibidos(true, adoptante);

        session.setAttribute("publicacionesNoAdoptados", publicacionesNoAdoptados);
        session.setAttribute("publicacionesAdoptados", publicacionesAdoptados);

        return "likes";
    }

    @GetMapping("/quitarLike/{id}")
    public String getQuitarLike (Model model, @PathVariable int id, HttpSession session){
        Usuario usuario = (Usuario) session.getAttribute("Usuario");
        Adoptante adoptante = adoptanteRepositorio.findByEmail(usuario.getEmail());
        Optional<Publicacion> publicacion = publicacionRepositorio.findById((long) id);
        Publicacion publicacion1 = publicacion.get();
        List<Adoptante> likesRecibidos = publicacion1.getLikesRecibidos();
        List<Publicacion> likesDados = adoptante.getLikesDados();
        Protectora protectora = publicacion1.getProtectora();


        for (int i = 0; i < likesRecibidos.size(); i++) {
            if (likesRecibidos.contains(adoptante)) {
                likesRecibidos.remove(adoptante);
                likesDados.remove(publicacion1);
                break;
            }
        }


        publicacion1.setLikesRecibidos(likesRecibidos);
        adoptante.setLikesDados(likesDados);

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
        session.setAttribute("adoptante", adoptanteRepositorio.findById(((Usuario) session.getAttribute("Usuario")).getId()));

        publicacionRepositorio.save(publicacion1);
        adoptanteRepositorio.save(adoptante);

        return "redirect:/publicacion/likes";
    }

    @GetMapping("/seguidores")
    public String getSeguidores (HttpSession session){
        Usuario usuario = (Usuario) session.getAttribute("Usuario");
        Protectora protectora = protectoraRepositorio.findByEmail(usuario.getEmail());
        session.setAttribute("seguidores", protectora.getSeguidores());
        return "seguidores";
    }

    @GetMapping("/siguiendo")
    public String getSiguiendo (HttpSession session){
        Usuario usuario = (Usuario) session.getAttribute("Usuario");
        Adoptante adoptante = adoptanteRepositorio.findByEmail(usuario.getEmail());
        session.setAttribute("siguiendo", adoptante.getSiguiendo());
        return "seguidos";
    }
}
