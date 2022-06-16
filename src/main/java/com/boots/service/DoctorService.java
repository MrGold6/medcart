package com.boots.service;

import com.boots.entity.*;
import com.boots.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @PersistenceContext
    private EntityManager em;

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

    public Doctor getById(Long id) {
        Doctor doctor = null;
        try {
            Optional<Doctor> doctorFromDb = doctorRepository.findById(id);
            doctor = doctorFromDb.orElse(new Doctor());
        } catch (NoResultException nre) {

        }

        return doctor;
    }

    public List<Doctor> doctorBySpecialization(Specialization specialization) {
        List<Doctor> doctors = null;
        try {
            doctors = doctorRepository.findBySpecialization(specialization);
        } catch (NoResultException nre) {

        }
        return doctors;
    }

    /*
    public List<Doctor> doctorBySpecializationList(List<Specialization> specializations) {
        List<Doctor> doctors = null;
        try {
            doctors = doctorRepository.findBySpecialization(specializations);
        } catch (NoResultException nre) {

        }

        return doctors;

    }*/

    public List<Doctor> findTelephone_number(int telephone_number) {
        List<Doctor> doctors = null;
        try {
            doctors = em.createQuery("SELECT p FROM Doctor p WHERE p.telephone_number = :paramId", Doctor.class)
                    .setParameter("paramId", telephone_number).getResultList();
        } catch (NoResultException nre) {
        }

        return doctors;
    }

    public Specialization getByIdSpecialization(int id) {
        Specialization specialization = null;
        try {
            specialization = em.createQuery("SELECT d FROM Specialization d WHERE d.id = :paramId", Specialization.class)
                    .setParameter("paramId", id).getSingleResult();
        } catch (NoResultException nre) {
        }

        return specialization;
    }

    public List<Specialization> allSpecializations() {
        List<Specialization> specializations = null;
        try {
            specializations = em.createQuery("SELECT d FROM Specialization d", Specialization.class).getResultList();

        } catch (NoResultException nre) {
        }

        return specializations;

    }


    @Transactional
    public int doctorsCount() {
        return (int) doctorRepository.count();
    }

    public Doctor doctorByUser(User user) {
        Doctor doctor = null;
        try {
            doctor = doctorRepository.findByUser(user);
        } catch (NoResultException nre) {

        }

        return doctor;

    }

    public boolean checkRNTRC(Long id) {
        Optional<Doctor> doctorFromDb = doctorRepository.findById(id);
        return !doctorFromDb.isPresent();

    }

    public List<Schedule> allSchedule() {
        List<Schedule> schedules = null;
        try {
            schedules = em.createQuery("SELECT d FROM Schedule d", Schedule.class).getResultList();

        } catch (NoResultException nre) {
        }

        return schedules;

    }

    //id_schedule

    public Schedule getScheduleById(int id_schedule) {
        Schedule schedule = null;
        try {
            schedule = em.createQuery("SELECT d FROM Schedule d WHERE d.id = :paramId", Schedule.class)
                    .setParameter("paramId", id_schedule).getSingleResult();

        } catch (NoResultException nre) {
        }

        return schedule;
    }

    @Transactional
    public void deleteSchedule(int id_schedule) {
        Query query = em.createQuery("DELETE FROM Schedule d WHERE d.id= :id_schedule");
        query.setParameter("id_schedule", id_schedule);
        query.executeUpdate();

    }
}
