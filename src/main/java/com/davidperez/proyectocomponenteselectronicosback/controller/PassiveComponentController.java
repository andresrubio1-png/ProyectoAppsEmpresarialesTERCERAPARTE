package com.davidperez.proyectocomponenteselectronicosback.controller;

import com.davidperez.proyectocomponenteselectronicosback.dto.PassiveComponentRequest;
import com.davidperez.proyectocomponenteselectronicosback.model.PackageType;
import com.davidperez.proyectocomponenteselectronicosback.model.PassiveComponent;
import com.davidperez.proyectocomponenteselectronicosback.service.IPassiveComponentService;
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
@RequestMapping("/components/passive")
@CrossOrigin(origins = "*")
@Tag(name = "Passive Components", description = "Gestión de componentes electrónicos pasivos")
public class PassiveComponentController {
    @Autowired
    private IPassiveComponentService service;

    @Operation(summary = "Crear un componente pasivo")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Componente creado",
                    content = @Content(schema = @Schema(implementation = PassiveComponent.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o fabricante no existe", content = @Content)
    })
    @PostMapping
    public ResponseEntity<PassiveComponent> create(@Valid @RequestBody PassiveComponentRequest request) {
        return new ResponseEntity<>(service.create(request), HttpStatus.CREATED);
    }

    @Operation(summary = "Listar componentes",
            description = "Lista todos los componentes. Filtrar por packageType y/o voltaje máximo.")
    @GetMapping
    public ResponseEntity<List<PassiveComponent>> findAll(
            @Parameter(description = "Filtrar por tipo de encapsulado") @RequestParam(required = false) PackageType packageType,
            @Parameter(description = "Voltaje mínimo") @RequestParam(required = false) Double minVoltage,
            @Parameter(description = "Voltaje máximo") @RequestParam(required = false) Double maxVoltage,
            @Parameter(description = "Filtrar por ID de fabricante") @RequestParam(required = false) Integer manufacturerId) {

        if (packageType != null) {
            return ResponseEntity.ok(service.findByPackageType(packageType));
        }
        if (minVoltage != null && maxVoltage != null) {
            return ResponseEntity.ok(service.findByVoltageRange(minVoltage, maxVoltage));
        }
        if (manufacturerId != null) {
            return ResponseEntity.ok(service.findByManufacturerId(manufacturerId));
        }
        return ResponseEntity.ok(service.findAll());
    }

    /**
     * Consulta personalizada 3: componentes con FK + atributo de Manufacturer
     */
    @Operation(summary = "Listar componentes con info del fabricante",
            description = "Muestra todos los atributos del componente, la llave foránea (manufacturerId) y el nombre del fabricante")
    @GetMapping("/detail")
    public ResponseEntity<List<Map<String, Object>>> findWithManufacturerInfo(
            @Parameter(description = "Filtrar por tipo de encapsulado (opcional)") @RequestParam(required = false) PackageType packageType,
            @Parameter(description = "Voltaje máximo (opcional)") @RequestParam(required = false) Double maxVoltage) {
        return ResponseEntity.ok(service.findAllWithManufacturerInfo(packageType, maxVoltage));
    }

    @Operation(summary = "Buscar componente por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Componente encontrado",
                    content = @Content(schema = @Schema(implementation = PassiveComponent.class))),
            @ApiResponse(responseCode = "404", description = "No encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<PassiveComponent> findById(@PathVariable int id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Actualizar un componente pasivo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Actualizado",
                    content = @Content(schema = @Schema(implementation = PassiveComponent.class))),
            @ApiResponse(responseCode = "404", description = "No encontrado", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<PassiveComponent> update(@PathVariable int id,
                                                   @Valid @RequestBody PassiveComponentRequest request) {
        return service.update(id, request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar un componente pasivo")
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
