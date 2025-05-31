package com.ferreteria.inventario.model; // Asegúrate de que el paquete sea correcto

// ************************************************************
// CAMBIO CLAVE: Importaciones de Jakarta Bean Validation
// ************************************************************
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data; // Si usas Lombok, asegúrate de que esté importado

@Data // Anotación de Lombok para getters, setters, equals, hashCode, toString
public class Producto {

    private String id; // ID de MongoDB, puede ser String o ObjectId

    @NotBlank(message = "El código no puede estar vacío")
    private String codigo;

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @Min(value = 0, message = "La cantidad no puede ser negativa")
    private int cantidad;

    @DecimalMin(value = "0.0", inclusive = true, message = "El precio unitario no puede ser negativo")
    private double precioUnitario;

    // Puedes añadir constructores si los necesitas, Lombok @Data los genera por defecto
    // public Producto() {}
    // public Producto(String codigo, String nombre, int cantidad, double precioUnitario) {
    //     this.codigo = codigo;
    //     this.nombre = nombre;
    //     this.cantidad = cantidad;
    //     this.precioUnitario = precioUnitario;
    // }
}