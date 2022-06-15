$(document).ready (function(){
    $(".buscador").autocomplete({
        source:function(request, response) {
            $.ajax({
                url: "/buscar/desplegable/" + request.term,
                dataType: "json",
                data: {
                    term: request.term
                },
                success: function (data) {
                    response($.map(data, function (item) {
                        return {
                            label: item.forma_juridica + ", " + item.nombre_protectora + ", " + item.email
                        };
                    }));
                },
            });
        },

        select:function(event, ui) {
            $(".buscador").val(ui.item.label);
            return false;
        },

        messages: {
            noResults: '',
            results: function() {}
        }
    });

    $(".desplegable").toggleClass(".ui-menu")
});