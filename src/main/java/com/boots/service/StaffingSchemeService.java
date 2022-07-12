package com.boots.service;

import com.boots.entity.StaffingScheme;
import com.boots.repository.StaffingSchemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class StaffingSchemeService {

    @Autowired
    private StaffingSchemeRepository staffingSchemeRepository;

    @Transactional
    public void add(StaffingScheme staffingScheme) {
        staffingSchemeRepository.save(staffingScheme);
    }

    @Transactional
    public void delete(StaffingScheme staffingScheme) {
        staffingSchemeRepository.delete(staffingScheme);
    }


    public StaffingScheme getById(String id) {
        Optional<StaffingScheme> staffingSchemeFromDb = staffingSchemeRepository.findById(id);
        return staffingSchemeFromDb.orElse(new StaffingScheme());

    }


}
