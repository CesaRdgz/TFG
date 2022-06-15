package es.cesar.controladores;

import es.cesar.modelos.*;
import es.cesar.repositorios.*;
import es.cesar.servicios.PerroServicio;
import es.cesar.servicios.ProtectoraServicio;
import es.cesar.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/darDeAlta")
public class AltaController {

    @Autowired
    ProtectoraRepositorio protectoraRepositorio;

    @Autowired
    ProtectoraServicio protectoraServicio;

    @Autowired
    UsuarioRepositorio usuarioRepositorio;

    @Autowired
    UsuarioServicio usuarioServicio;

    @Autowired
    AnimalRepositorio animalRepositorio;

    @Autowired
    AnimalRepositorioCRUD animalRepositorioCRUD;

    @Autowired
    AdoptanteRepositorioCRUD adoptanteRepositorioCRUD;

    @Autowired
    AdoptanteRepositorio adoptanteRepositorio;

    @Autowired
    PerroRepositorio perroRepositorio;

    @Autowired
    PerroServicio perroServicio;

    @Autowired
    GatoRepositorio gatoRepositorio;

    @GetMapping("/")
    public String darDeAlta (Model model, HttpSession session){
        Usuario usuario = (Usuario) session.getAttribute("Usuario");
        String email = usuario.getEmail();
        Protectora protectora = protectoraRepositorio.findByEmail(email);
        List<Animal> animal = animalRepositorio.findByProtectora(protectora);
        model.addAttribute("animal", animal);
        session.setAttribute("animales", animal);
        return "darDeAlta";
    }

    @GetMapping("/perro")
    public String getAltaPerro (Model model, HttpSession session){
        Usuario usuario = (Usuario) session.getAttribute("Usuario");
        String email = usuario.getEmail();
        Protectora protectora = protectoraRepositorio.findByEmail(email);
        model.addAttribute("perro", new Perro());
        return "darDeAlta";
    }

    @PostMapping("/perro")
    public String postAltaPerro (@ModelAttribute("perro")Perro perro, Model model, HttpSession session, @RequestParam(value = "file", required = false) MultipartFile imagen, @RequestParam("tipoVacuna") List<String> tipoVacuna){

        Usuario usuario = (Usuario) session.getAttribute("Usuario");
        String email = usuario.getEmail();
        Protectora protectora = protectoraRepositorio.findByEmail(email);

            if (!imagen.isEmpty()){

                String ruta = "C://Temp//uploads//perros";

                try{
                    byte[] bytesImg = imagen.getBytes();
                    Path rutaCompleta = Paths.get(ruta + "//" + imagen.getOriginalFilename());
                    Files.write(rutaCompleta, bytesImg);
                    perro.setFoto(imagen.getOriginalFilename());

                    perro.setProtectora(protectora);
                } catch (Exception e){
                    System.out.println("Error");
                }
            }

        for(int i = 0;i < tipoVacuna.size(); i++)
        {

            if (tipoVacuna.get(i).equals("Primera Vacuna")) {
                perro.setVacuna_primeraVacuna(true);
            }

            if (tipoVacuna.get(i).equals("Polivalente")) {
                perro.setVacuna_polivalente(true);
            }

            if (tipoVacuna.get(i).equals("Recuerdo Polivalente")) {
                perro.setVacuna_recuerdoPolivalente(true);
            }

            if (tipoVacuna.get(i).equals("Rabia")) {
                perro.setVacuna_rabia(true);
            }
        }
            perro.setAdoptado(false);
            model.addAttribute("perro", perroRepositorio.save(perro));

        return "redirect:/darDeAlta/";
    }

    @GetMapping("/gato")
    public String getAltaGato (Model model, HttpSession session){

        Usuario usuario = (Usuario) session.getAttribute("Usuario");
        String email = usuario.getEmail();
        Protectora protectora = protectoraRepositorio.findByEmail(email);
        model.addAttribute("gato", new Gato());
        return "darDeAlta";

    }

    @PostMapping("/gato")
    public String postAltaGato (@ModelAttribute("gato")Gato gato,Model model, HttpSession session, @RequestParam("tipoVacuna") List<String> tipoVacuna, @RequestParam(value = "file", required = false) MultipartFile imagen){

        Usuario usuario = (Usuario) session.getAttribute("Usuario");
        String email = usuario.getEmail();
        Protectora protectora = protectoraRepositorio.findByEmail(email);
        model.addAttribute("gato", new Gato());

        if (!imagen.isEmpty()){

            String ruta = "C://Temp//uploads////gatos";

            try{
                byte[] bytesImg = imagen.getBytes();
                Path rutaCompleta = Paths.get(ruta + "//" + imagen.getOriginalFilename());
                Files.write(rutaCompleta, bytesImg);
                gato.setFoto(imagen.getOriginalFilename());

                gato.setProtectora(protectora);
            } catch (Exception e){
                System.out.println("Error");
            }
        }

        for(int i = 0;i < tipoVacuna.size(); i++)
        {

            if (tipoVacuna.get(i).equals("Gripe Felina")) {
                gato.setVacuna_GripeFelina(true);
            }

            if (tipoVacuna.get(i).equals("Panleucopenia")) {
                gato.setVacuna_Panleucopenia(true);
            }

            if (tipoVacuna.get(i).equals("Leucemia Felina")) {
                gato.setVacuna_LeucemiaFelina(true);
            }

        }

            gato.setAdoptado(false);
            model.addAttribute("gato", gatoRepositorio.save(gato));

        return "redirect:/darDeAlta/";

    }

    @GetMapping("/editar/{id}")
    public String getEditarAnimal (@PathVariable("id") int id, Model model, HttpSession session) {
        Optional<Animal> animal = animalRepositorio.findById(Long.valueOf(id));
        Optional<Perro> perro = perroRepositorio.findById(Long.valueOf(id));
        Optional<Gato> gato = gatoRepositorio.findById(Long.valueOf(id));

        Animal animal1 = animal.get();
        if (!perro.isEmpty()) {
            Perro perro1 = perro.get();
            session.setAttribute("animal", animal1);
            session.setAttribute("perro", perro1);
            return "animal-editarPerro";
        } else {
            Gato gato1 = gato.get();
            session.setAttribute("animal", animal1);
            session.setAttribute("gato", gato1);
            return "animal-editarGato";
        }

    }

    @PostMapping("/editar/{id}")
    public String postEditarAnimal (@PathVariable("id") int id, Model model, HttpSession session,
                                    @RequestParam(name = "tipoVacuna", required = false) List<String> tipoVacuna, @RequestParam(value = "file", required = false) MultipartFile imagen, @RequestParam("nombre") String nombre, @RequestParam("genero") String genero, @RequestParam("edad") int edad, @RequestParam("chip") String chip, @RequestParam("castrado") String castrado, @RequestParam("raza") String raza){

        Usuario usuario = (Usuario) session.getAttribute("Usuario");
        String email = usuario.getEmail();
        Protectora protectora = protectoraRepositorio.findByEmail(email);

        Optional<Animal> animal = animalRepositorio.findById(Long.valueOf(id));
        Optional<Perro> perro = perroRepositorio.findById(Long.valueOf(id));
        Optional<Gato> gato = gatoRepositorio.findById(Long.valueOf(id));

        Animal animal1 = animal.get();
        if (!perro.isEmpty()){

            Perro perro1 = perro.get();

            if (imagen.isEmpty()){
                perro1.setFoto(perro1.getFoto());
            } else {

                String ruta = "C://Temp//uploads//perros";

                try{
                    byte[] bytesImg = imagen.getBytes();
                    Path rutaCompleta = Paths.get(ruta + "//" + imagen.getOriginalFilename());
                    Files.write(rutaCompleta, bytesImg);
                    perro1.setFoto(imagen.getOriginalFilename());

                    perro1.setProtectora(protectora);
                } catch (Exception e){
                    System.out.println("Error");
                }

            }
            if (tipoVacuna == null){

                perro1.setVacuna_primeraVacuna(false);
                perro1.setVacuna_polivalente(false);
                perro1.setVacuna_recuerdoPolivalente(false);
                perro1.setVacuna_rabia(false);

            } else {
                for (int i = 0; i < tipoVacuna.size(); i++) {
                    if (tipoVacuna.get(i).equals("Primera Vacuna")) {
                        perro1.setVacuna_primeraVacuna(true);
                    }

                    if (tipoVacuna.get(i).equals("Polivalente")) {
                        perro1.setVacuna_polivalente(true);
                    }

                    if (tipoVacuna.get(i).equals("Recuerdo Polivalente")) {
                        perro1.setVacuna_recuerdoPolivalente(true);
                    }

                    if (tipoVacuna.get(i).equals("Rabia")) {
                        perro1.setVacuna_rabia(true);
                    }
                }
            }

            perro1.setNombre(nombre);
            perro1.setGenero(genero);
            perro1.setEdad(edad);
            perro1.setChip(chip);
            perro1.setCastrado(castrado);
            perro1.setRaza(raza);

            perroRepositorio.save(perro1);

            session.setAttribute("animal", animal1);
            session.setAttribute("perro", perro1);

        } else {

            Gato gato1 = gato.get();

            if (imagen.isEmpty()) {
                gato1.setFoto(gato1.getFoto());
            } else {

                String ruta = "C://Temp//uploads//gatos";

                try {
                    byte[] bytesImg = imagen.getBytes();
                    Path rutaCompleta = Paths.get(ruta + "//" + imagen.getOriginalFilename());
                    Files.write(rutaCompleta, bytesImg);
                    gato1.setFoto(imagen.getOriginalFilename());

                    gato1.setProtectora(protectora);
                } catch (Exception e) {
                    System.out.println("Error");
                }

            }

            if (tipoVacuna == null) {

                    gato1.setVacuna_GripeFelina(false);
                    gato1.setVacuna_Panleucopenia(false);
                    gato1.setVacuna_LeucemiaFelina(false);

            } else {

                for (int i = 0; i < tipoVacuna.size(); i++) {
                    if (tipoVacuna.get(i).equals("Gripe Felina")) {
                        gato1.setVacuna_GripeFelina(true);
                    }
                    if (tipoVacuna.get(i).equals("Panleucopenia")) {
                        gato1.setVacuna_Panleucopenia(true);
                    }
                    if (tipoVacuna.get(i).equals("Leucemia Felina")) {
                        gato1.setVacuna_LeucemiaFelina(true);
                    }
                }
        }

            gato1.setNombre(nombre);
            gato1.setGenero(genero);
            gato1.setEdad(edad);
            gato1.setChip(chip);
            gato1.setCastrado(castrado);
            gato1.setRaza(raza);

            gatoRepositorio.save(gato1);

            session.setAttribute("animal", animal1);
            session.setAttribute("gato", gato1);
        }
        return "redirect:/darDeAlta/";
    }

    @GetMapping(value = "/desplegable/{term}", produces = {"application/json"})
    public @ResponseBody List<Animal> getPublicacion(@PathVariable String term){
        List<Animal> animal = animalRepositorioCRUD.findByNombre(term);
        return animal;
    }

    @PostMapping(value = "/adoptanteGato/{id}")
    public String postAdoptante(@PathVariable("id") int id, @RequestParam("searchInput") String nombreCorreo){
        String [] infoPartes = nombreCorreo.split(", ");
        String nombre = infoPartes[0];
        String correo = infoPartes[1];
        String [] nombreApellido = nombre.split(" ");

        String nombrefin;
        String apellidofin;

        if (nombreApellido.length == 2){
            nombrefin = nombreApellido[0];
            apellidofin = nombreApellido[1];
        } else {
            nombrefin = nombreApellido[0];
            apellidofin = nombreApellido[1] + " " + nombreApellido[2];
        }

        Adoptante adoptante = adoptanteRepositorio.findByEmailAndAndNombreAndApellidos(correo, nombrefin,apellidofin);
        Optional<Animal> animal = animalRepositorio.findById(Long.valueOf(id));
        animal.get().setAdoptante(adoptante);
        animal.get().setAdoptado(true);
        return "darDeAlta";
    }


}
