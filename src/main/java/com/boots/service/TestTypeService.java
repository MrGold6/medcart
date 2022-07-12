package com.boots.service;

import com.boots.entity.TestsType;
import com.boots.repository.TestTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class TestTypeService {

    @Autowired
    private TestTypeRepository testTypeRepository;

    @Transactional
    public List<TestsType> allTestTypes() {
        List<TestsType> testsTypes = testTypeRepository.findAll();
        testsTypes.sort(Comparator.comparing(TestsType::getName));
        return testsTypes;
    }

    @Transactional
    public TestsType getTestsTypeById(String id) {
        return testTypeRepository.findTestsTypeById(id);
    }


    @Transactional
    public void add(TestsType testType) {
        testTypeRepository.save(testType);
    }

    @Transactional
    public void delete(TestsType testType) {
        testTypeRepository.delete(testType);
    }

    public boolean checkId(String id) {
        Optional<TestsType> testTypeFromDb = testTypeRepository.findById(id);
        return !testTypeFromDb.isPresent();
    }

}
