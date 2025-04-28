package com.inventario.service.impl;

import com.inventario.model.Categoria;
import com.inventario.repository.CategoriaRepository;
import com.inventario.service.ICategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService implements ICategoriaService {
    
    private final CategoriaRepository categoriaRepository;
    
    @Autowired
    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }
    
    public List<Categoria> obtenerTodasCategorias() {
        return categoriaRepository.findAll();
    }
    
    public Optional<Categoria> obtenerCategoriaPorId(Long id) {
        return categoriaRepository.findById(id);
    }
    
    public Optional<Categoria> obtenerCategoriaPorNombre(String nombre) {
        return categoriaRepository.findByNombre(nombre);
    }
    
    @Transactional
    public Categoria guardarCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }
    
    @Transactional
    public boolean eliminarCategoria(Long id) {
        if (categoriaRepository.existsById(id)) {
            categoriaRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    public boolean existePorNombre(String nombre) {
        return categoriaRepository.existsByNombre(nombre);
    }
}