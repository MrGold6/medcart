package com.boots.repository;

import com.boots.entity.Doctor;
import com.boots.entity.Specialization;
import com.boots.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    List<Doctor> findBySpecialization(Specialization specializations);

    List<Doctor> findBySpecializationNot(Specialization specializations);

    Doctor findByUser(User user);

    @Query("SELECT d FROM Doctor d WHERE d.telephone_number = :telephone_number")
    List<Doctor> findDocTelephoneNumber(@Param("telephone_number") int telephone_number);

}
