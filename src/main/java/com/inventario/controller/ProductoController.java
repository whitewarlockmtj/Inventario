package com.inventario.controller;

import com.inventario.model.Producto;
import com.inventario.model.Categoria;
import com.inventario.service.IProductoService;
import com.inventario.service.ICategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {
    
    private final IProductoService productoService;
    private final ICategoriaService categoriaService;
    
    @Autowired
    public ProductoController(IProductoService productoService, ICategoriaService categoriaService) {
        this.productoService = productoService;
        this.categoriaService = categoriaService;
    }
    
    @GetMapping
    public ResponseEntity<List<Producto>> obtenerTodosProductos() {
        List<Producto> productos = productoService.obtenerTodosProductos();
        return ResponseEntity.ok(productos);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable Long id) {
        Optional<Producto> producto = productoService.obtenerProductoPorId(id);
        return producto.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<Producto>> obtenerProductosPorCategoria(@PathVariable Long categoriaId) {
        if (!categoriaService.obtenerCategoriaPorId(categoriaId).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        
        List<Producto> productos = productoService.obtenerProductosPorCategoria(categoriaId);
        return ResponseEntity.ok(productos);
    }
    
    @GetMapping("/buscar")
    public ResponseEntity<List<Producto>> buscarProductos(@RequestParam(required = false) String nombre) {
        if (nombre != null && !nombre.isEmpty()) {
            return ResponseEntity.ok(productoService.buscarProductosPorNombre(nombre));
        }
        return ResponseEntity.ok(productoService.obtenerTodosProductos());
    }

    @GetMapping("/buscarSku")
    public ResponseEntity<List<Producto>> buscarProductosPorCodigoSku(@RequestParam(required = false) String codigoSku) {
        if (codigoSku != null && !codigoSku.isEmpty()) {
            return ResponseEntity.ok(productoService.buscarProductoPorCodigoSku(codigoSku));
        }
        return ResponseEntity.ok(productoService.obtenerTodosProductos());
    }

    @PostMapping
    public ResponseEntity<Producto> crearProducto(@RequestBody Producto producto) {
        // Verificar si la categoría existe
        Optional<Categoria> categoriaOpt = categoriaService.obtenerCategoriaPorId(producto.getCategoria().getId());
        if (!categoriaOpt.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        
        // Asociar la categoría existente al producto
        producto.setCategoria(categoriaOpt.get());
        
        Producto nuevoProducto = productoService.guardarProducto(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @RequestBody Producto producto) {
        Optional<Producto> productoExistente = productoService.obtenerProductoPorId(id);
        
        if (productoExistente.isPresent()) {
            // Verificar si la categoría existe
            Optional<Categoria> categoriaOpt = categoriaService.obtenerCategoriaPorId(producto.getCategoria().getId());
            if (!categoriaOpt.isPresent()) {
                return ResponseEntity.badRequest().build();
            }
            
            Producto productoActualizar = productoExistente.get();
            productoActualizar.setNombre(producto.getNombre());
            productoActualizar.setDescripcion(producto.getDescripcion());
            productoActualizar.setCategoria(categoriaOpt.get());
            productoActualizar.setCodigoSku(producto.getCodigoSku());
            
            return ResponseEntity.ok(productoService.guardarProducto(productoActualizar));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        boolean eliminado = productoService.eliminarProducto(id);
        return eliminado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}