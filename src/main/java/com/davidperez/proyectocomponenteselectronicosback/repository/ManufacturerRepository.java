package com.davidperez.proyectocomponenteselectronicosback.repository;

import com.davidperez.proyectocomponenteselectronicosback.model.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Integer> {

    Optional<Manufacturer> findByNameIgnoreCase(String name);

    List<Manufacturer> findByCountryIgnoreCase(String country);

    /**
     * CONSULTA PERSONALIZADA 1:
     * Muestra datos del fabricante (maestro) + cantidad de componentes y voltaje promedio (detalle).
     * Cumple: "mostrar los datos de la tabla maestro y dos de detalle"
     */
    @Query("""
        SELECT m.id, m.name, m.country, m.averageLeadTime,
               COUNT(pc.id) AS totalComponents,
               COALESCE(AVG(pc.voltage), 0) AS avgVoltage
        FROM Manufacturer m
        LEFT JOIN PassiveComponent pc ON pc.manufacturer = m
        GROUP BY m.id, m.name, m.country, m.averageLeadTime
        ORDER BY COUNT(pc.id) DESC
    """)
    List<Object[]> findManufacturersWithComponentStats();

    /**
     * CONSULTA PERSONALIZADA 2:
     * Fabricantes cuyo tiempo de entrega está dentro de un rango dado.
     */
    @Query("""
        SELECT m FROM Manufacturer m
        WHERE m.averageLeadTime BETWEEN :min AND :max
        ORDER BY m.averageLeadTime ASC
    """)
    List<Manufacturer> findByLeadTimeBetween(@Param("min") Double min, @Param("max") Double max);
}
