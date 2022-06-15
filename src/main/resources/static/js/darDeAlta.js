$(document).ready(function() {

    $("#mas").show();
    $("#lista").hide();

    $("#listado-registroAnimal").show();

    $("#formPerro").hide();
    $("#formGato").hide();


    $("#mas").on("click", function() {

        $("#mas").hide();
        $("#lista").show();

        $("#listado-registroAnimal").hide();
        $("#formGato").hide();
        $("#formPerro").show();


    })

    $("#lista").on("click", function() {

        $("#mas").show();
        $("#lista").hide();

        $("#listado-registroAnimal").show();
        $("#formPerro").hide();
        $("#formGato").hide();


    })

    $('#tipoPerro').change(function(){

        var tipo = $(this).val()

        if (tipo == "Perro"){
            $("#formPerro").show();
            $("#formGato").hide();
            $("#tipoPerro").val("Perro");
            $('#tipoGato').prop('selectedIndex',1);
        } else {
            $("#formPerro").hide();
            $("#formGato").show();
            $("#tipoPerro").val("Perro");
            $('#tipoGato').prop('selectedIndex',1);
        }
    })

    $('#tipoGato').change(function(){

        var tipo = $(this).val()

        if (tipo == "Perro"){
            $("#formPerro").show();
            $("#formGato").hide();
            $('#tipoPerro').prop('selectedIndex',0);
        } else {
            $("#formPerro").hide();
            $("#formGato").show();
            $('#tipoPerro').prop('selectedIndex',0);
        }
    })

    $(".nombre_forEach").each(function(i, v) {
        if (v.innerHTML.length >= 5) {
            v.innerHTML = v.innerHTML.substr(0,4) + "…";
        }

    })

    $(".header_toggle").on("click", function() {
        console.log("a")
        if ($("#nav-bar").hasClass(".show")) {
            $(".basura_forEach").hide();
            $(".check_foreach").hide();
            $(".editar_forEach").hide();

        }
    });

    $(".foto_perfil-publicacion-edit").on("click", function() {
        $("#file").click();
    })
})
