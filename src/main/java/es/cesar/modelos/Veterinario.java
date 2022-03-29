package es.cesar.modelos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Veterinario")
public class Veterinario {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

}
