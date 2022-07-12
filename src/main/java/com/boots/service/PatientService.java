package com.boots.service;

import com.boots.entity.*;
import com.boots.repository.DirectionRepository;
import com.boots.repository.MedicineCatalogRepository;
import com.boots.repository.PatientRepository;
import com.boots.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DirectionRepository directionRepository;
    @Autowired
    private VisitRepository visitRepository;
    @Autowired
    private MedicineCatalogRepository medicineCatalogRepository;

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
    public void deleteVisit(Visit visit) {
        visitRepository.deleteById(visit.getNumber());
    }

    public Patient patientByUser(User user) {
        Patient patient = null;
        try {
            patient = patientRepository.findByUser(user);

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


    public boolean checkRNTRC(Long id) {
        Optional<Patient> patientFromDb = patientRepository.findById(id);
        return !patientFromDb.isPresent();

    }


    public List<MedicineCatalog> allMedicines() {
        List<MedicineCatalog> medicineCatalogs = null;
        try {
            medicineCatalogs = medicineCatalogRepository.findAll();
        } catch (NoResultException nre) {
        }

        return medicineCatalogs;
    }


    public List<Patient> findTelephone_number(int telephone_number) {
        List<Patient> patients = null;
        try {
            patients = patientRepository.findPatientTelephoneNumber(telephone_number);
        } catch (NoResultException nre) {
        }

        return patients;
    }

    public Set<Patient> getPatientsByDirection(Specialization specialization, Boolean status) {
        Set<Patient> patients = new HashSet<>();

        for (Direction direction : directionRepository.findBySpecializationAndStatus(specialization, status)) {
            patients.add(direction.getPatient());
        }

        return patients;

    }

    public List<Patient> getPatientsByDirection(Specialization specialization, Boolean status, int telephone_number) {
        List<Patient> patients = new ArrayList<>();

        for (Direction direction : directionRepository.findBySpecializationAndStatus(specialization, status)) {
            if (direction.getPatient().getTelephone_number() == telephone_number) {
                patients.add(direction.getPatient());
            }
        }

        return patients;

    }

}
