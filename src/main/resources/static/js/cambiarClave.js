$(document).ready (function(){

    var pass1 = $('[name=contrasenaNueva]');
    var pass2 = $('[name=repetirContrasenaNueva]');
    var input = $('.input')
    var confirmacion = "Las contraseñas coinciden";
    var longitud = "Debe estar formada como minimo por 8 carácteres. Minimo una mayuscula, una minuscula y un numero";
    var negacion = "No coinciden las contraseñas";
    var vacio = "La contraseña no puede estar vacía";
    //oculto por defecto el elemento span
    var span = $('<span></span>').insertAfter(pass2);
    $("#boton").prop('disabled', true);
    span.hide();
    //función que comprueba las dos contraseñas
    function coincidePassword(){
        var valor1 = pass1.val();
        var valor2 = pass2.val();
        //muestro el span
        span.show().removeClass();
        //condiciones dentro de la función
        if(valor1 != valor2){
            span.text(negacion).addClass('alert-danger').addClass('negacion');
            input.removeClass('input').addClass('error');
        }
        if(valor1.length==0 || valor1==""){
            span.text(vacio).addClass('alert-danger').addClass('negacion');
            input.removeClass('input').addClass('error');
        }
        if(valor1.length<8 || !valor1.match(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/)){
            span.text(longitud).addClass('negacion').addClass('alert-danger');
            input.removeClass('input').addClass('error');
        }
        if(valor1.length!=0 && valor1==valor2 && valor1.match(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/)){
            span.text(confirmacion).removeClass("negacion").removeClass('alert-danger').addClass('confirmacion').addClass('alert-success');
            input.removeClass('input').removeClass('error');
            $("#boton").prop('disabled', false);
        }
    }
    //ejecuto la función al soltar la tecla
    pass2.keyup(function(){
        coincidePassword();
    });

})