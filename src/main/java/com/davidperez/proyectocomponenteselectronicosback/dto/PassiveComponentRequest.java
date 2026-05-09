package com.davidperez.proyectocomponenteselectronicosback.dto;

import com.davidperez.proyectocomponenteselectronicosback.model.PackageType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "Datos para crear o actualizar un componente pasivo")
public class PassiveComponentRequest {

    @NotNull(message = "El número de pines es obligatorio")
    @Positive(message = "El número de pines debe ser positivo")
    @Max(value = 1000, message = "No puede superar 1000 pines")
    @Schema(description = "Número de pines", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer pinCount;

    @Schema(description = "Tipo de encapsulado (opcional): SMD, DIP, SIP, QFP, BGA, SOT, TO, AXIAL", example = "SMD")
    private PackageType packageType;

    @NotNull(message = "El voltaje es obligatorio")
    @Positive(message = "El voltaje debe ser positivo")
    @DecimalMax(value = "1000.0", message = "El voltaje no puede superar 1000V")
    @Schema(description = "Voltaje de operación en voltios", example = "5.0", requiredMode = Schema.RequiredMode.REQUIRED)
    private Double voltage;

    @NotNull(message = "El ID del fabricante es obligatorio")
    @Schema(description = "ID del fabricante", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer manufacturerId;

    @NotNull(message = "La tolerancia es obligatoria")
    @PositiveOrZero(message = "La tolerancia debe ser 0 o positiva")
    @DecimalMax(value = "1.0", message = "La tolerancia no puede superar 1.0")
    @Schema(description = "Tolerancia del componente (ej: 0.05 = 5%)", example = "0.05", requiredMode = Schema.RequiredMode.REQUIRED)
    private Double tolerance;

    @NotNull(message = "El valor nominal es obligatorio")
    @Valid
    @Schema(description = "Valor nominal del componente con su unidad", requiredMode = Schema.RequiredMode.REQUIRED)
    private NominalValueRequest nominalValue;
}