package es.cesar.modelos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Adoptante")
public class Adoptante extends Usuario{

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    private String nombre;
    private String apellidos;
    private Date fecha_nacimiento;
    private String DNI;
    private String telefono;
    private String foto_perfil;
}
