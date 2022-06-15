#!/bin/bash



echo "Creando el dockerfile..."

docker build -t dockerapp .

echo "Creado!"

echo "Levantando los contenedores"

docker compose up -d

echo "Listo!"

echo "Creando directorios..."

mkdir C:/Temp
mkdir C:/Temp/uploads
mkdir C:/Temp/uploads/adoptante
mkdir C:/Temp/uploads/publicaciones
mkdir C:/Temp/uploads/gatos
mkdir C:/Temp/uploads/perros
mkdir C:/Temp/uploads/protectora

mv perrogafas.png C:/Temp/uploads

echo "Fin!"