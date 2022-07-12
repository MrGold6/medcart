package com.boots.repository;

import com.boots.entity.Patient;

import com.boots.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Patient findByUser(User user);
    @Query("SELECT p FROM Patient p WHERE p.telephone_number = :telephone_number")
    List<Patient> findPatientTelephoneNumber(@Param("telephone_number") int telephone_number);
}
