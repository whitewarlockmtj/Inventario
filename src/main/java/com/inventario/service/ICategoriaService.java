package com.inventario.service;

import com.inventario.model.Categoria;

import java.util.List;
import java.util.Optional;

public interface ICategoriaService {
    List<Categoria> obtenerTodasCategorias();
    Optional<Categoria> obtenerCategoriaPorId(Long id);
    Optional<Categoria> obtenerCategoriaPorNombre(String nombre);
    Categoria guardarCategoria(Categoria categoria);
    boolean eliminarCategoria(Long id);
    boolean existePorNombre(String nombre);
}
