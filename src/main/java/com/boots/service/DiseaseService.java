package com.boots.service;

import com.boots.entity.Disease;
import com.boots.entity.MedicineCatalog;
import com.boots.repository.DiseaseRepository;
import com.boots.repository.MedicineCatalogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Service
public class DiseaseService {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private DiseaseRepository diseaseRepository;

    @Transactional
    public List<Disease> allDisease() {
        return diseaseRepository.findAll();
    }
    @Transactional
    public void add(Disease disease) {
        diseaseRepository.save(disease);
    }
    @Transactional
    public void delete(Disease disease) {
        diseaseRepository.delete(disease);
    }

    public boolean checkICD(String ICD) {
        Optional<Disease> diseaseFromDb = diseaseRepository.findById(ICD);
        return !diseaseFromDb.isPresent();
    }

    public Disease getById(String ICD) {
        Optional<Disease> diseaseFromDb = diseaseRepository.findById(ICD);
        return diseaseFromDb.orElse(new Disease());

    }
}
