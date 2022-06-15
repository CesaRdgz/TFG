$(document).ready(function () {
    $('.mostrar').click(function show() {
            if ($('.contrasena').attr('data-status') === 'pass'){
                $(".ojo").removeClass('bi bi-eye').addClass('bi bi-eye-slash');
                $('.contrasena').prop('type', 'text');
                $('.contrasena').attr('data-status', 'show')
            } else {
                $(".ojo").removeClass('bi bi-eye-' +
                    '').addClass('bi bi-eye');
                $('.contrasena').prop('type', 'password');
                $('.contrasena').attr('data-status', 'pass');
            }
        })
});
