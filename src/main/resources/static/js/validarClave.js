$(document).ready (function(){

    var pass1 = $('[name=contrasena]');
    var longitud = "Debe estar formada como minimo por 8 carácteres. Minimo una mayuscula, una minuscula y un numero";
    var vacio = "La contraseña no puede estar vacía";
    var confirmacion = "Contraseña valida";
    $("#boton").prop('disabled', false);
    //oculto por defecto el elemento span
    var span = $('<span></span>').insertAfter(pass1);
    span.hide();
    //función que comprueba las dos contraseñas
    function coincidePassword(){

        var valor1 = pass1.val();
        //muestro el span
        span.show().removeClass();
        //condiciones dentro de la función
        if(valor1.length==0 || valor1==""){

            span.text(vacio).addClass('vacio').addClass('negacion').addClass('alert-danger');
        } else if (valor1.length>=8 && valor1.match(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$$/)){
            $("#boton").prop('disabled', false);
            span.text(confirmacion).addClass('confirmacion').addClass('alert-success');
        } else if(valor1.length<8 || !valor1.match(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/)){
            span.text(longitud).addClass('negacion').addClass('alert-danger');
            $("#boton").prop('disabled', true);
        }

    }
    //ejecuto la función al soltar la tecla
    pass1.keyup(function(){
        coincidePassword();
    });

})