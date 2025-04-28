package com.inventario.service;

import com.inventario.model.Stock;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface IStockService {
    List<Stock> obtenerTodosStocks();
    Optional<Stock> obtenerStockPorId(Long id);
    List<Stock> buscarStocksPorRangoPrecio(BigDecimal min, BigDecimal max);
    Stock guardarStock(Stock stock);
    boolean eliminarStock(Long id);
    boolean actualizarCantidad(Long id, Integer nuevaCantidad);
}
