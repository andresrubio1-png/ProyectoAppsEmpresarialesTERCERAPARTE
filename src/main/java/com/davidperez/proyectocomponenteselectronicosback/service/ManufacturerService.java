package com.davidperez.proyectocomponenteselectronicosback.service;

import com.davidperez.proyectocomponenteselectronicosback.dto.ManufacturerRequest;
import com.davidperez.proyectocomponenteselectronicosback.model.Manufacturer;
import com.davidperez.proyectocomponenteselectronicosback.repository.ManufacturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ManufacturerService implements IManufacturerService {

    @Autowired
    private ManufacturerRepository repository;

    @Override
    public Manufacturer create(ManufacturerRequest request) {
        Manufacturer m = new Manufacturer();
        m.setName(request.getName());
        m.setCountry(request.getCountry());
        m.setAverageLeadTime(request.getAverageLeadTime());
        return repository.save(m);
    }

    @Override
    public List<Manufacturer> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Manufacturer> findById(int id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Manufacturer> findByName(String name) {
        return repository.findByNameIgnoreCase(name);
    }

    @Override
    public List<Manufacturer> findByCountry(String country) {
        return repository.findByCountryIgnoreCase(country);
    }

    @Override
    public List<Manufacturer> findByLeadTimeBetween(Double min, Double max) {
        return repository.findByLeadTimeBetween(min, max);
    }

    @Override
    public List<Map<String, Object>> findWithComponentStats() {
        List<Object[]> rows = repository.findManufacturersWithComponentStats();
        List<Map<String, Object>> result = new ArrayList<>();
        for (Object[] row : rows) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("id",               row[0]);
            map.put("name",             row[1]);
            map.put("country",          row[2]);
            map.put("averageLeadTime",  row[3]);
            map.put("totalComponents",  row[4]);
            map.put("avgVoltage",       row[5]);
            result.add(map);
        }
        return result;
    }

    @Override
    public Optional<Manufacturer> update(int id, ManufacturerRequest request) {
        return repository.findById(id).map(existing -> {
            existing.setName(request.getName());
            existing.setCountry(request.getCountry());
            existing.setAverageLeadTime(request.getAverageLeadTime());
            return repository.save(existing);
        });
    }

    @Override
    public boolean delete(int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
