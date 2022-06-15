let fechaMostrada;
let fechaRecogida;
fechaRecogida = document.getElementById("fecha_nacimiento").value.substr(0, 10);

let year = fechaRecogida.substring(0,4);
let month = fechaRecogida.substring(5,7);
let day = fechaRecogida.substring(8);

fechaMostrada = day + '/' +  month + '/' + year;

document.getElementById("fecha_nacimiento").value = fechaMostrada;



