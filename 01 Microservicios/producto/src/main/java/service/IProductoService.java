package service;

import model.Producto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IProductoService {

    public List<Producto> getProductos();

    public void saveProducto(Producto prod);

}
