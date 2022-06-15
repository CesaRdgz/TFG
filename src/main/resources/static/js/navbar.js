document.addEventListener("DOMContentLoaded", function(event) {

    const showNavbar = (toggleId, navId, bodyId, headerId) =>{
        const toggle = document.getElementById(toggleId),
            nav = document.getElementById(navId),
            bodypd = document.getElementById(bodyId),
            headerpd = document.getElementById(headerId)

        if(toggle && nav && bodypd && headerpd){
            toggle.addEventListener('click', ()=>{

                nav.classList.toggle('show')

                toggle.classList.toggle('bx-x')

                bodypd.classList.toggle('body-pd')

                headerpd.classList.toggle('body-pd')
            })
        }
    }

    showNavbar('header-toggle','nav-bar','body-pd','header')

    const linkColor = document.querySelectorAll('.nav_link')

    function colorLink(){
        if(linkColor){
            linkColor.forEach(l=> l.classList.remove('active'))
            this.classList.add('active')
        }
    }
    linkColor.forEach(l=> l.addEventListener('click', colorLink))

});

$(document).ready(function() {

    if( $(window).width() <= 768) {
        $("#div_ul-dropdown").hide();
        $("#div_media").show();
    } else {
        $("#div_ul-dropdown").show();
        $("#div_media").hide();
    }

    $(window).on('load resize', function() {

        if( $(window).width() <= 768) {

            $("#div_ul-dropdown").hide();
            $("#div_media").show();

            $("#ul-dropdown").hide();
            $("#ul-dropdown").attr('data-status', 'hidden');

            $("#media").hide();
            $("#media").attr('data-status', 'hidden');

        } else {

            $("#div_ul-dropdown").show();
            $("#ul-dropdown").hide();
            $("#ul-dropdown").attr('data-status', 'hidden');

            $("#div_media").hide();

        }
    })

    $("#engranaje_media").on("click", function() {
        var status = $("#media").attr('data-status');
        if(status === "show") {
            $("#media").hide();
            $("#media").attr('data-status', 'hidden');
        } else {
            $("#media").show();
            $("#media").attr('data-status', 'show');
        }

    })

    $("#engranaje_ul-dropdown").on("click", function() {
        $("#header-toggle").click();
        $("#ajustes_ul-dropdown").on("click", function() {
            var status = $("#ul-dropdown").attr('data-status');
            if(status === "show") {
                $("#ul-dropdown").hide();
                $("#ul-dropdown").attr('data-status', 'hidden');
            } else {
                $("#ul-dropdown").show();
                $("#ul-dropdown").attr('data-status', 'show');
            }
        })
    })
})
