package com.boots.repository;

import com.boots.entity.Direction;
import com.boots.entity.Patient;
import com.boots.entity.Specialization;
import com.boots.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DirectionRepository extends JpaRepository<Direction, String> {

    List<Direction> findBySpecializationAndStatus(Specialization specialization, Boolean status);
    List<Direction> findBySpecializationAndStatusAndPatient(Specialization specialization, Boolean status, Patient patient);
}
