package com.ferreteria.inventario.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data; // Para @Data, si usas Lombok
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Document(collection = "productos") // Define la colección en MongoDB
@Data // Genera getters, setters, toString, equals, hashCode
@NoArgsConstructor // Genera constructor sin argumentos
@AllArgsConstructor // Genera constructor con todos los argumentos
public class Producto {
    @Id // Marca el campo como el ID principal del documento en MongoDB
    private String id;
    private String codigo;
    private String nombre;
    private int cantidad;
    private double precio_unitario;
}
