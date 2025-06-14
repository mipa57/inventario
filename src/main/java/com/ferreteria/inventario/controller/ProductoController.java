package com.ferreteria.inventario.controller;

import com.ferreteria.inventario.model.Producto;
import com.ferreteria.inventario.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos")
// @CrossOrigin(origins = "http://localhost:8080") // Descomentar en desarrollo si el frontend está en otro puerto
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    // GET: Obtener todos los productos
    @GetMapping
    public List<Producto> getAllProductos() {
        return productoRepository.findAll(); //
    }

    // GET: Obtener un producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProductoById(@PathVariable String id) {
        Optional<Producto> producto = productoRepository.findById(id);
        return producto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST: Crear un nuevo producto
    @PostMapping
    public ResponseEntity<Producto> createProducto(@RequestBody Producto producto) {
        // Aquí podrías añadir validaciones adicionales antes de guardar
        Producto savedProducto = productoRepository.save(producto); //
        return new ResponseEntity<>(savedProducto, HttpStatus.CREATED);
    }

    // PUT: Actualizar un producto existente
    @PutMapping("/{id}")
    public ResponseEntity<Producto> updateProducto(@PathVariable String id, @RequestBody Producto producto) {
        return productoRepository.findById(id)
                .map(existingProduct -> {
                    existingProduct.setCodigo(producto.getCodigo());
                    existingProduct.setNombre(producto.getNombre());
                    existingProduct.setCantidad(producto.getCantidad()); //
                    existingProduct.setPrecio_unitario(producto.getPrecio_unitario()); //
                    return ResponseEntity.ok(productoRepository.save(existingProduct));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE: Eliminar un producto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable String id) {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id); //
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // TODO: Implementar lógica para alertas de bajo stock
    // Por ejemplo, un endpoint GET /api/productos/bajo-stock
}
