package com.boots.service;

import com.boots.entity.*;
import com.boots.repository.DoctorRepository;
import com.boots.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

    public  Patient patientByUser(User user) {
        return (Patient) em.createQuery("SELECT d FROM Patient d WHERE d.user= :user", Patient.class)
                .setParameter("user", user).getSingleResult();
    }
    public boolean deletePatient(Long userId) {
        if (patientRepository.findById(userId).isPresent()) {
            patientRepository.deleteById(userId);
            return true;
        }
        return false;
    }



    public Patient getById(Long id) {

        Optional<Patient> patientFromDb = patientRepository.findById(id);
        return patientFromDb.orElse(new Patient());

    }

    public int patientsCount() {
        return (int) patientRepository.count();

    }

    public boolean checkRNTRC(Long id) {
        Optional<Patient> patientFromDb = patientRepository.findById(id);
        return !patientFromDb.isPresent();

    }


    public List<Disease> allDiseases() {
        return  (List<Disease>) em.createQuery("SELECT d FROM Disease d", Disease.class).getResultList();
    }

    public Disease getByIdDisease(String id) {
        return (Disease) em.createQuery("SELECT d FROM Disease d WHERE d.ICD_10 = :paramId", Disease.class)
                .setParameter("paramId", id).getSingleResult();
    }

    public List<MedicineCatalog> allMedicines() {
        return  (List<MedicineCatalog>) em.createQuery("SELECT m FROM MedicineCatalog m", MedicineCatalog.class).getResultList();
    }


    public  List<Patient> findTelephone_number(int telephone_number) {
        return (List<Patient>) em.createQuery("SELECT p FROM Patient p WHERE p.telephone_number = :paramId", Patient.class)
                .setParameter("paramId", telephone_number).getResultList();
    }

}
