package es.cesar.modelos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Protectora extends Usuario{

    @Column(name = "CIF", nullable = true)
    private String CIF;

    @Column(name = "nombre_protectora", nullable = true)
    private String nombre_protectora;

    @Column(name = "forma_juridica", nullable = true)
    private String forma_juridica;

    @Column(name = "domicilio_social", nullable = true)
    private String domicilio_social;

    @Column(name = "telefono_protectora", nullable = true)
    private String telefono_protectora;

    @Column(name = "logotipo_protectora", nullable = true)
    private String logotipo_protectora;

    @Column(name = "nombre_personaContacto", nullable = true)
    private String nombre_personaContacto;

    @Column(name = "apellidos_personaContacto", nullable = true)
    private String apellidos_personaContacto;

    @Column(name = "dni_personaContacto", nullable = true)
    private String dni_personaContacto;

    @ManyToMany
    @JsonIgnore
    private List<Adoptante> seguidores;

    @OneToMany(mappedBy = "protectora")
    private List<Publicacion> publicacion;

    @OneToMany(mappedBy = "protectora")
    private List<Animal> animales;

}
