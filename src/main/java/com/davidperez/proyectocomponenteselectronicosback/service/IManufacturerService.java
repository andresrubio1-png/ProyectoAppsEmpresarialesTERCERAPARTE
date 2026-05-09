package com.davidperez.proyectocomponenteselectronicosback.service;

import com.davidperez.proyectocomponenteselectronicosback.dto.ManufacturerRequest;
import com.davidperez.proyectocomponenteselectronicosback.model.Manufacturer;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IManufacturerService {

    Manufacturer create(ManufacturerRequest request);

    List<Manufacturer> findAll();

    Optional<Manufacturer> findById(int id);

    Optional<Manufacturer> findByName(String name);

    List<Manufacturer> findByCountry(String country);

    List<Manufacturer> findByLeadTimeBetween(Double min, Double max);

    List<Map<String, Object>> findWithComponentStats();

    Optional<Manufacturer> update(int id, ManufacturerRequest request);

    boolean delete(int id);
}
