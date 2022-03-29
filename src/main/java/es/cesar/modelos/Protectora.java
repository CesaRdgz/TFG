package es.cesar.modelos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Protectora")
public class Protectora extends Usuario{

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    private String CIF;
    private String nombre_protectora;
    private String forma_juridica;
            //fundacion o asociacion
    private String domicilio_social;
    private String email_protectora;
    private String telefono_protectora;
    private String logotipo_protectora;
    private String fotos_protectora;
    private String nombre_personaContacto;
    private String apellidos_personaContacto;
    private String dni_personaContacto;


}
