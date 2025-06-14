package com.ferreteria.inventario.repository;

import com.ferreteria.inventario.model.Transaccion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransaccionRepository extends MongoRepository<Transaccion, String> {
    // Puedes añadir métodos para buscar por fecha, tipo, etc.
    // List<Transaccion> findByFechaBetween(LocalDate startDate, LocalDate endDate);
}
