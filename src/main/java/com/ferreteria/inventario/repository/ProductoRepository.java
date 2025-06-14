package com.ferreteria.inventario.repository;

import com.ferreteria.inventario.model.Producto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends MongoRepository<Producto, String> {
    // Puedes añadir métodos personalizados aquí si los necesitas,
    // por ejemplo: Producto findByCodigo(String codigo);
}
