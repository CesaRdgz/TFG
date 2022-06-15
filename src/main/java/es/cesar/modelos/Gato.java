package es.cesar.modelos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Gato extends Animal{

    @Column(name = "vacuna_GripeFelina", nullable = true)
    private boolean vacuna_GripeFelina;

    @Column(name = "vacuna_Panleucopenia", nullable = true)
    private boolean vacuna_Panleucopenia;

    @Column(name = "vacuna_LeucemiaFelina", nullable = true)
    private boolean vacuna_LeucemiaFelina;

}
