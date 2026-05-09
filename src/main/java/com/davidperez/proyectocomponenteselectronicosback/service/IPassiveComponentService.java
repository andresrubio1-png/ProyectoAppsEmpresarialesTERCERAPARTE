package com.davidperez.proyectocomponenteselectronicosback.service;

import com.davidperez.proyectocomponenteselectronicosback.dto.PassiveComponentRequest;
import com.davidperez.proyectocomponenteselectronicosback.model.PackageType;
import com.davidperez.proyectocomponenteselectronicosback.model.PassiveComponent;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IPassiveComponentService {

    PassiveComponent create(PassiveComponentRequest request);

    List<PassiveComponent> findAll();

    List<PassiveComponent> findByPackageType(PackageType packageType);

    List<PassiveComponent> findByVoltageRange(Double minVoltage, Double maxVoltage);

    List<PassiveComponent> findByManufacturerId(Integer manufacturerId);

    List<Map<String, Object>> findAllWithManufacturerInfo(PackageType packageType, Double maxVoltage);

    Optional<PassiveComponent> findById(int id);

    Optional<PassiveComponent> update(int id, PassiveComponentRequest request);

    boolean delete(int id);
}
