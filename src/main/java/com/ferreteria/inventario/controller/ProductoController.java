package com.ferreteria.inventario.controller;

import com.ferreteria.inventario.model.Producto;
import com.ferreteria.inventario.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")  // permite acceso desde frontend
public class ProductoController {

    @Autowired
    private ProductoRepository repository;

    // ✅ Agregar producto
    @PostMapping
    public ResponseEntity<?> agregarProducto(@RequestBody Producto producto) {
        Optional<Producto> existente = repository.findByCodigo(producto.getCodigo());

        if (existente.isPresent()) {
            return ResponseEntity.badRequest().body("⚠️ Error: ya existe un producto con ese código.");
        }

        repository.save(producto);
        return ResponseEntity.ok("✅ Producto agregado correctamente.");
    }

    // ✅ Ver todos los productos
    @GetMapping
    public List<Producto> obtenerTodos() {
        return repository.findAll();
    }

    // ✅ Actualizar producto por ID
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable String id, @RequestBody Producto productoNuevo) {
        Optional<Producto> existente = repository.findById(id);
        if (!existente.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Producto prod = existente.get();
        prod.setNombre(productoNuevo.getNombre());
        prod.setCantidad(productoNuevo.getCantidad());
        prod.setPrecioUnitario(productoNuevo.getPrecioUnitario());

        repository.save(prod);
        return ResponseEntity.ok("🔄 Producto actualizado correctamente.");
    }

    // ✅ Eliminar producto por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable String id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        repository.deleteById(id);
        return ResponseEntity.ok("🗑️ Producto eliminado correctamente.");
    }
}
