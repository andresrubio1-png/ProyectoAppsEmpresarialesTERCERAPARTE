package com.davidperez.proyectocomponenteselectronicosback.repository;

import com.davidperez.proyectocomponenteselectronicosback.model.PackageType;
import com.davidperez.proyectocomponenteselectronicosback.model.PassiveComponent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PassiveComponentRepository extends JpaRepository<PassiveComponent, Integer> {

    List<PassiveComponent> findByPackageType(PackageType packageType);

    List<PassiveComponent> findByVoltageBetween(Double minVoltage, Double maxVoltage);

    List<PassiveComponent> findByManufacturerId(Integer manufacturerId);

    /**
     * CONSULTA PERSONALIZADA 3:
     * Lista componentes mostrando todos sus atributos + llave foránea (manufacturer.id)
     * + un atributo del fabricante (manufacturer.name).
     * Cumple: "El listar de la Clase/Tabla B debe mostrar todos sus atributos
     *          más la llave foránea y un atributo de la Clase/Tabla A"
     */
    @Query("""
        SELECT pc.id, pc.pinCount, pc.packageType, pc.voltage, pc.createdAt,
               pc.tolerance,
               pc.nominalValue.id, pc.nominalValue.value, pc.nominalValue.unit,
               pc.manufacturer.id, pc.manufacturer.name
        FROM PassiveComponent pc
        WHERE (:packageType IS NULL OR pc.packageType = :packageType)
          AND (:maxVoltage IS NULL OR pc.voltage <= :maxVoltage)
        ORDER BY pc.id ASC
    """)
    List<Object[]> findAllWithManufacturerInfo(
            @Param("packageType") PackageType packageType,
            @Param("maxVoltage") Double maxVoltage
    );
}
