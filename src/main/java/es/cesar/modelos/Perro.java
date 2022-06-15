package es.cesar.modelos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Perro extends Animal{

    @Column(name = "vacuna_primeraVacuna", nullable = true)
    public boolean vacuna_primeraVacuna;

    @Column(name = "vacuna_polivalente", nullable = true)
    public boolean vacuna_polivalente;

    @Column(name = "vacuna_recuerdoPolivalente", nullable = true)
    public boolean vacuna_recuerdoPolivalente;

    @Column(name = "vacuna_rabia", nullable = true)
    public boolean vacuna_rabia;

}
