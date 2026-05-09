package com.davidperez.proyectocomponenteselectronicosback.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "Valor nominal del componente con su unidad")
public class NominalValueRequest {

    @Positive(message = "El valor nominal debe ser positivo")
    @Schema(description = "Valor numérico del componente", example = "100.0", requiredMode = Schema.RequiredMode.REQUIRED)
    private Double value;

    @Size(max = 20, message = "La unidad no puede superar 20 caracteres")
    @Schema(description = "Unidad del valor nominal: Ohm, uF, uH, nF, etc.", example = "Ohm")
    private String unit;
}
