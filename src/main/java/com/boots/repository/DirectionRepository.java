package com.boots.repository;

import com.boots.entity.Direction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectionRepository extends JpaRepository<Direction, String> {
}
