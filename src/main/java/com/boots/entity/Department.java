package com.boots.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Department {

    @Id
    private String id;
    private String name;

    @OneToMany(mappedBy = "department_s", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StaffingScheme> staffingSchemes;


    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Doctor> doctors = new ArrayList<>();

    public List<Doctor> findByTelephone(int telephone) {
        List<Doctor> doctorsByTelephone = new ArrayList<>();
        for (Doctor doctor : this.getDoctors()) {
            if (doctor.getTelephone_number() == telephone) {
                doctorsByTelephone.add(doctor);
            }
        }

        return doctorsByTelephone;
    }

    public void addDoctor(Doctor doctor) {
        doctor.setDepartment(this);
        this.doctors.add(doctor);
    }

    public void addStaffingScheme(StaffingScheme staffingScheme) {
        staffingScheme.setDepartment_s(this);
        this.staffingSchemes.add(staffingScheme);
    }
}
