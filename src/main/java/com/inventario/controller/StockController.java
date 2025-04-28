package com.inventario.controller;

import com.inventario.model.Producto;
import com.inventario.model.Stock;
import com.inventario.service.IProductoService;
import com.inventario.service.IStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/stocks")
public class StockController {

    private final IStockService stockService;
    private final IProductoService productoService;

    @Autowired
    public StockController(IStockService stockService, IProductoService productoService) {
        this.stockService = stockService;
        this.productoService = productoService;
    }
    
    @GetMapping
    public ResponseEntity<List<Stock>> obtenerTodosStocks() {
        List<Stock> stocks = stockService.obtenerTodosStocks();
        return ResponseEntity.ok(stocks);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Stock> obtenerStockPorId(@PathVariable Long id) {
        Optional<Stock> stock = stockService.obtenerStockPorId(id);
        return stock.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/precio")
    public ResponseEntity<List<Stock>> buscarPorRangoPrecio(
            @RequestParam BigDecimal min, 
            @RequestParam BigDecimal max) {
        return ResponseEntity.ok(stockService.buscarStocksPorRangoPrecio(min, max));
    }
    
    @PostMapping
    public ResponseEntity<Stock> crearStock(@RequestBody Stock stock) {
        // Verificar si la categoría existe
        Optional<Producto> productoOpt = productoService.obtenerProductoPorId(stock.getProducto().getId());
        if (!productoOpt.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        
        // Asociar la categoría existente al producto
        stock.setProducto(productoOpt.get());
        
        Stock nuevoStock = stockService.guardarStock(stock);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoStock);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Stock> actualizarStock(@PathVariable Long id, @RequestBody Stock stock) {
        Optional<Stock> stockExistente = stockService.obtenerStockPorId(id);
        
        if (stockExistente.isPresent()) {
            Stock stockActualizar = stockExistente.get();
            stockActualizar.setPrecio(stock.getPrecio());
            stockActualizar.setCantidad(stock.getCantidad());

            return ResponseEntity.ok(stockService.guardarStock(stockActualizar));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PatchMapping("/{id}/cantidad")
    public ResponseEntity<Void> actualizarCantidad(@PathVariable Long id, @RequestParam Integer cantidad) {
        boolean actualizado = stockService.actualizarCantidad(id, cantidad);
        return actualizado ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarStock(@PathVariable Long id) {
        boolean eliminado = stockService.eliminarStock(id);
        return eliminado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}