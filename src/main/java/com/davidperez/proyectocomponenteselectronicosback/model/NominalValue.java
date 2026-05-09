package com.davidperez.proyectocomponenteselectronicosback.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "NOMINAL_VALUE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Valor nominal reutilizable (ej: 100 Ohm, 10 uF)")
public class NominalValue {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_nominalvalue")
    @SequenceGenerator(name = "seq_nominalvalue", sequenceName = "SEQ_NOMINALVALUE", allocationSize = 1)
    @Column(name = "ID", columnDefinition = "NUMBER")
    @Schema(description = "Identificador único", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;

    @Column(name = "VALUE", nullable = false, columnDefinition = "NUMBER")
    @Positive(message = "El valor nominal debe ser positivo")
    @Schema(description = "Valor numérico", example = "100.0")
    private Double value;

    @Column(name = "UNIT", length = 20)
    @Size(max = 20, message = "La unidad no puede superar 20 caracteres")
    @Schema(description = "Unidad de medida: Ohm, uF, uH, nF, etc.", example = "Ohm")
    private String unit;
}
