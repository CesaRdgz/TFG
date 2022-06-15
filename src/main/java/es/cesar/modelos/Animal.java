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
@Table(name="Animal")
public class Animal {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "edad", nullable = false)
    private int edad;

    @Column(name = "genero", nullable = false)
    private String genero;

    @Column(name = "foto", nullable = false)
    private String foto;

    @Column(name = "chip", nullable = false)
    private String chip;

    @Column(name = "castrado", nullable = false)
    private String castrado;

    @Column(name = "raza", nullable = false)
    private String raza;

    @Column(name = "tipo", nullable = false)
    private String tipo;

    @Column(name = "adoptado", nullable = false)
    private Boolean adoptado;

    @OneToMany(mappedBy = "animal")
    @JsonIgnore
    private List<Publicacion> publicacion;

    @ManyToOne
    @JsonIgnore
    private Protectora protectora;

    @ManyToOne
    @JsonIgnore
    private Adoptante adoptante;
}