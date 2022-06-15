$(document).ready (function(){
    $(".searchInput").autocomplete({
        source:function(request, response) {
            $.ajax({
                url: "/adopcion/buscarAdoptante/" + request.term,
                dataType: "json",
                data: {
                    term: request.term
                },
                success: function (data) {
                    response($.map(data, function (item) {
                        return {
                            label: item.nombre + " " + item.apellidos + ", " + item.email
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

});