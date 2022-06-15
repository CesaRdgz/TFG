$(document).ready(function() {
    $(".edit").hide();
    $(".card-publicacion").hover(function() {
        let elementId = $(this).attr("id");
        $("#" + elementId).on("click",function (){
            $("#" + elementId).hide();
            $("." + elementId).removeAttr("hidden");
            $("#" + elementId).addClass('order-last');

        });
        $("." + elementId).on("click",function (){
            $("." + elementId).prop("hidden", true);
            $("#" + elementId).show();
            $("#" + elementId).removeClass('order-last');
        });
    });
});
