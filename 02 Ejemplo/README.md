# Trabajo Práctico Ingeniería Inversa

El objetivo de esta actividad es lograr que ambos proyectos corran de manera simultánea en sus computadoras y extraer de ellos toda la información sobre como se conectan diferentes microservicios.

Trabajando en grupos (o de forma individual según indique el profesor), realicen un informe técnico o documento Markdown (README.md) que responda y detalle lo siguiente:

Mapa de Endpoints: Liste las rutas (URLs, métodos HTTP y parámetros) que ofrece cada microservicio basándose estrictamente en el código fuente.

Estructura del Proyecto: Describan brevemente cómo están organizadas las capas en cada proyecto (Controladores, Servicios, Repositorios, Entidades/Modelos).

Independencia vs. Comunicación: Analicen si los microservicios operan completamente aislados o si existe algún punto de contacto o dependencia entre ellos.

**Conclusiones:** Redacten una breve reflexión sobre las ventajas de separar un sistema monolítico en estos dos microservicios.

## Informe Técnico: Arquitectura de Microservicios ( Pacientes y Turnos)

### en este informe tecnico de los microservicios de Pacientes y Turnos se explicara lo siguiente:
---
## 1-Mapa de Endpoints:
**Microservicio:Paciente**: 
+ **Crear Paciente**:
  + **URL:** /pacientes/crear
  + **Metodo HTTP:** Post
  + **Parametros:** Objeto **Paciente** en el cuerpo de petición (@RequestBody).
  
+ **Traer Todos los Pacientes**
  + **URL:** /pacientes/traer
  + **Método HTTP:** GET
  + **Parámetros:** Ninguno.
  
+ **Eliminar un Paciente**
  + **URL:** /pacientes/borrar/{id}
  + **Método HTTP:** DELETE
  + **Parámetros:** @PathVariable Long id.
  
+ **Editar un Paciente**
  + **URL:** /pacientes/editar/{id_original}
  + **Método HTTP:** PUT
  + **Parámetros:** @PathVariable Long id_original y un @RequestBody Paciente pacienteEditar.

+ **Obtener un Paciente por ID**
  + **URL:** /pacientes/traer/{id}
  + **Método HTTP:** GET
  + **Parámetros:** @PathVariable Long id.

+ **Obtener un Paciente por DNI**
  + **URL:** /pacientes/traerdni/{dni}
  + **Método HTTP:** GET
  + **Parámetros:** @PathVariable String dni.
---
**Microservicio: Turnos**:
+ **Crear Turno**:
  + **URL:** /turno/crear
  + **Método HTTP:** POST
  + **Parámetros:** Objeto **TurnoDTO** en el cuerpo de la petición (@RequestBody), que contiene la fecha, el tratamiento y el DNI del paciente.

+ **Traer Todos los Turnos**
  + **URL:** /turno/traer
  + **Método HTTP:** GET
  + **Parámetros:** Ninguno.

+ **Eliminar un Turno**
  + **URL:** /turno/borrar/{id}
  + **Método HTTP:** DELETE
  + **Parámetros:** @PathVariable Long id (identificador único del turno).

+ **Editar un Turno**
  + **URL:** /turno/editar/{id_original}
  + **Método HTTP:** PUT
  + **Parámetros:** @PathVariable Long id_original y un @RequestBody **Turno** turnoEditar con los nuevos datos.

+ **Obtener un Turno por ID**
  + **URL:** /turno/traer/{id}
  + **Método HTTP:** GET
  + **Parámetros:** @PathVariable Long id (identificador único del turno).
---
## 2-Estructura del Proyecto
Ambos microservicios siguen una arquitectura organizada en capas o tambien conosida como MVC (aunque vista no lo usamos en este caso):

  + **Controladores (controller):** Exponen los endpoints REST (@RestController), gestionan las peticiones HTTP entrantes (@GetMapping, @PostMapping, etc.) y delegan la lógica en la capa de servicios.

  + **Servicios (service):** Contienen la lógica de negocio de la aplicación. Gestionan las reglas de validación, procesamiento de datos y la interaccioncon lo externo (por ejemplo, el consumo de la API de pacientes desde el servicio de turnos).

  + **Repositorios (repository):** Interfaces que extienden de Spring a JPA para interactuar directamente con la base de datos (persistencia, consultas, guardado).

  + **Entidades / Modelos (model / dto):** Clases que representan las tablas de la base de datos (@Entity) y los objetos de transferencia de datos (DTO) utilizados para estructurar la información que viaja entre capas o servicios(de otra forma dicha el dto es una clase de seguridad para no traer todo los datos sino que lo necesario).
---
## 3-Independencia vs. Comunicación
Los microservicios no operan de forma completamente aislada; existe un punto de contacto y dependencia explícita entre ellos en tiempo de ejecución:

+ **Punto de contacto:** El microservicio de Turnos se comunica sincrónicamente con el microservicio de Pacientes.

+ **Mecanismo:** En el método saveTurno, el servicio de turnos utiliza un cliente HTTP (apiConsumir.getForObject) para traer el endpoint del otro servicio:
+ **extra:**(se lo dice de de forma explicita porque funcionario como si fuera un cast osea que trairia los dato de otro lado en tiempo de ejeccion y lo convertiria del tipo de objeto que es el paciente de esta forma permite usar sus metodos como pac.getNombre())
```java
Paciente pac = apiConsumir.getForObject("http://localhost:9001/pacientes/traerdni/"+dniPaciente, Paciente.class);
```
+ **Dependencia:** El microservicio de Turnos depende funcionalmente del microservicio de Pacientes (que corre en el puerto 9001) para obtener los datos personales del paciente (nombre y apellido) y así poder completar la creación y persistencia del turno de manera Correcta.

## 4-Conclusiones
La separación de un sistema monolítico en estos dos microservicios aporta las siguientes ventajas:

+ **Escalabilidad independiente:** Es posible escalar de manera aislada el servicio de turnos o el de pacientes según la demanda de tráfico que reciba cada módulo.

+ **Mantenibilidad y cohesión:** El código de pacientes y el de turnos se encuentran desacoplados en repositorios y proyectos distintos, lo que facilita que equipos de desarrollo trabajen en forma simultania sin generar conflictos en un único repositorio monolítico
