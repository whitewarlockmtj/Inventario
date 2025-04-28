package com.inventario.repository;

import com.inventario.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    List<Stock> findByPrecioBetween(BigDecimal min, BigDecimal max);
}