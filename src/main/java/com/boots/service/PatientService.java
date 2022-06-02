package com.boots.service;

import com.boots.entity.*;
import com.boots.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    @PersistenceContext
    private EntityManager em;
    @Autowired
    private PatientRepository patientRepository;

    public List<Patient> allPatients() {
        return patientRepository.findAll();
    }

    public void add(Patient patient) {
        patientRepository.save(patient);
    }

    public void delete(Patient patient) {
        patientRepository.delete(patient);
    }

    @Transactional
    public void deleteVisit(String id_visit) {

        Query query = em.createQuery("DELETE FROM Visit d WHERE d.number= :id_visit");
        query.setParameter("id_visit", id_visit);
        query.executeUpdate();

    }


    public Patient patientByUser(User user) {
        Patient patient = null;
        try {
            patient = em.createQuery("SELECT d FROM Patient d WHERE d.user= :user", Patient.class)
                    .setParameter("user", user).getSingleResult();

        } catch (NoResultException nre) {
        }

        return patient;
    }

    public void deletePatient(Long userId) {
        if (patientRepository.findById(userId).isPresent()) {
            patientRepository.deleteById(userId);
        }
    }

    public Patient getById(Long id) {
        Patient patient = null;
        try {
            Optional<Patient> patientFromDb = patientRepository.findById(id);
            patient = patientFromDb.orElse(new Patient());
        } catch (NoResultException nre) {
        }

        return patient;
    }

    public int patientsCount() {
        return (int) patientRepository.count();
    }

    public boolean checkRNTRC(Long id) {
        Optional<Patient> patientFromDb = patientRepository.findById(id);
        return !patientFromDb.isPresent();

    }


    public List<Disease> allDiseases() {
        List<Disease> diseases = null;
        try {
            diseases = em.createQuery("SELECT d FROM Disease d", Disease.class).getResultList();
        } catch (NoResultException nre) {
        }

        return diseases;
    }

    public Disease getByIdDisease(String id) {
        Disease disease = null;
        try {
            disease = em.createQuery("SELECT d FROM Disease d WHERE d.ICD_10 = :paramId", Disease.class)
                    .setParameter("paramId", id).getSingleResult();
        } catch (NoResultException nre) {
        }

        return disease;
    }

    public List<MedicineCatalog> allMedicines() {
        List<MedicineCatalog> medicineCatalogs = null;
        try {
            medicineCatalogs = em.createQuery("SELECT m FROM MedicineCatalog m", MedicineCatalog.class).getResultList();
        } catch (NoResultException nre) {
        }

        return medicineCatalogs;
    }


    public List<Patient> findTelephone_number(int telephone_number) {
        List<Patient> patients = null;
        try {
            patients = em.createQuery("SELECT p FROM Patient p WHERE p.telephone_number = :paramId", Patient.class)
                    .setParameter("paramId", telephone_number).getResultList();
        } catch (NoResultException nre) {
        }

        return patients;
    }

}
