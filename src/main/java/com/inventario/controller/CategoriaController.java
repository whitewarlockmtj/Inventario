package com.inventario.controller;

import com.inventario.model.Categoria;
import com.inventario.service.ICategoriaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final ICategoriaService categoriaService;

    public CategoriaController(ICategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public ResponseEntity<List<Categoria>> obtenerTodasCategorias() {
        List<Categoria> categorias = categoriaService.obtenerTodasCategorias();
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> obtenerCategoriaPorId(@PathVariable Long id) {
        Optional<Categoria> categoria = categoriaService.obtenerCategoriaPorId(id);
        return categoria.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Categoria> crearCategoria(@RequestBody Categoria categoria) {
        if (categoriaService.existePorNombre(categoria.getNombre())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        Categoria nuevaCategoria = categoriaService.guardarCategoria(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCategoria);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categoria> actualizarCategoria(@PathVariable Long id, @RequestBody Categoria categoria) {
        Optional<Categoria> categoriaExistente = categoriaService.obtenerCategoriaPorId(id);

        if (categoriaExistente.isPresent()) {
            Categoria categoriaActualizar = categoriaExistente.get();

            // Verificar si el nombre ya existe y es diferente al actual
            if (!categoriaActualizar.getNombre().equals(categoria.getNombre()) &&
                categoriaService.existePorNombre(categoria.getNombre())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }

            categoriaActualizar.setNombre(categoria.getNombre());
            categoriaActualizar.setDescripcion(categoria.getDescripcion());

            return ResponseEntity.ok(categoriaService.guardarCategoria(categoriaActualizar));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable Long id) {
        boolean eliminado = categoriaService.eliminarCategoria(id);
        return eliminado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
