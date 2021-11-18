package com.boots.service;

import com.boots.entity.Direction;
import com.boots.entity.Disease;
import com.boots.repository.DirectionRepository;
import com.boots.repository.DiseaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Service
public class DirectionService {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private DirectionRepository directionRepository;

    @Transactional
    public List<Direction> allDirection() {
        return directionRepository.findAll();
    }
    @Transactional
    public void add(Direction direction) {
        directionRepository.save(direction);
    }
    @Transactional
    public void delete(Direction direction) {
        directionRepository.delete(direction);
    }

    public boolean checkICD(String id) {
        Optional<Direction> directionFromDb = directionRepository.findById(id);
        return !directionFromDb.isPresent();
    }

    public Direction getById(String id) {
        Optional<Direction> directionFromDb = directionRepository.findById(id);
        return directionFromDb.orElse(new Direction());

    }
}
