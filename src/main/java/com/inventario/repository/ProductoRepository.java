package com.inventario.repository;

import com.inventario.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByCategoriaId(Long categoriaId);
    List<Producto> findByNombreContaining(String nombre);
    Optional<Producto> findByCodigoSku(String codigoSku);
}