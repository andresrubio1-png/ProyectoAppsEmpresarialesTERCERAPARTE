package com.davidperez.proyectocomponenteselectronicosback.service;

import com.davidperez.proyectocomponenteselectronicosback.dto.PassiveComponentRequest;
import com.davidperez.proyectocomponenteselectronicosback.model.Manufacturer;
import com.davidperez.proyectocomponenteselectronicosback.model.PackageType;
import com.davidperez.proyectocomponenteselectronicosback.model.PassiveComponent;
import com.davidperez.proyectocomponenteselectronicosback.repository.ManufacturerRepository;
import com.davidperez.proyectocomponenteselectronicosback.repository.PassiveComponentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class PassiveComponentService implements IPassiveComponentService {

    @Autowired
    private PassiveComponentRepository repository;

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    private Manufacturer resolveManufacturer(int manufacturerId) {
        return manufacturerRepository.findById(manufacturerId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Fabricante con id " + manufacturerId + " no encontrado"));
    }

    @Override
    public PassiveComponent create(PassiveComponentRequest request) {
        Manufacturer manufacturer = resolveManufacturer(request.getManufacturerId());
        PassiveComponent pc = new PassiveComponent();
        pc.setPinCount(request.getPinCount());
        pc.setPackageType(request.getPackageType());
        pc.setVoltage(request.getVoltage());
        pc.setTolerance(request.getTolerance());
        pc.setNominalValue(request.getNominalValue());
        pc.setNominalUnit(request.getNominalUnit());
        pc.setManufacturer(manufacturer);
        return repository.save(pc);
    }

    @Override
    public List<PassiveComponent> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<PassiveComponent> findById(int id) {
        return repository.findById(id);
    }

    @Override
    public List<PassiveComponent> findByPackageType(PackageType packageType) {
        return repository.findByPackageType(packageType);
    }

    @Override
    public List<PassiveComponent> findByVoltageRange(Double minVoltage, Double maxVoltage) {
        return repository.findByVoltageBetween(minVoltage, maxVoltage);
    }

    @Override
    public List<PassiveComponent> findByManufacturerId(Integer manufacturerId) {
        return repository.findByManufacturerId(manufacturerId);
    }

    @Override
    public List<Map<String, Object>> findAllWithManufacturerInfo(PackageType packageType, Double maxVoltage) {
        List<Object[]> rows = repository.findAllWithManufacturerInfo(packageType, maxVoltage);
        List<Map<String, Object>> result = new ArrayList<>();
        for (Object[] row : rows) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("id",               row[0]);
            map.put("pinCount",         row[1]);
            map.put("packageType",      row[2]);
            map.put("voltage",          row[3]);
            map.put("createdAt",        row[4]);
            map.put("tolerance",        row[5]);
            map.put("nominalValue",     row[6]);
            map.put("nominalUnit",      row[7]);
            map.put("manufacturerId",   row[8]);   // llave foránea
            map.put("manufacturerName", row[9]);   // atributo de Manufacturer
            result.add(map);
        }
        return result;
    }

    @Override
    public Optional<PassiveComponent> update(int id, PassiveComponentRequest request) {
        return repository.findById(id).map(existing -> {
            Manufacturer manufacturer = resolveManufacturer(request.getManufacturerId());
            existing.setPinCount(request.getPinCount());
            existing.setPackageType(request.getPackageType());
            existing.setVoltage(request.getVoltage());
            existing.setTolerance(request.getTolerance());
            existing.setNominalValue(request.getNominalValue());
            existing.setNominalUnit(request.getNominalUnit());
            existing.setManufacturer(manufacturer);
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
