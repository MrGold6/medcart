package com.boots.service;

import com.boots.entity.*;
import com.boots.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private DoctorRepository doctorRepository;

    @Transactional
    public List<Doctor> allDoctors() {
        return doctorRepository.findAll();
    }
    @Transactional
    public void add(Doctor doctor) {
        doctorRepository.save(doctor);
    }
    @Transactional
    public void delete(Doctor doctor) {
        doctorRepository.delete(doctor);
    }

   /* public void update(Doctor doctor) {
        return em.createQuery("update d FROM Doctor d WHERE d.id = :paramId", Doctor.class)
                .setParameter("paramId", idMin).getResultList();
    }*/
   public Doctor getById(Long id) {

       Optional<Doctor> doctorFromDb = doctorRepository.findById(id);
       return doctorFromDb.orElse(new Doctor());

   }
    public  List<Doctor> doctorBySpecialization(Specialization specialization) {
        return (List<Doctor>) em.createQuery("SELECT d FROM Doctor d WHERE d.specialization= :specialization", Doctor.class)
                .setParameter("specialization", specialization).getResultList();
    }

    public Specialization getByIdSpecialization(int id) {

        return (Specialization) em.createQuery("SELECT d FROM Specialization d WHERE d.id = :paramId", Specialization.class)
                .setParameter("paramId", id).getSingleResult();
    }

    public List<Specialization> allSpecializations() {
        return  (List<Specialization>) em.createQuery("SELECT d FROM Specialization d", Specialization.class).getResultList();
    }


    @Transactional
    public int doctorsCount() {
        return (int) doctorRepository.count();
    }

    public  Doctor doctorByUser(User user) {
        return (Doctor) em.createQuery("SELECT d FROM Doctor d WHERE d.user= :user", Doctor.class)
                .setParameter("user", user).getSingleResult();
    }
/*
    public boolean checkRNTRC(String id) {
        if (em.createQuery("SELECT d FROM Doctor d WHERE d.RNTRC = :id", Doctor.class)
                .setParameter("id", id).getSingleResult()) {
            return false;
        } else {
            return true;
        }
    }*/
}
