package com.boots.service;

import com.boots.entity.*;
import com.boots.repository.DoctorRepository;
import com.boots.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;


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

    public List<Doctor> doctorByNotSpecialization(Specialization specialization) {
        List<Doctor> doctors = null;
        try {
            doctors = doctorRepository.findBySpecializationNot(specialization);
        } catch (NoResultException nre) {

        }
        return doctors;
    }


    public List<Doctor> findTelephone_number(int telephone_number) {
        List<Doctor> doctors = null;
        try {
            doctors = doctorRepository.findDocTelephoneNumber(telephone_number);
        } catch (NoResultException nre) {

        }
        return doctors;
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

    public Schedule getScheduleById(int id_schedule) {

        Schedule schedule = null;
        try {
            Optional<Schedule> scheduleFromDb = scheduleRepository.findById(id_schedule);
            schedule = scheduleFromDb.orElse(new Schedule());
        } catch (NoResultException nre) {

        }
        return schedule;
    }

    @Transactional
    public void deleteSchedule(int id_schedule) {
        scheduleRepository.deleteById(id_schedule);
    }
}
