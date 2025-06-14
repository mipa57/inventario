package com.ferreteria.inventario.controller;

import com.ferreteria.inventario.model.Transaccion;
import com.ferreteria.inventario.repository.TransaccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus; //
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/transacciones")
// @CrossOrigin(origins = "http://localhost:8080")
public class ContabilidadController {

    @Autowired
    private TransaccionRepository transaccionRepository; // La advertencia "Field injection is not recommended" sugiere usar inyección por constructor.

    // GET: Calcular balance (diario/semanal/mensual)
    @GetMapping("/balance")
    public ResponseEntity<?> getBalance(
            @RequestParam(required = false) String periodo,
            @RequestParam(required = false) String fechaInicio,
            @RequestParam(required = false) String fechaFin) {

        final LocalDate startFilter; // Declara como final o effectively final
        final LocalDate endFilter;   // Declara como final o effectively final

        if (periodo != null) {
            LocalDate tempStart = null;
            LocalDate tempEnd = LocalDate.now();

            switch (periodo.toLowerCase()) {
                case "diario":
                    tempStart = LocalDate.now();
                    break;
                case "semanal":
                    tempStart = LocalDate.now().minusWeeks(1);
                    break;
                case "mensual":
                    tempStart = LocalDate.now().minusMonths(1);
                    break;
                default:
                    return ResponseEntity.badRequest().body("Periodo no válido. Use 'diario', 'semanal', o 'mensual'.");
            }
            startFilter = tempStart;
            endFilter = tempEnd;

        } else if (fechaInicio != null && fechaFin != null) {
            startFilter = LocalDate.parse(fechaInicio);
            endFilter = LocalDate.parse(fechaFin);
        } else {
            // Si no hay filtros, se retornan todas las transacciones
            startFilter = null; // O un valor que indique "sin filtro de inicio"
            endFilter = null;   // O un valor que indique "sin filtro de fin"
        }

        List<Transaccion> transacciones = transaccionRepository.findAll();

        // Aplicar el filtro solo si startFilter o endFilter no son nulos
        if (startFilter != null && endFilter != null) {
            transacciones = transacciones.stream()
                    .filter(t -> !t.getFecha().isBefore(startFilter) && !t.getFecha().isAfter(endFilter))
                    .collect(Collectors.toList());
        } else if (startFilter != null) { // Si solo hay fecha de inicio (podrías expandir esto)
            transacciones = transacciones.stream()
                    .filter(t -> !t.getFecha().isBefore(startFilter))
                    .collect(Collectors.toList());
        } else if (endFilter != null) { // Si solo hay fecha de fin (podrías expandir esto)
            transacciones = transacciones.stream()
                    .filter(t -> !t.getFecha().isAfter(endFilter))
                    .collect(Collectors.toList());
        }
        // Si ambos son null, transacciones ya contiene todos los registros

        double ingresos = transacciones.stream()
                .filter(t -> "venta".equals(t.getTipo())) //
                .mapToDouble(Transaccion::getMonto) //
                .sum(); //

        double egresos = transacciones.stream()
                .filter(t -> "compra".equals(t.getTipo())) //
                .mapToDouble(Transaccion::getMonto) //
                .sum(); //

        double balance = ingresos - egresos;

        // Mejorar la respuesta JSON usando un Map o una clase DTO
        return ResponseEntity.ok(
                "{" +
                        "\"ingresos\": " + ingresos + "," +
                        "\"egresos\": " + egresos + "," +
                        "\"balance\": " + balance +
                        "}"
        );
    }

    // ... (otros métodos del controlador)
    // POST: Registrar una venta
    @PostMapping("/venta")
    public ResponseEntity<Transaccion> registrarVenta(@RequestBody Transaccion transaccion) {
        transaccion.setTipo("venta");
        transaccion.setFecha(LocalDate.now()); // Establece la fecha actual
        Transaccion savedTransaccion = transaccionRepository.save(transaccion);
        // TODO: Aquí podrías añadir lógica para actualizar el stock de productos
        return new ResponseEntity<>(savedTransaccion, HttpStatus.CREATED);
    }

    // POST: Registrar una compra de insumos
    @PostMapping("/compra")
    public ResponseEntity<Transaccion> registrarCompra(@RequestBody Transaccion transaccion) {
        transaccion.setTipo("compra");
        transaccion.setFecha(LocalDate.now()); // Establece la fecha actual
        Transaccion savedTransaccion = transaccionRepository.save(transaccion);
        // TODO: Aquí podrías añadir lógica para actualizar el stock de productos
        return new ResponseEntity<>(savedTransaccion, HttpStatus.CREATED);
    }

    // GET: Obtener historial de transacciones
    @GetMapping("/historial")
    public List<Transaccion> getHistorialTransacciones() {
        return transaccionRepository.findAll();
    }
}
