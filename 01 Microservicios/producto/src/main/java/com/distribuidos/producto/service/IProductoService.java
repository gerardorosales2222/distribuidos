package com.distribuidos.producto.service;

import com.distribuidos.producto.model.Producto;

import java.util.List;

public interface IProductoService {

    public List<Producto> getProductos();

    public void saveProducto(Producto prod);

}
