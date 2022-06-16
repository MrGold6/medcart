package com.boots.service;

import com.boots.entity.Unit;
import com.boots.repository.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UnitService {
    @Autowired
    private UnitRepository unitRepository;

    @Transactional
    public List<Unit> allUnit() {
        return unitRepository.findAll();
    }

    @Transactional
    public void add(Unit unit) {
        unitRepository.save(unit);
    }

    @Transactional
    public void delete(Unit unit) {
        unitRepository.delete(unit);
    }


    public Unit getById(String id) {
        Optional<Unit> unitFromDb = unitRepository.findById(id);
        return unitFromDb.orElse(new Unit());

    }

    public boolean checkId(String id) {
        Optional<Unit> unitFromDb = unitRepository.findById(id);
        return !unitFromDb.isPresent();
    }

}
