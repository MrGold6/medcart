package com.boots.service;

import com.boots.entity.MedicineCatalog;
import com.boots.entity.Patient;
import com.boots.repository.MedicineCatalogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Service
public class MedicineCatalogService {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private MedicineCatalogRepository medicineCatalogRepository;

    @Transactional
    public List<MedicineCatalog> allMedicine() {
        return medicineCatalogRepository.findAll();
    }
    @Transactional
    public void add(MedicineCatalog medicineCatalog) {
        medicineCatalogRepository.save(medicineCatalog);
    }
    @Transactional
    public void delete(MedicineCatalog medicineCatalog) {
        medicineCatalogRepository.delete(medicineCatalog);
    }

    public boolean checkATX(String ATX) {
        Optional<MedicineCatalog> medicineCatalogFromDb = medicineCatalogRepository.findById(ATX);
        return !medicineCatalogFromDb.isPresent();
    }

    public MedicineCatalog getById(String ATX) {

        Optional<MedicineCatalog> medicineCatalogFromDb = medicineCatalogRepository.findById(ATX);
        return medicineCatalogFromDb.orElse(new MedicineCatalog());

    }
}
