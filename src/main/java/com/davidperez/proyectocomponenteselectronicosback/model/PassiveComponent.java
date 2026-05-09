package com.davidperez.proyectocomponenteselectronicosback.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "ELECTRONICCOMPONENT")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Componente electrónico pasivo (resistencia, capacitor, inductor, etc.)")
public class PassiveComponent {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_electroniccomponent")
    @SequenceGenerator(name = "seq_electroniccomponent", sequenceName = "SEQ_ELECTRONICCOMPONENT", allocationSize = 1)
    @Column(name = "ID", columnDefinition = "NUMBER")
    @Schema(description = "Identificador único del componente", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;

    @Column(name = "PIN_COUNT", nullable = false, columnDefinition = "NUMBER")
    @NotNull(message = "El número de pines es obligatorio")
    @Positive(message = "El número de pines debe ser positivo")
    @Max(value = 1000, message = "El número de pines no puede superar 1000")
    @Schema(description = "Número de pines del componente", example = "2")
    private Integer pinCount;

    @Enumerated(EnumType.STRING)
    @Column(name = "PACKAGE_TYPE", length = 20)
    @Schema(description = "Tipo de encapsulado físico (opcional): SMD, DIP, SIP, QFP, BGA, SOT, TO, AXIAL", example = "SMD")
    private PackageType packageType;

    @Column(name = "VOLTAGE", nullable = false, columnDefinition = "NUMBER")
    @NotNull(message = "El voltaje es obligatorio")
    @Positive(message = "El voltaje debe ser un valor positivo")
    @DecimalMax(value = "1000.0", message = "El voltaje no puede superar 1000V")
    @Schema(description = "Voltaje de operación en voltios", example = "5.0")
    private Double voltage;

    @Column(name = "CREATED_AT", nullable = false, updatable = false, columnDefinition = "DATE")
    @Schema(description = "Fecha de registro", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDate createdAt;

    @Column(name = "TOLERANCE", nullable = false, columnDefinition = "NUMBER")
    @NotNull(message = "La tolerancia es obligatoria")
    @PositiveOrZero(message = "La tolerancia debe ser 0 o positiva")
    @DecimalMax(value = "1.0", message = "La tolerancia no puede superar 1.0 (100%)")
    @Schema(description = "Tolerancia del componente (ej: 0.05 = 5%)", example = "0.05")
    private Double tolerance;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_NOMINAL_VALUE", nullable = false)
    @NotNull(message = "El valor nominal es obligatorio")
    @Schema(description = "Valor nominal del componente")
    private NominalValue nominalValue;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_MANUFACTURER", nullable = false)
    @NotNull(message = "El fabricante es obligatorio")
    @Schema(description = "Fabricante del componente")
    private Manufacturer manufacturer;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDate.now();
    }
}