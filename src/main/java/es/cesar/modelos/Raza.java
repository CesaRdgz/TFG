package es.cesar.modelos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Raza")
public class Raza {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    private String tipo_raza;
}
