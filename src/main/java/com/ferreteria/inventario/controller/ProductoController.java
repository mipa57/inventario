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
    public ResponseEntity<String> agregarProducto(@RequestBody Producto producto) {
    Optional<Producto> existente = repositorio.findByNombre(producto.getNombre());

    if (existente.isPresent()) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("⚠️ El producto ya existe.");
    }

    repositorio.save(producto);
    return ResponseEntity.status(HttpStatus.CREATED)
            .body("✅ Producto agregado correctamente.");
}


    // ✅ Ver todos los productos
    @GetMapping
    public List<Producto> obtenerTodos() {
        return repository.findAll();
    }

    
    // ✅ Actualizar producto por ID
    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarProducto(@PathVariable String id, @RequestBody Producto nuevoProducto) {
    Optional<Producto> productoOptional = repositorio.findById(id);

    if (productoOptional.isPresent()) {
        Producto producto = productoOptional.get();
        producto.setNombre(nuevoProducto.getNombre());
        producto.setCantidad(nuevoProducto.getCantidad());
        producto.setPrecioUnitario(nuevoProducto.getPrecioUnitario());
        repositorio.save(producto);
        return ResponseEntity.ok("🔄 Producto actualizado correctamente.");
    }

    return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body("❌ Producto no encontrado.");
}


    // ✅ Eliminar producto por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarProducto(@PathVariable String id) {
    if (repositorio.existsById(id)) {
        repositorio.deleteById(id);
        return ResponseEntity.ok("🗑️ Producto eliminado correctamente.");
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body("❌ No se encontró el producto a eliminar.");
}

}