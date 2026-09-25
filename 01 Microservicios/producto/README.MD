```java

package model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private float precio;
    private int stock;

    public Producto() {
    }

    public Producto(Long id, String nombre, float precio, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }
}

--------------------------------------------------------------------------------




package repository;

import model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductoRepository extends JpaRepository<Producto, Long> {
}

--------------------------------------------------------------------------------



import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IProductoService {

    public List<Producto> getProductos();
    
    public void saveProducto(Producto prod);

}

--------------------------------------------------------------------------------



package service;

import model.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.IProductoRepository;

import java.util.List;

@Service
public class ProductoService implements IProductoService{

    @Autowired
    private IProductoRepository repoProducto;

    @Override
    public List<Producto> getProductos() {
        return repoProducto.findAll();
    }

    @Override
    public void saveProducto(Producto prod) {
        repoProducto.save(prod);
    }
}

--------------------------------------------------------------------------------

package controller;

import model.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import service.IProductoService;

import java.util.List;

@RestController
public class ProductoController {

    @Autowired
    private IProductoService serviceProd;

    @PostMapping("/productos/crear")
    public String crearProducto(@RequestBody Producto prod){
        serviceProd.saveProducto(prod);
        return "Dado de alta OK";
    }

    @GetMapping("/productos/listar")
    public List<Producto> getProducto(){
        return serviceProd.getProductos();
    }

}

```