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
