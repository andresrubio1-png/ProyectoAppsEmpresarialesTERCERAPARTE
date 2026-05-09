package com.davidperez.proyectocomponenteselectronicosback.repository;

import com.davidperez.proyectocomponenteselectronicosback.model.NominalValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NominalValueRepository extends JpaRepository<NominalValue, Integer> {

    Optional<NominalValue> findByValueAndUnit(Double value, String unit);
}
