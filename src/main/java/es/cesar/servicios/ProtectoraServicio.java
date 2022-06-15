package es.cesar.servicios;

import es.cesar.modelos.Protectora;
import es.cesar.repositorios.ProtectoraRepositorio;
import es.cesar.repositorios.PublicacionRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Optional;

@Service
public class ProtectoraServicio {
    @Autowired
    ProtectoraRepositorio protectoraRepositorio;

    @Autowired
    EmailServicio emailServicio;

    @Autowired
    PublicacionRepositorio publicacionRepositorio;

    public Protectora registrar(Protectora p, BCryptPasswordEncoder passwordEncoder) throws MessagingException {
        p.setContrasena(passwordEncoder.encode(p.getContrasena()));
        emailServicio.enviarEmail(p.getEmail(), "Cats&Dogs Shelter",
                "<!DOCTYPE html>\n" +
                        "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\n" +
                        "<head>\n" +
                        "    <meta charset=\"utf-8\">\n" +
                        "    <meta name=\"viewport\" content=\"width=device-width,initial-scale=1\">\n" +
                        "    <meta name=\"x-apple-disable-message-reformatting\">\n" +
                        "    <title></title>\n" +
                        "    <!--[if mso]>\n" +
                        "    <style>\n" +
                        "        table {border-collapse:collapse;border-spacing:0;border:none;margin:0;}\n" +
                        "        div, td {padding:0;}\n" +
                        "        div {margin:0 !important;}\n" +
                        "    </style>\n" +
                        "    <noscript>\n" +
                        "        <xml>\n" +
                        "            <o:OfficeDocumentSettings>\n" +
                        "                <o:PixelsPerInch>96</o:PixelsPerInch>\n" +
                        "            </o:OfficeDocumentSettings>\n" +
                        "        </xml>\n" +
                        "    </noscript>\n" +
                        "    <![endif]-->\n" +
                        "    <style>\n" +
                        "        table, td, div, h1, p {\n" +
                        "            font-family: Arial, sans-serif;\n" +
                        "        }\n" +
                        "        @media screen and (max-width: 530px) {\n" +
                        "            .unsub {\n" +
                        "                display: block;\n" +
                        "                padding: 8px;\n" +
                        "                margin-top: 14px;\n" +
                        "                border-radius: 6px;\n" +
                        "                background-color: #555555;\n" +
                        "                text-decoration: none !important;\n" +
                        "                font-weight: bold;\n" +
                        "            }\n" +
                        "            .col-lge {\n" +
                        "                max-width: 100% !important;\n" +
                        "            }\n" +
                        "        }\n" +
                        "        @media screen and (min-width: 531px) {\n" +
                        "            .col-sml {\n" +
                        "                max-width: 27% !important;\n" +
                        "            }\n" +
                        "            .col-lge {\n" +
                        "                max-width: 73% !important;\n" +
                        "            }\n" +
                        "        }\n" +
                        "    </style>\n" +
                        "</head>\n" +
                        "<body style=\"margin:0;padding:0;word-spacing:normal;background-color:#939297;\">\n" +
                        "<div role=\"article\" aria-roledescription=\"email\" lang=\"en\" style=\"text-size-adjust:100%;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;background-color:#939297;\">\n" +
                        "    <table role=\"presentation\" style=\"width:100%;border:none;border-spacing:0;\">\n" +
                        "        <tr>\n" +
                        "            <td align=\"center\" style=\"padding:0;\">\n" +
                        "                <!--[if mso]>\n" +
                        "                <table role=\"presentation\" align=\"center\" style=\"width:600px;\">\n" +
                        "                    <tr>\n" +
                        "                        <td>\n" +
                        "                <![endif]-->\n" +
                        "                <table role=\"presentation\" style=\"width:94%;max-width:600px;border:none;border-spacing:0;text-align:left;font-family:Arial,sans-serif;font-size:16px;line-height:22px;color:#363636;\">\n" +
                        "                    <tr>\n" +
                        "                        <td style=\"padding:40px 30px 30px 30px;text-align:center;font-size:24px;font-weight:bold;\">\n" +
                        "                            <a href=\"http://www.example.com/\" style=\"text-decoration:none;\"></a>\n" +
                        "                        </td>\n" +
                        "                    </tr>\n" +
                        "                    <tr>\n" +
                        "                        <td style=\"padding:30px;background-color:#ffffff;\">\n" +
                        "                            <h1 style=\"margin-top:0;margin-bottom:16px;font-size:26px;line-height:32px;font-weight:bold;letter-spacing:-0.02em;\">Bienvenido a Cats&Dogs shelter!</h1>\n" +
                        "                            <p style=\"margin:0;\">Bienvenido y gracias por confiar en cats&dogshelter para gestionar tu protectora con nosotros. Daremos lo mejor de nosotros para que ninguno de nuestros animalitos se quede sin su casa e intentaremos dar el mejor soporte posible.</p>\n" +
                        "                        </td>\n" +
                        "                    </tr>\n" +
                        "                    <tr>\n" +
                        "                        <td style=\"padding:0;font-size:24px;line-height:28px;font-weight:bold;\">\n" +
                        "                            <a href=\"http://www.example.com/\" style=\"text-decoration:none;\"><img src=\"https://eldiariony.com/wp-content/uploads/sites/2/2022/04/por-que-los-perros-mueven-la-cola.jpg?quality=60&strip=all&w=1200\" width=\"600\" alt=\"\" style=\"width:100%;height:auto;display:block;border:none;text-decoration:none;color:#363636;\"></a>\n" +
                        "                        </td>\n" +
                        "                    </tr>\n" +
                        "                    <tr>\n" +
                        "                        <td style=\"padding:35px 30px 11px 30px;font-size:0;background-color:#ffffff;border-bottom:1px solid #f0f0f5;border-color:rgba(201,201,207,.35);\">\n" +
                        "                            <!--[if mso]>\n" +
                        "                            <table role=\"presentation\" width=\"100%\">\n" +
                        "                                <tr>\n" +
                        "                                    <td style=\"width:145px;\" align=\"left\" valign=\"top\">\n" +
                        "                            <![endif]-->\n" +
                        "                            <div class=\"col-sml\" style=\"display:inline-block;width:100%;max-width:145px;vertical-align:top;text-align:left;font-family:Arial,sans-serif;font-size:14px;color:#363636;\">\n" +
                        "                                <img src=\"https://www.bunko.pet/__export/1653684640687/sites/debate/img/2022/05/27/gato-de-raza-azul-ruso.jpg_423682103.jpg\" width=\"115\" alt=\"\" style=\"width:115px;max-width:80%;margin-bottom:20px;border-radius: 60px;\">\n" +
                        "                            </div>\n" +
                        "                            <!--[if mso]>\n" +
                        "                            </td>\n" +
                        "                            <td style=\"width:395px;padding-bottom:20px;\" valign=\"top\">\n" +
                        "                            <![endif]-->\n" +
                        "                            <div class=\"col-lge\" style=\"display:inline-block;width:100%;max-width:395px;vertical-align:top;padding-bottom:20px;font-family:Arial,sans-serif;font-size:16px;line-height:22px;color:#363636;\">\n" +
                        "                                <p style=\"margin-top:0;margin-bottom:12px;\">Si tienes alguna sugerencia para implementar en futuras versiones de scribenos con el asunto ACTUALIZACION a catsandogshelter@gmail.com para echarles un vistazo y mas adelante poder implementarlas.</p>\n" +
                        "                                <p style=\"margin:0;\"><a href=\"https://www.youtube.com/channel/UCIxNtMvucPvqDZkAznmiEQw?sub_confirmation=1\" style=\"background: #34C75D; text-decoration: none; padding: 10px 25px; color: #ffffff; border-radius: 4px; display:inline-block; mso-padding-alt:0;text-underline-color:#34C75D\"><!--[if mso]><i style=\"letter-spacing: 25px;mso-font-width:-100%;mso-text-raise:20pt\">&nbsp;</i><![endif]--><span style=\"mso-text-raise:10pt;font-weight:bold;\">Suscribete!</span><!--[if mso]><i style=\"letter-spacing: 25px;mso-font-width:-100%\">&nbsp;</i><![endif]--></a></p>\n" +
                        "                            </div>\n" +
                        "                            <!--[if mso]>\n" +
                        "                            </td>\n" +
                        "                            </tr>\n" +
                        "                            </table>\n" +
                        "                            <![endif]-->\n" +
                        "                        </td>\n" +
                        "                    </tr>\n" +
                        "                    <tr>\n" +
                        "                        <td style=\"padding:30px;font-size:24px;line-height:28px;font-weight:bold;background-color:#ffffff;border-bottom:1px solid #f0f0f5;border-color:rgba(201,201,207,.35);\">\n" +
                        "                            <a href=\"http://www.example.com/\" style=\"text-decoration:none;\"><img src=\"https://www.tiendanimal.es/articulos/wp-content/uploads/2010/06/educacion-comportamiento-gato-perro-1200x720.jpg\" width=\"540\" alt=\"\" style=\"width:100%;height:auto;border:none;text-decoration:none;color:#363636;\"></a>\n" +
                        "                        </td>\n" +
                        "                    </tr>\n" +
                        "                    <tr>\n" +
                        "                        <td style=\"padding:30px;background-color:#ffffff;\">\n" +
                        "                            <p style=\"margin:0;\">Esperemos que disfrutes de esta, nuestra comunidad.</p>\n" +
                        "                        </td>\n" +
                        "                    </tr>\n" +
                        "                    <tr>\n" +
                        "                        <td style=\"padding:30px;text-align:center;font-size:12px;background-color:#404040;color:#cccccc;\">\n" +
                        "                            <p style=\"margin:0 0 8px 0;\"></a> <a href=\"https://twitter.com/intent/tweet?text=Me+acabo+de+registrar+en+Cats%26Dog+Shelter.+Una+plataforma+donde+encontrar+protectoras+de+animales+para+adoptar+a+estos.+%0d%0a%c2%bfTe+unes%3f+%c2%a1Es+gratis!%0d%0awww.cats%26dogshelter.com\" style=\"text-decoration:none;\"><img src=\"https://assets.codepen.io/210284/twitter_1.png\" width=\"40\" height=\"40\" alt=\"t\" style=\"display:inline-block;color:#cccccc;\"></a></p>\n" +
                        "                            <p style=\"margin:0;font-size:14px;line-height:20px;\">&reg; Someone, Somewhere 2021<br><a class=\"unsub\" href=\"http://www.example.com/\" style=\"color:#cccccc;text-decoration:underline;\">Unsubscribe instantly</a></p>\n" +
                        "                        </td>\n" +
                        "                    </tr>\n" +
                        "                </table>\n" +
                        "                <!--[if mso]>\n" +
                        "                </td>\n" +
                        "                </tr>\n" +
                        "                </table>\n" +
                        "                <![endif]-->\n" +
                        "            </td>\n" +
                        "        </tr>\n" +
                        "    </table>\n" +
                        "</div>\n" +
                        "</body>\n" +
                        "</html>");
        return protectoraRepositorio.save(p);
    }

    public Protectora setProtectora(Protectora protectora, String emailProtectora, String nombreProtectora, String CIF, String formaJuridica, String telefonoProtectora, String domicilioSocial, String nombrePersonaContacto, String apellidosPersonaContacto){
        protectora.setEmail(emailProtectora);
        protectora.setNombre_protectora(nombreProtectora);
        protectora.setCIF(CIF);
        protectora.setForma_juridica(formaJuridica);
        protectora.setTelefono_protectora(telefonoProtectora);
        protectora.setDomicilio_social(domicilioSocial);
        protectora.setNombre_personaContacto(nombrePersonaContacto);
        protectora.setApellidos_personaContacto(apellidosPersonaContacto);
        return protectora;
    }

}
