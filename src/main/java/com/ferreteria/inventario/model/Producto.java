package com.ferreteria.inventario.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.*;


@Document(collection = "productos")
public class Producto {

    @Id
    private String id;
    @NotBlank(message = "El código no puede estar vacío")
    private String codigo;
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;
    @Min(value = 1, message = "La cantidad debe ser mayor que 0")
    private int cantidad;
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor que 0")
    private double precioUnitario;

    public Producto() {}

    public Producto(String codigo, String nombre, int cantidad, double precioUnitario) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    // Getters y Setters
    public String getId() { return id; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(double precioUnitario) { this.precioUnitario = precioUnitario; }
}