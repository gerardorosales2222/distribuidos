# ☁️ Introducción a Spring Cloud y OpenFeign

Apuntes de clase sobre los fundamentos de arquitecturas de microservicios con el ecosistema Spring y la comunicación declarativa entre servicios.

---

## 1. ¿Por qué necesitamos Spring Cloud?

Cuando desarrollamos con **Spring Boot**, creamos aplicaciones individuales robustas. Sin embargo, al dar el salto hacia una arquitectura de **microservicios** (por ejemplo: *Usuarios*, *Productos*, *Pedidos*), surgen desafíos propios de los sistemas distribuidos que Spring Boot por sí solo no resuelve:

* **Descubrimiento de servicios:** ¿Cómo sabe el servicio de *Pedidos* en qué IP y puerto está corriendo el de *Usuarios* si en la nube son dinámicos?
* **Configuración centralizada:** ¿Cómo evitamos editar decenas de archivos `application.yml` cada vez que cambia una credencial o una URL?
* **Tolerancia a fallos:** ¿Cómo evitamos que la caída de un microservicio secundario provoque un efecto dominó que tumbe todo el sistema?
* **Enrutamiento:** ¿Cómo ofrecemos un punto de entrada único hacia el exterior?

### ¿Qué es Spring Cloud?
> **Definición:** Es un conjunto de herramientas y librerías construidas sobre Spring Boot que actúan como una capa de coordinación para sistemas distribuidos. 

Mientras **Spring Boot** se encarga de *construir* cada microservicio individual, **Spring Cloud** se encarga de *conectarlos, organizarlos y gestionarlos* como un todo.

---

## 2. Componentes Principales del Ecosistema

| Componente | Herramienta habitual | Función principal |
| :--- | :--- | :--- |
| **Config Server** | Spring Cloud Config | Centraliza las propiedades de todos los servicios en un repositorio (ej. Git). |
| **Service Discovery** | Netflix Eureka | Actúa como una "guía telefónica" donde los servicios se registran y se encuentran por nombre. |
| **API Gateway** | Spring Cloud Gateway | Punto de entrada único. Enruta peticiones, aplica seguridad y filtra tráfico. |
| **Circuit Breaker** | Resilience4j | Protege el sistema aislando servicios que están fallando y ofreciendo respuestas alternativas (*fallbacks*). |
| **Cliente HTTP** | **OpenFeign** | Permite realizar llamadas REST entre microservicios de forma declarativa y sencilla. |

---

## 3. Comunicación entre Microservicios con OpenFeign

Tradicionalmente, para consumir una API REST desde Java se utilizaba `RestTemplate` o `WebClient`, lo que obligaba a escribir mucho código repetitivo (*boilerplate*) para armar URLs y mapear respuestas.

**Spring Cloud OpenFeign** soluciona esto mediante **clientes HTTP declarativos**:
1. Solo defines una **interfaz** de Java con anotaciones estándar de Spring MVC.
2. Spring genera la implementación real en tiempo de ejecución.
3. Se integra automáticamente con **Eureka** (para resolver el nombre del servicio) y con **LoadBalancer** (para repartir la carga si hay varias instancias).

---

## 4. Ejemplo Práctico: `order-service` llamando a `customer-service`

Escenario: El microservicio de **Pedidos** necesita obtener los datos de un cliente desde el microservicio de **Clientes** usando su ID.

### Paso 1: Agregar la dependencia (`pom.xml`)
En el microservicio consumidor (`order-service`), añadimos el *starter* de OpenFeign:

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```

### Paso 2: Habilitar Feign en la aplicación
Añadimos `@EnableFeignClients` en la clase principal de Spring Boot:

```java
package com.example.orderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients // Habilita el escaneo de interfaces anotadas con @FeignClient
public class OrderServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }
}
```

### Paso 3: Crear el DTO de respuesta
Definimos una clase simple para recibir los datos del JSON que devolverá el servicio remoto:

```java
package com.example.orderservice.dto;

public class CustomerDTO {
    private Long id;
    private String name;
    private String email;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
```

### Paso 4: Definir la interfaz `@FeignClient`
Creamos la interfaz que representa al microservicio remoto. El atributo `name` debe coincidir con el `spring.application.name` registrado en Eureka (opcionalmente se puede usar `url` si no se usa un servidor de descubrimiento).

```java
package com.example.orderservice.client;

import com.example.orderservice.dto.CustomerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customer-service")
public interface CustomerClient {

    // La firma del método debe coincidir con el endpoint expuesto en customer-service
    @GetMapping("/api/customers/{id}")
    CustomerDTO getCustomerById(@PathVariable("id") Long id);
}
```
> 💡 **Nota clave:** No necesitas crear una clase que implemente `CustomerClient`. Spring se encarga de todo por debajo.

### Paso 5: Inyectar y utilizar el cliente
Inyectamos `CustomerClient` en nuestra capa de servicio como cualquier otro *Bean* de Spring:

```java
package com.example.orderservice.service;

import com.example.orderservice.client.CustomerClient;
import com.example.orderservice.dto.CustomerDTO;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final CustomerClient customerClient;

    // Inyección por constructor (buena práctica)
    public OrderService(CustomerClient customerClient) {
        this.customerClient = customerClient;
    }

    public void createOrder(Long customerId) {
        // Se invoca como un método local, pero ejecuta una petición HTTP GET por detrás
        CustomerDTO customer = customerClient.getCustomerById(customerId);
        
        System.out.println("Creando pedido para: " + customer.getName());
    }
}
```

---

## 📝 Resumen para recordar

* **Spring Boot** = Crea el microservicio.
* **Spring Cloud** = Orquesta y conecta los microservicios.
* **OpenFeign** = Convierte llamadas HTTP remotas en simples llamadas a métodos de una interfaz Java.