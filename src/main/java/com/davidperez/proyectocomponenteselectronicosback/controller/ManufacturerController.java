package com.davidperez.proyectocomponenteselectronicosback.controller;

import com.davidperez.proyectocomponenteselectronicosback.dto.ManufacturerRequest;
import com.davidperez.proyectocomponenteselectronicosback.model.Manufacturer;
import com.davidperez.proyectocomponenteselectronicosback.service.IManufacturerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/manufacturers")
@CrossOrigin(origins = "*")
@Tag(name = "Manufacturers", description = "Gestión de fabricantes de componentes electrónicos")
public class ManufacturerController {

    @Autowired
    private IManufacturerService service;

    @Operation(summary = "Crear un fabricante")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Fabricante creado",
                    content = @Content(schema = @Schema(implementation = Manufacturer.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Manufacturer> create(@Valid @RequestBody ManufacturerRequest request) {
        return new ResponseEntity<>(service.create(request), HttpStatus.CREATED);
    }

    @Operation(summary = "Listar fabricantes", description = "Retorna todos los fabricantes. Filtrar por 'country' o por rango de leadTime.")
    @GetMapping
    public ResponseEntity<List<Manufacturer>> findAll(
            @Parameter(description = "Filtrar por país") @RequestParam(required = false) String country,
            @Parameter(description = "LeadTime mínimo en días") @RequestParam(required = false) Double minLeadTime,
            @Parameter(description = "LeadTime máximo en días") @RequestParam(required = false) Double maxLeadTime) {

        if (country != null) {
            return ResponseEntity.ok(service.findByCountry(country));
        }
        if (minLeadTime != null && maxLeadTime != null) {
            return ResponseEntity.ok(service.findByLeadTimeBetween(minLeadTime, maxLeadTime));
        }
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(summary = "Buscar fabricante por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Fabricante encontrado",
                    content = @Content(schema = @Schema(implementation = Manufacturer.class))),
            @ApiResponse(responseCode = "404", description = "No encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Manufacturer> findById(@PathVariable int id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Buscar fabricante por nombre")
    @GetMapping("/search")
    public ResponseEntity<Manufacturer> findByName(
            @Parameter(description = "Nombre exacto del fabricante") @RequestParam String name) {
        return service.findByName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Consulta personalizada 1: fabricante + cantidad de componentes + voltaje promedio
     */
    @Operation(summary = "Estadísticas de fabricantes",
            description = "Muestra cada fabricante con la cantidad de componentes que tiene y el voltaje promedio de sus componentes")
    @GetMapping("/stats")
    public ResponseEntity<List<Map<String, Object>>> getStats() {
        return ResponseEntity.ok(service.findWithComponentStats());
    }

    @Operation(summary = "Actualizar fabricante")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Actualizado",
                    content = @Content(schema = @Schema(implementation = Manufacturer.class))),
            @ApiResponse(responseCode = "404", description = "No encontrado", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Manufacturer> update(@PathVariable int id,
                                               @Valid @RequestBody ManufacturerRequest request) {
        return service.update(id, request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar fabricante")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Eliminado", content = @Content),
            @ApiResponse(responseCode = "404", description = "No encontrado", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
