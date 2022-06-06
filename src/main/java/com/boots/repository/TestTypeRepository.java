package com.boots.repository;

import com.boots.entity.TestsType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestTypeRepository extends JpaRepository<TestsType, String> {
    TestsType findTestsTypeById(String id);
}