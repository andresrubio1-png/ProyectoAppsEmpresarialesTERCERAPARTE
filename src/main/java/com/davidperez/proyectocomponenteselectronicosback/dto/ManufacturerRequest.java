package com.davidperez.proyectocomponenteselectronicosback.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "Datos para crear o actualizar un fabricante")
public class ManufacturerRequest {

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    @Schema(description = "Nombre del fabricante", example = "Texas Instruments", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @NotBlank(message = "El país no puede estar vacío")
    @Size(min = 2, max = 50, message = "El país debe tener entre 2 y 50 caracteres")
    @Schema(description = "País de origen", example = "USA", requiredMode = Schema.RequiredMode.REQUIRED)
    private String country;

    @NotNull(message = "El tiempo promedio de entrega es obligatorio")
    @Positive(message = "El tiempo promedio debe ser mayor a 0")
    @DecimalMax(value = "365.0", message = "No puede superar 365 días")
    @Schema(description = "Tiempo promedio de entrega en días", example = "14.5", requiredMode = Schema.RequiredMode.REQUIRED)
    private Double averageLeadTime;
}
