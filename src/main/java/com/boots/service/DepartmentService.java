package com.boots.service;

import com.boots.entity.Department;
import com.boots.entity.Direction;
import com.boots.entity.Doctor;
import com.boots.entity.Unit;
import com.boots.repository.DepartmentRepository;
import com.boots.repository.DirectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Transactional
    public List<Department> allDepartment() {
        List<Department> departments =  departmentRepository.findAll();
        departments.sort(Comparator.comparing(Department::getName));
        return departments;
    }

    @Transactional
    public List<Department> allDepartmentWithoutUnit() {
        List<Department> departments =  departmentRepository.findByUnitNull();
        departments.sort(Comparator.comparing(Department::getName));
        return departments;
    }

    @Transactional
    public void add(Department department) {
        departmentRepository.save(department);
    }

    @Transactional
    public void delete(Department department) {
        departmentRepository.delete(department);
    }


    public Department getById(String id) {
        Optional<Department> departmentFromDb = departmentRepository.findById(id);
        return departmentFromDb.orElse(new Department());

    }

    public boolean checkId(String id) {
        Optional<Department> departmentFromDb = departmentRepository.findById(id);
        return !departmentFromDb.isPresent();
    }


}
