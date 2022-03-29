package es.cesar.modelos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    private String nombre;
    private int edad;
    private String genero;
    private String foto;
    //private Raza raza;


}
