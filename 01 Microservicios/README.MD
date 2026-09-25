# Guía Paso a Paso: Creación de un Microservicio Base en Spring Boot
Esta guía servirá de base para el desarrollo del Trabajo Práctico Nro. 1 de la materia y tiene como objetivo brindar los lineamientos necesarios para la construcción de un microservicio básico en Spring Boot. El contenido presentado en este documento contempla la primera etapa del proyecto; la completitud del archivo README.md junto con el código correspondiente deberá ser finalizada y entregada a través del repositorio asignado con fecha límite el 2 de Octubre de 2026. En el ejemplo se trabaja con el IDE IntelliJ IDEA Community, pero el proyecto funcionará también en cualquier otro IDE compatible.

# 1. Configuración del Proyecto en Spring Initializr
Para dar inicio al desarrollo, ingrese al sitio web start.spring.io y configure los datos del formulario de la siguiente manera:

**Project:** Maven

**Language:** Java

**Spring Boot:** Seleccionar una versión estable 4.1.1 (o superior de acuerdo a la fecha de lectura de esta guía)

**Group:** com.distribuido

**Artifact:** producto

**Packaging:** Jar

**Java:** 17 (o la versión correspondiente al entorno de trabajo configurado)

![Captura de Inicializr](https://github.com/gerardorosales2222/distribuidos/blob/main/01%20Microservicios/producto/src/main/resources/static/img/01initializr.jpg)

### Dependencias requeridas: 
En el panel lateral derecho, proceda a seleccionar las siguientes 5 dependencias:   
+ Spring Boot DevTools: Proporciona reinicios automáticos de la aplicación y recarga en vivo para acelerar el proceso de desarrollo.   
+ Spring Web: Permite la construcción de aplicaciones web y la exposición de servicios RESTful utilizando Spring MVC.   
+ Spring Data JPA: Facilita la persistencia de datos en bases de datos relacionales mediante la API de Java Persistence y Hibernate.   
+ MySQL Driver: Controlador JDBC indispensable para conectar la aplicación con el motor de base de datos MySQL.   
+ Lombok: Librería de herramientas para desarrolladores que ayuda a reducir el código repetitivo (boilerplate code), generando automáticamente getters, setters, constructores y más mediante anotaciones.   

Posteriormente, haga clic en GENERATE, descargue el archivo comprimido .zip y proceda a descomprimirlo en su equipo.

# 2. Importación y Apertura en IntelliJ IDEA
Inicie la aplicación IntelliJ IDEA, NetBeans o la que usted utilice para trabajar.

Seleccione la opción File > Open... y navegue hasta el directorio donde descomprimió el proyecto (producto).

Aguarde unos momentos mientras el gestor de dependencias Maven efectúa la descarga e indexación del proyecto.

Verifique que la estructura del directorio mantenga la siguiente forma:

``` Plaintext
src/
└── main/
    └── java/
        └── com/
            └── distribuido/
                └── producto/
                    └── ProductoApplication.java
```

![Estructura sin paquetes](https://github.com/gerardorosales2222/distribuidos/blob/main/01%20Microservicios/producto/src/main/resources/static/img/02sinPaquetes.jpg)

# 3. Creación de la Estructura de Paquetes
Con la finalidad de respetar estrictamente la arquitectura en capas (Controller → Service → Repository → DB), es necesario crear 4 paquetes dentro de la ruta principal com.distribuido.producto:

Haga clic derecho sobre el paquete base ***com.distribuido.producto***.

Diríjase a ***New > Package***.

Incorpore cada uno de los siguientes paquetes:

+ com.distribuido.producto.controller

+ com.distribuido.producto.model

+ com.distribuido.producto.repository

+ com.distribuido.producto.service

La jerarquía dentro del proyecto deberá visualizarse de la siguiente manera:

```Plaintext
com.distribuido.producto/
├── controller/
├── model/
├── repository/
├── service/
└── ProductoApplication.java
```

![Estructura con paquetes](https://github.com/gerardorosales2222/distribuidos/blob/main/01%20Microservicios/producto/src/main/resources/static/img/03conPaquetes.jpg)

# 4. Creación de la Entidad Producto.java
Vamos a empezar con el modelado de **Producto**, la clase Producto.java debe estar en el paquete Model.
Dentro del paquete model, se procederá a definir la clase de dominio que representará la tabla dentro de la base de datos. En este caso dado que se trata del primer microservicio que estamos haciendo utilizaremos únicamente tipos de datos elementales para facilitar la comprensión.

Seleccione ***New > Java Class*** e ingrese el nombre **Producto**.

---
# ***COMPLETAR***
---

# Consigna del Trabajo Práctico
Fecha límite de entrega: 2 de Octubre de 2026

**Consigna:**
Basándose en la explicación brindada en clase para las capas restantes y en la demostración práctica del profesor, complete este archivo README.md documentando de forma detallada:

+ La creación e implementación de la interfaz ProductoRepository dentro del paquete repository.

+ La creación e implementación de la clase ProductoService dentro del paquete service.

+ La creación e implementación de la clase ProductoController dentro del paquete controller.

+ La configuración del archivo application.properties para entablar la conexión con la base de datos MySQL.

+ Las instrucciones paso a paso para ejecutar la aplicación y verificar el funcionamiento de los endpoints utilizando Postman, Bruno o el navegador.

## Recursos de Apoyo para la Realización de la Guía
Para orientar el desarrollo correcto de la arquitectura y la configuración del entorno, considere los siguientes aspectos técnicos fundamentales:

Regla de Comunicación Estricta entre Capas
Al momento de diseñar e implementar el código de las capas faltantes, respete la jerarquía de llamadas del patrón arquitectónico:

```Plaintext
[ Controller ] -> [ Service ] -> [ Repository ] -> [ DataBase ]

El Controller llama al Service: Gestiona la petición HTTP y delega la ejecución de la lógica al servicio.

El Service llama al Repository: Procesa las reglas de negocio y solicita las operaciones de datos al repositorio.

El Repository llama a la DataBase: Ejecuta las consultas y la persistencia directa en el motor relacional.
```
### Configuración del Archivo application.properties
Para que el microservicio logre conectarse a la base de datos y ejecutarse en el puerto especificado, debe ingresar al archivo ubicado en la siguiente ruta. Recordar tener instalado y corriendo xampp:
```plaintext
src/main/resources/application.properties
```
Dentro de dicho archivo, incorpore las siguientes líneas de configuración:

```plaintext
server.port = 9001
spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mysql://localhost:3306/servicio_productos?serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=
```