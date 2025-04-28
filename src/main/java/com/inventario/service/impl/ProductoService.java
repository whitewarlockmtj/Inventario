package com.inventario.service.impl;

import com.inventario.model.Producto;
import com.inventario.repository.ProductoRepository;
import com.inventario.service.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService implements IProductoService {
    
    private final ProductoRepository productoRepository;
    
    @Autowired
    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }
    
    public List<Producto> obtenerTodosProductos() {
        return productoRepository.findAll();
    }
    
    public Optional<Producto> obtenerProductoPorId(Long id) {
        return productoRepository.findById(id);
    }
    
    public List<Producto> obtenerProductosPorCategoria(Long categoriaId) {
        return productoRepository.findByCategoriaId(categoriaId);
    }
    
    public List<Producto> buscarProductosPorNombre(String nombre) {
        return productoRepository.findByNombreContaining(nombre);
    }
    
    public Optional<Producto> buscarProductoPorCodigoSku(String codigoSku) {
        return productoRepository.findByCodigoSku(codigoSku);
    }
    
    @Transactional
    public Producto guardarProducto(Producto producto) {
        return productoRepository.save(producto);
    }
    
    @Transactional
    public boolean eliminarProducto(Long id) {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}