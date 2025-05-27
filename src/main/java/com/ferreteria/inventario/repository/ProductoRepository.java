package com.ferreteria.inventario.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ferreteria.inventario.model.Producto;

public interface ProductoRepository extends MongoRepository<Producto, String> {
    Optional<Producto> findByNombre(String nombre);
}
