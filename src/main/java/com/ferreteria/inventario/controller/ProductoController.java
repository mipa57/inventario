package com.ferreteria.inventario.controller;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ferreteria.inventario.model.Producto;
import com.ferreteria.inventario.repository.ProductoRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*") // Permite peticiones desde frontend externo (como Netlify)
public class ProductoController {

    @Autowired
    private ProductoRepository repositorio;

    // GET: Listar productos
    @GetMapping
    public List<Producto> obtenerTodos() {
        return repositorio.findAll();
    }

    // POST: Crear producto
    @PostMapping
    public ResponseEntity<?> crearProducto(@Valid @RequestBody Producto producto, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errores = result.getFieldErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .toList();
            return ResponseEntity.badRequest().body(errores);
        }

        // Validar si ya existe un producto con el mismo nombre
        Optional<Producto> existente = repositorio.findByNombre(producto.getNombre());
        if (existente.isPresent()) {
            return ResponseEntity.badRequest().body("⚠️ Ya existe un producto con ese nombre.");
        }

        Producto guardado = repositorio.save(producto);
        return ResponseEntity.ok(guardado);
    }

    // PUT: Actualizar producto
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarProducto(@PathVariable String id, @Valid @RequestBody Producto producto, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errores = result.getFieldErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .toList();
            return ResponseEntity.badRequest().body(errores);
        }

        Optional<Producto> existente = repositorio.findById(id);
        if (existente.isPresent()) {
            Producto actualizar = existente.get();
            actualizar.setCodigo(producto.getCodigo());
            actualizar.setNombre(producto.getNombre());
            actualizar.setCantidad(producto.getCantidad());
            actualizar.setPrecioUnitario(producto.getPrecioUnitario());

            return ResponseEntity.ok(repositorio.save(actualizar));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE: Eliminar producto
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarProducto(@PathVariable String id) {
        Optional<Producto> producto = repositorio.findById(id);
        if (producto.isPresent()) {
            repositorio.deleteById(id);
            return ResponseEntity.ok("🗑️ Producto eliminado exitosamente.");
        } else {
            return ResponseEntity.status(404).body("❌ Producto no encontrado.");
        }
    }
}

