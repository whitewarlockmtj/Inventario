package com.inventario.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "stocks")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal precio;

    private Integer cantidad;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    // Constructores
    public Stock() {
    }

    public Stock(BigDecimal precio, Integer cantidad, Producto producto) {
        this.precio = precio;
        this.cantidad = cantidad;
        this.producto = producto;
    }
    
    // Getters y setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public BigDecimal getPrecio() {
        return precio;
    }
    
    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }
    
    public Integer getCantidad() {
        return cantidad;
    }
    
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
    
    public Producto getProducto() {
        return producto;
    }
    
    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    
}