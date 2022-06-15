package es.cesar.modelos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Publicacion")
public class Publicacion {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "fecha_publicacion", nullable = false)
    private Date fecha_publicacion;

    @Column(name = "pie_foto", nullable = false)
    private String pie_foto;

    @Column(name = "foto", nullable = false)
    private String foto;

    @ManyToOne
    @JsonIgnore
    private Protectora protectora;

    @ManyToMany
    @JsonIgnore
    private List<Adoptante> likesRecibidos;

    @ManyToOne
    private Animal animal;
}
