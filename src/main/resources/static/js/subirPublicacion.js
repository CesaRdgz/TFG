$(document).ready (function(){
    $(".searchInput").autocomplete({
        source:function(request, response) {
            $.ajax({
                url: "/darDeAlta/desplegable/" + request.term,
                dataType: "json",
                data: {
                    term: request.term
                },
                success: function (data) {
                    response($.map(data, function (item) {
                        return {
                            label: item.nombre + ", " + item.raza + ", " + item.edad
                        };
                    }));
                },
            });
        },

        select:function(event, ui) {
            $(".searchInput").val(ui.item.label);
            return false;
            },

        messages: {
            noResults: '',
            results: function() {}
        }
    });

    $(".middle").on("click", function (){
        $("#file").click();
    })

    $(".desplegable").toggleClass(".ui-menu")
});