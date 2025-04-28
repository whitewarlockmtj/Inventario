package com.inventario.service.impl;

import com.inventario.model.Stock;
import com.inventario.repository.StockRepository;
import com.inventario.service.IStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class StockService implements IStockService {

    private final StockRepository stockRepository;

    @Autowired
    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }
    
    public List<Stock> obtenerTodosStocks() {
        return stockRepository.findAll();
    }
    
    public Optional<Stock> obtenerStockPorId(Long id) {
        return stockRepository.findById(id);
    }
    
    public List<Stock> buscarStocksPorRangoPrecio(BigDecimal min, BigDecimal max) {
        return stockRepository.findByPrecioBetween(min, max);
    }
    
    @Transactional
    public Stock guardarStock(Stock stock) {
        return stockRepository.save(stock);
    }
    
    @Transactional
    public boolean eliminarStock(Long id) {
        if (stockRepository.existsById(id)) {
            stockRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    @Transactional
    public boolean actualizarCantidad(Long id, Integer nuevaCantidad) {
        Optional<Stock> stockOpt = stockRepository.findById(id);
        if (stockOpt.isPresent()) {
            Stock stock = stockOpt.get();
            stock.setCantidad(nuevaCantidad);
            stockRepository.save(stock);
            return true;
        }
        return false;
    }
}