package com.boots.service;

import com.boots.entity.BodyPart;
import com.boots.entity.Symptom;
import com.boots.repository.BodyPartRepository;
import com.boots.repository.SymptomsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class SymptomsService {

    @Autowired
    private BodyPartService bodyPartService;

    @Autowired
    private BodyPartRepository bodyPartRepository;

    @Autowired
    private SymptomsRepository symptomsRepository;

    public void addSymptom(Symptom symptom, String bodyPartId) {
        symptomsRepository.save(symptom);
        BodyPart bodyPart = bodyPartService.getById(bodyPartId);
        bodyPart.addSymptom(symptom);
        bodyPartRepository.save(bodyPart);
    }

    @Transactional
    public void add(Symptom symptom) {
        symptomsRepository.save(symptom);
    }

    @Transactional
    public void delete(Symptom symptom) {
        symptomsRepository.delete(symptom);
    }


    public Symptom getById(String id) {
        Optional<Symptom> symptomFromDb = symptomsRepository.findById(id);
        return symptomFromDb.orElse(new Symptom());

    }
}
