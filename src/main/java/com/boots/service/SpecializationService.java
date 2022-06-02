package com.boots.service;

import com.boots.entity.Specialization;
import com.boots.repository.SpecializationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SpecializationService {

    @Autowired
    private SpecializationRepository specializationRepository;

    @Transactional
    public List<Specialization> allSpecialization() {
        return specializationRepository.findAll();
    }

    @Transactional
    public void add(Specialization specialization) {
        specializationRepository.save(specialization);
    }

    @Transactional
    public void delete(Specialization specialization) {
        specializationRepository.delete(specialization);
    }

    public boolean checkId(int id) {
        Optional<Specialization> specializationFromDb = specializationRepository.findById(id);
        return !specializationFromDb.isPresent();
    }

    public Specialization getById(int id) {

        Optional<Specialization> specializationFromDb = specializationRepository.findById(id);
        return specializationFromDb.orElse(new Specialization());

    }
}
