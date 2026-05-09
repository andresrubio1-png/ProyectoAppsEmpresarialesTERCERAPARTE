package com.davidperez.proyectocomponenteselectronicosback.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "MANUFACTURER")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Fabricante de componentes electrónicos")
public class Manufacturer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_manufacturer")
    @SequenceGenerator(name = "seq_manufacturer", sequenceName = "SEQ_MANUFACTURER", allocationSize = 1)
    @Column(name = "ID", columnDefinition = "NUMBER")
    @Schema(description = "Identificador único del fabricante", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;

    @Column(name = "NAME", nullable = false, length = 50)
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    @Schema(description = "Nombre del fabricante", example = "Texas Instruments")
    private String name;

    @Column(name = "COUNTRY", nullable = false, length = 50)
    @NotBlank(message = "El país no puede estar vacío")
    @Size(min = 2, max = 50, message = "El país debe tener entre 2 y 50 caracteres")
    @Schema(description = "País de origen del fabricante", example = "USA")
    private String country;

    @Column(name = "CREATED_AT", nullable = false, updatable = false, columnDefinition = "DATE")
    @Schema(description = "Fecha de registro", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDate createdAt;

    @Column(name = "AVERAGE_LEAD_TIME", nullable = false, columnDefinition = "NUMBER")
    @NotNull(message = "El tiempo promedio de entrega es obligatorio")
    @Positive(message = "El tiempo promedio debe ser mayor a 0")
    @DecimalMax(value = "365.0", message = "No puede superar 365 días")
    @Schema(description = "Tiempo promedio de entrega en días", example = "14.5")
    private Double averageLeadTime;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDate.now();
    }
}