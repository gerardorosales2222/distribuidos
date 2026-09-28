package com.distribuidos.producto.service;

import com.distribuidos.producto.model.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.distribuidos.producto.repository.IProductoRepository;

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
