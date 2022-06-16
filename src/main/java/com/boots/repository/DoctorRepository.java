package com.boots.repository;

import com.boots.entity.Doctor;
import com.boots.entity.Specialization;
import com.boots.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
  //  List<Doctor> findBySpecializationList(List<Specialization> specializations);
    List<Doctor> findBySpecialization(Specialization specializations);
    Doctor findByUser(User user);

}
