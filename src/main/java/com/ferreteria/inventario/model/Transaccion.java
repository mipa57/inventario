package com.ferreteria.inventario.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Document(collection = "transacciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaccion {
    @Id
    private String id;
    private LocalDate fecha;
    private String tipo; // "venta" o "compra"
    private double monto;
    private String detalle;
}
