package com.boots.repository;

import com.boots.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, String> {
    List<Department> findByUnitNull();

}
