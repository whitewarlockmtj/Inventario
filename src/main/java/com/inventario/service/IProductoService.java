package com.inventario.service;

import com.inventario.model.Producto;

import java.util.List;
import java.util.Optional;

public interface IProductoService {
    List<Producto> obtenerTodosProductos();
    Optional<Producto> obtenerProductoPorId(Long id);
    List<Producto> obtenerProductosPorCategoria(Long categoriaId);
    List<Producto> buscarProductosPorNombre(String nombre);
    Optional<Producto> buscarProductoPorCodigoSku(String codigoSku);
    Producto guardarProducto(Producto producto);
    boolean eliminarProducto(Long id);
}
