package com.inventario.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "productos")
public class Producto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nombre;
    
    private String descripcion;
    
    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    private String codigoSku;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
    private List<Stock> stocks = new ArrayList<>();

    // Constructores
    public Producto() {
    }
    
    public Producto(String nombre, String descripcion, Categoria categoria, String codigoSku) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.codigoSku = codigoSku;
    }
    
    // Getters y setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public Categoria getCategoria() {
        return categoria;
    }
    
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    
    public String getCodigoSku() {
        return codigoSku;
    }
    
    public void setCodigoSku(String codigoSku) {
        this.codigoSku = codigoSku;
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }
}