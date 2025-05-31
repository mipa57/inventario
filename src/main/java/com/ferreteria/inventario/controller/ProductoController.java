package com.ferreteria.inventario.controller; // Asegúrate de que el paquete sea correcto

import com.ferreteria.inventario.model.Producto; // Asegúrate de que el paquete sea correcto
import com.ferreteria.inventario.repository.ProductoRepository; // Asegúrate de que el paquete sea correcto
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; // Importa esta anotación
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos")
// ************************************************************
// CAMBIO CLAVE: Protege todos los métodos de este controlador
// ************************************************************
@PreAuthorize("isAuthenticated()") // Requiere que el usuario esté autenticado para acceder a cualquier método aquí
public class ProductoController {

    private final ProductoRepository productoRepository;

    public ProductoController(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @GetMapping
    public List<Producto> getAllProductos() {
        return productoRepository.findAll();
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Producto> getProductoByCodigo(@PathVariable String codigo) {
        Optional<Producto> producto = productoRepository.findByCodigo(codigo);
        return producto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Producto> createProducto(@RequestBody Producto producto) {
        // Validación de código duplicado a nivel de controlador (opcional si ya lo haces en frontend)
        if (productoRepository.findByCodigo(producto.getCodigo()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null); // Código 409 Conflict
        }
        Producto savedProducto = productoRepository.save(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProducto);
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<Producto> updateProducto(@PathVariable String codigo, @RequestBody Producto productoDetails) {
        Optional<Producto> producto = productoRepository.findByCodigo(codigo);
        if (producto.isPresent()) {
            Producto existingProducto = producto.get();
            existingProducto.setNombre(productoDetails.getNombre());
            existingProducto.setCantidad(productoDetails.getCantidad());
            existingProducto.setPrecioUnitario(productoDetails.getPrecioUnitario());
            Producto updatedProducto = productoRepository.save(existingProducto);
            return ResponseEntity.ok(updatedProducto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> deleteProducto(@PathVariable String codigo) {
        Optional<Producto> producto = productoRepository.findByCodigo(codigo);
        if (producto.isPresent()) {
            productoRepository.delete(producto.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/bajo-stock")
    public List<Producto> getProductosBajoStock() {
        // Asumiendo que 'cantidad' es el campo para el stock y 5 es el umbral
        return productoRepository.findByCantidadLessThanEqual(5);
    }
}
