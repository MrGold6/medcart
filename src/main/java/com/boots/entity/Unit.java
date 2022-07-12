package com.boots.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Unit {
    @Id
    private String id;
    private String name;

    @OneToMany(mappedBy = "unit")
    private List<Department> departmentList = new ArrayList<>();

    public void addDepartment(Department department) {
        department.setUnit(this);
        this.departmentList.add(department);
    }

    public void removeDepartment(Department department) {
        department.setUnit(null);
        this.departmentList.remove(department);
    }
}
