package com.boots.repository;

import com.boots.entity.Symptom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SymptomsRepository extends JpaRepository<Symptom, String> {

}

