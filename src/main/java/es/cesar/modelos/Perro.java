package es.cesar.modelos;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class Perro extends Animal{

    public boolean vacuna_primeraVacuna;
    public boolean vacuna_polivalente;
    public boolean vacuna_recuerdoPolivalente;
    public boolean vacuna_rabia;

}
