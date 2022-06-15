package es.cesar.servicios;

import es.cesar.modelos.Adoptante;
import es.cesar.modelos.Usuario;
import es.cesar.repositorios.AdoptanteRepositorio;
import es.cesar.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
public class UsuarioServicio {

    @Autowired
    EmailServicio emailServicio;

    public void enviarEmail(Usuario u) throws MessagingException {
        emailServicio.enviarEmail(u.getEmail(), "Cambio de contraseña", "\n" +
                "<!doctype html>\n" +
                "<html lang=\"en-US\">\n" +
                "\n" +
                "<head>\n" +
                "    <meta content=\"text/html; charset=utf-8\" http-equiv=\"Content-Type\" />\n" +
                "    <title>Reset Password Email Template</title>\n" +
                "    <meta name=\"description\" content=\"Reset Password Email Template.\">\n" +
                "    <style type=\"text/css\">\n" +
                "        a:hover {text-decoration: underline !important;}\n" +
                "    </style>\n" +
                "</head>\n" +
                "\n" +
                "<body marginheight=\"0\" topmargin=\"0\" marginwidth=\"0\" style=\"margin: 0px; background-color: #f2f3f8;\" leftmargin=\"0\">\n" +
                "<!--100% body table-->\n" +
                "<table cellspacing=\"0\" border=\"0\" cellpadding=\"0\" width=\"100%\" bgcolor=\"#f2f3f8\"\n" +
                "       style=\"@import url(https://fonts.googleapis.com/css?family=Rubik:300,400,500,700|Open+Sans:300,400,600,700); font-family: 'Open Sans', sans-serif;\">\n" +
                "    <tr>\n" +
                "        <td>\n" +
                "            <table style=\"background-color: #f2f3f8; max-width:670px;  margin:0 auto;\" width=\"100%\" border=\"0\"\n" +
                "                   align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "                <tr>\n" +
                "                    <td style=\"height:80px;\">&nbsp;</td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td style=\"height:20px;\">&nbsp;</td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td>\n" +
                "                        <table width=\"95%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\"\n" +
                "                               style=\"max-width:670px;background:#fff; border-radius:3px; text-align:center;-webkit-box-shadow:0 6px 18px 0 rgba(0,0,0,.06);-moz-box-shadow:0 6px 18px 0 rgba(0,0,0,.06);box-shadow:0 6px 18px 0 rgba(0,0,0,.06);\">\n" +
                "                            <tr>\n" +
                "                                <td style=\"height:40px;\">&nbsp;</td>\n" +
                "                            </tr>\n" +
                "                            <tr>\n" +
                "                                <td style=\"padding:0 35px;\">\n" +
                "                                    <h1 style=\"color:#1e1e2d; font-weight:500; margin:0;font-size:32px;font-family:'Rubik',sans-serif;\">Has cambiado tu contraseña correctamente</h1>\n" +
                "                                    <span\n" +
                "                                            style=\"display:inline-block; vertical-align:middle; margin:29px 0 26px; border-bottom:1px solid #cecece; width:100px;\"></span>\n" +
                "                                    <p style=\"color:#455056; font-size:15px;line-height:24px; margin:0;\">\n" +
                "                                        Si no has sido tu el que ha solicitado cambiar tu contraseña pinche en el boton y envienos un correo para que podamos revisar su caso\n" +
                "                                    </p>\n" +
                "                                    <a href=\"mailto:catsandogshelter@gmail.com?subject=Cambio de contraseña indebido&body=Hola, me di de alta con el correo: "+ u.getEmail()+" y no he cambiado mi contraseña agradeceria que se revisara mi caso\"\n" +
                "                                       style=\"background:#34C75D;text-decoration:none !important; font-weight:500; margin-top:35px; color:#fff;text-transform:uppercase; font-size:14px;padding:10px 24px;display:inline-block;border-radius:50px;\">\n" +
                "                                        No he sido yo\n" +
                "                                    </a>\n" +
                "                                </td>\n" +
                "                            </tr>\n" +
                "                            <tr>\n" +
                "                                <td style=\"height:40px;\">&nbsp;</td>\n" +
                "                            </tr>\n" +
                "                        </table>\n" +
                "                    </td>\n" +
                "                <tr>\n" +
                "                    <td style=\"height:20px;\">&nbsp;</td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td style=\"height:80px;\">&nbsp;</td>\n" +
                "                </tr>\n" +
                "            </table>\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "</table>\n" +
                "<!--/100% body table-->\n" +
                "</body>\n" +
                "\n" +
                "</html>");
    }
}
