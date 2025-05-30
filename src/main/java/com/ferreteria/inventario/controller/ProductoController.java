package com.ferreteria.inventario.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.ferreteria.inventario.model.Producto;
import com.ferreteria.inventario.repository.ProductoRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductoController {

    @Autowired
    private ProductoRepository repositorio;

    // GET: listar todos
    @GetMapping
    public List<Producto> obtenerTodos() {
        return repositorio.findAll();
    }

    // GET: obtener por código (opcional para editar)
    @GetMapping("/{codigo}")
    public ResponseEntity<?> obtenerPorCodigo(@PathVariable String codigo) {
        Optional<Producto> producto = repositorio.findByCodigo(codigo);
        return producto.isPresent() ? ResponseEntity.ok(producto.get()) : ResponseEntity.notFound().build();
    }

    // POST: crear producto
    @PostMapping
    public ResponseEntity<?> crearProducto(@Valid @RequestBody Producto producto, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errores = result.getFieldErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .toList();
            return ResponseEntity.badRequest().body(errores);
        }

        // Validar si ya existe un producto con el mismo código
        Optional<Producto> existente = repositorio.findByCodigo(producto.getCodigo());
        if (existente.isPresent()) {
            return ResponseEntity.badRequest().body("⚠️ Ya existe un producto con ese código.");
        }

        Producto guardado = repositorio.save(producto);
        return ResponseEntity.ok(guardado);
    }

    // PUT: actualizar producto
    @PutMapping("/{codigo}")
    public ResponseEntity<?> actualizarProducto(@PathVariable String codigo, @Valid @RequestBody Producto producto, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errores = result.getFieldErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .toList();
            return ResponseEntity.badRequest().body(errores);
        }

        Optional<Producto> existente = repositorio.findByCodigo(codigo);
        if (existente.isPresent()) {
            Producto actualizar = existente.get();
            actualizar.setNombre(producto.getNombre());
            actualizar.setCantidad(producto.getCantidad());
            actualizar.setPrecioUnitario(producto.getPrecioUnitario());

            return ResponseEntity.ok(repositorio.save(actualizar));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE: eliminar producto
    @DeleteMapping("/{codigo}")
    public ResponseEntity<?> eliminarProducto(@PathVariable String codigo) {
        Optional<Producto> producto = repositorio.findByCodigo(codigo);
        if (producto.isPresent()) {
            repositorio.delete(producto.get());
            return ResponseEntity.ok("🗑️ Producto eliminado exitosamente.");
        } else {
            return ResponseEntity.status(404).body("❌ Producto no encontrado.");
        }
    }

    // GET: productos con bajo stock
    @GetMapping("/bajo-stock")
    public List<Producto> productosConBajoStock(@RequestParam(defaultValue = "5") int limite) {
        return repositorio.findAll()
                .stream()
                .filter(p -> p.getCantidad() <= limite)
                .toList();
    }
}
