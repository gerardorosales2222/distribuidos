# Arquitectura de Software: Monolitos vs. Microservicios

Este documento proporciona una guía explicativa e introductoria sobre los patrones de arquitectura de software **Monolítica** y de **Microservicios**, detallando sus conceptos fundamentales, características principales, ventajas, desventajas, criterios de elección y un caso práctico orientado al ámbito médico.

---

## Tabla de Contenidos
1. [Introducción](#introducción)
2. [Arquitectura Monolítica](#arquitectura-monolítica)
3. [Arquitectura de Microservicios](#arquitectura-de-microservicios)
4. [Características Clave de los Microservicios](#características-clave-de-los-microservicios)
5. [Comparativa: Ventajas y Desventajas](#comparativa-ventajas-y-desventajas)
6. [¿Cuál Opción Elegir?](#cuál-opción-elegir)
7. [Caso Práctico: Sistema para Consultorio Médico](#caso-práctico-sistema-para-consultorio-médico)

---

## Introducción

La elección de la arquitectura de software es uno de los pilares más críticos al diseñar una aplicación. Determina cómo se estructuran los módulos de código, cómo interactúan las capas de datos y de qué forma se despliega, escala y mantiene el sistema a lo largo de su ciclo de vida.

---

## Arquitectura Monolítica

En un **monolito**, toda la aplicación se empaqueta y ejecuta como una **única unidad monolítica**. Esto significa que la interfaz de usuario, la lógica de negocio y la capa de acceso a datos comparten la misma base de código y se despliegan en un solo archivo ejecutable o artefacto.

### Estructura Típica
```
+-----------------------------------+
|    Interfaz Gráfica (Frontend)    |
+-----------------------------------+
                  |
+-----------------------------------+
|     Lógica de Negocio (Backend)   |
+-----------------------------------+
                  |
+-----------------------------------+
|    Persistencia / Base de Datos   |
+-----------------------------------+
```

### Principales Desafíos
* **Efecto dominó:** Una falla puntual en un módulo puede causar la caída completa del sistema.
* **Cuellos de botella:** Al crecer el código, el proyecto se vuelve más complejo de depurar, probar y mantener.
* **Despliegue monolítico:** Cualquier actualización (por pequeña que sea) exige rebalancear y redesplegar toda la aplicación.

---

## Arquitectura de Microservicios

En una arquitectura de **microservicios**, la aplicación se divide en un conjunto de **servicios pequeños, autónomos e independientes**. Cada servicio se encarga de un dominio funcional específico, posee su propio esquema de base de datos y se comunica con los demás a través de interfaces bien definidas (como APIs REST o protocolos de mensajería).

### Estructura Típica
```
                     +-------------------+
                     |   API Gateway /   |
                     |  Microservicio A  |
                     +---------+---------+
                               |
        +----------------------+----------------------+
        |                      |                      |
+-------v-------+      +-------v-------+      +-------v-------+
|  Servicio 1   |      |  Servicio 2   |      |  Servicio 3   |
|  (Productos)  |      |   (Ventas)    |      |  (Clientes)   |
+-------+-------+      +-------+-------+      +-------+-------+
        |                      |                      |
+-------v-------+      +-------v-------+      +-------v-------+
|  Base Datos 1 |      |  Base Datos 2 |      |  Base Datos 3 |
+---------------+      +---------------+      +---------------+
```

---

## Características Clave de los Microservicios

1. **Autonomía e Independencia:** Cada microservicio ejecuta su propia lógica de negocio y gestiona su propia persistencia de datos.
2. **Comunicación por API:** Interacción estandarizada mediante interfaces (por ejemplo, endpoints REST / JSON).
3. **Escalabilidad Individual:** Permite escalar únicamente los componentes de alta demanda sin consumir recursos innecesarios en el resto del sistema.
4. **Despliegues Independientes:** Actualizaciones y entregas continuas sin interferir en los servicios en ejecución.
5. **Enfoque en Dominio Específico:** Facilita la resolución de problemas focalizados al aislar las responsabilidades.
6. **Agnosticismo Tecnológico:** Cada servicio puede construirse utilizando el lenguaje, framework o tipo de base de datos que mejor se adapte a sus necesidades.

---

## Comparativa: Ventajas y Desventajas

| Criterio | Arquitectura Monolítica | Arquitectura de Microservicios |
| :--- | :--- | :--- |
| **Puesta en marcha** | **Ventaja:** Más simple de desarrollar, probar e integrar inicialmente. | **Desventaja:** Mayor complejidad inicial en infraestructura y comunicación. |
| **Escalabilidad** | **Desventaja:** Requiere duplicar todo el monolito en paralelo. | **Ventaja:** Gran flexibilidad; se escalan únicamente los servicios necesarios. |
| **Despliegues** | **Desventaja:** Cambios pequeños requieren compilar y redesplegar todo el proyecto. | **Ventaja:** Permite entregas ágiles e independientes por módulo. |
| **Tolerancia a fallos** | **Desventaja:** Un error crítico puede detener todo el sistema. | **Ventaja:** Aislamiento de fallos; si un servicio cae, los demás siguen funcionando. |
| **Mantenimiento** | Crecimiento complejo si el código no se mantiene estrictamente modular. | Alta modularidad, pero exige mayor gestión de redes, logs y monitoreo distribuido. |
| **Costos** | Infraestructura inicial más accesible. | Puede requerir mayores recursos y costos operativos en la nube. |

---

## ¿Cuál Opción Elegir?

* **Elegir Monolito cuando:**
  * Se desarrolla un prototipo, MVP o aplicación de escala pequeña/mediana.
  * El equipo de desarrollo es reducido.
  * Se busca rapidez en el lanzamiento sin complejidad operativa adicional.

* **Elegir Microservicios cuando:**
  * Se proyecta un crecimiento acelerado y requerimientos de alta disponibilidad.
  * Múltiples equipos autónomos trabajarán en paralelo en distintas funcionalidades.
  * Se necesita flexibilidad tecnológica y escalabilidad horizontal selectiva.

---

## Caso Práctico: Sistema para Consultorio Médico

Como ejercicio aplicativo, consideremos un sistema de gestión para un **consultorio médico** enfocado en administrar la interacción entre pacientes y sus turnos asociados.

```
                  +--------------------------------+
                  |  Microservicio de Pacientes    |
                  |  - Operaciones CRUD            |
                  |  - Datos personales e historia |
                  +---------------+----------------+
                                  |
                                  | Consumo /
                                  | Interconexión REST
                                  v
                  +--------------------------------+
                  |  Microservicio de Turnos       |
                  |  - Alta y asignación de citas  |
                  |  - Validación de agendas       |
                  +--------------------------------+
```
