# Spotify Client App

Esta es una aplicación web que permite a los usuarios buscar canciones utilizando la API de Spotify. También proporciona un sistema de autenticación de usuario utilizando JWT (JSON Web Token) para el registro de usuarios e inicio de sesión y tiene la capacidad de guardar canciones favoritas en una lista personal.

## Funcionalidades

- Registro de usuarios: Los usuarios pueden crear una nueva cuenta proporcionando su correo electrónico y contraseña.
- Inicio de sesión de usuarios: Los usuarios existentes pueden iniciar sesión utilizando su correo electrónico y contraseña registrados.
- Autenticación JWT: La aplicación utiliza tokens de autenticación y autorización basados en JSON Web Tokens.
- Búsqueda de canciones: Los usuarios pueden buscar canciones ingresando palabras clave en la barra de búsqueda.
- Resultados de búsqueda: La aplicación muestra los resultados de búsqueda con información relevante, como el nombre de la canción y el artista, asi como la posibilidad de escuchar un preview.
- Canciones favoritas: Los usuarios autenticados pueden guardar canciones en su lista de favoritos personal.

## Tecnologías Utilizadas

- Angular: Framework de frontend para construir la interfaz de usuario.
- Spring Boot: Framework de backend para implementar la API RESTful y la autenticación de usuarios.
- Spotify API: Utilizada para obtener información de canciones y realizar búsquedas.
- JWT (JSON Web Token): Autenticación y autorización basada en tokens.
- PostgreSQL: Base de datos relacional para almacenar datos de usuarios y canciones favoritas.

## Inicio

Para obtener una copia local del proyecto y ponerlo en funcionamiento, sigue estos pasos:

### Requisitos Previos

- Node.js: Asegúrate de tener Node.js instalado en tu sistema.
- Angular CLI: Instala Angular CLI globalmente ejecutando `npm install -g @angular/cli`.
- Java Development Kit (JDK): Instala JDK para ejecutar el backend de Spring Boot.
- PostgreSQL: Configura un servidor de base de datos y asegúrate de tenerlo en funcionamiento. Hibernate, utilizado en este proyecto, creará automáticamente la base de datos al iniciar la aplicación.

### Instalación

1. Clona el repositorio: git clone https://github.com/LucianoGH1/flexTechChallenge.git

2. Configuración del frontend:

- Navega al directorio `challengeFE`.
- Instala las dependencias ejecutando `npm install`.
- Inicia la applicación con `ng serve`.

3. Configuración del backend:

- Navega al directorio `challengeBE`.
- Configura la conexión a la base de datos en el archivo `application.yml`.
- Compila y ejecuta la aplicación utilizando Maven. Puedes utilizar los comandos `mvn clean install` y
`mvn spring-boot:run`

4. Accede a la aplicación:

- Abre tu navegador web y visita `http://localhost:4200` para acceder a la aplicación

## Uso

- Registra una nueva cuenta utilizando una dirección de correo electrónico válida y una contraseña (inicia sesion automaticamente)
- Inicia sesión en la aplicación con las credenciales registradas.
- Utiliza la barra de búsqueda para buscar canciones.
- Visualiza los resultados de búsqueda. 
- Para agregar una canción a tus favoritos, haz click en el icono de corazon.
- Para escuchar una preview de la canción, haz click en el icono de play.
