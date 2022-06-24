package com.boots.repository;

import com.boots.entity.BodyPart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BodyPartRepository extends JpaRepository<BodyPart, String> {

}
