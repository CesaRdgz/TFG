package es.cesar.modelos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Adoptante extends Usuario{

    @Column(name = "nombre", nullable = true)
    private String nombre;

    @Column(name = "apellidos", nullable = true)
    private String apellidos;

    @Column(name = "fecha_nacimiento", nullable = true)
    private Date fecha_nacimiento;

    @Column(name = "DNI", nullable = true)
    private String DNI;

    @Column(name = "telefono", nullable = true)
    private String telefono;

    @Column(name = "foto_perfil", nullable = true)
    private String foto_perfil;

    @ManyToMany(mappedBy = "seguidores")
    private List<Protectora> siguiendo;

    @OneToMany
    private List <Animal> animal;

    @ManyToMany(mappedBy = "likesRecibidos")
    private List<Publicacion> likesDados;

}
