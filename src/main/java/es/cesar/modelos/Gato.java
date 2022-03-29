package es.cesar.modelos;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class Gato extends Animal{

    private boolean vacuna_GripeFelina;
    private boolean vacuna_Panleucopenia;
    private boolean vacuna_LeucemiaFelina;
    private boolean castrado;
}
