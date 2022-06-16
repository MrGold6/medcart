package com.boots.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Department {

    @Id
    private String id;
    private String name;

    @OneToMany(mappedBy = "department_s", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StaffingScheme> staffingSchemes = new ArrayList<>();

    @OneToMany(mappedBy = "department")
    private List<Doctor> doctors = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit")
    private Unit unit;

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
        this.changeCountDueToSpecialization(doctor.getSpecialization(), -1);
        doctor.setDepartment(this);
        this.doctors.add(doctor);
    }

    public void removeDoctor(Doctor doctor) {
        this.changeCountDueToSpecialization(doctor.getSpecialization(), 1);
        doctor.setDepartment(null);
        this.doctors.remove(doctor);
    }

    public void addStaffingScheme(StaffingScheme staffingScheme) {
        staffingScheme.setDepartment_s(this);
        this.staffingSchemes.add(staffingScheme);
    }

    public void removeStaffingScheme(String id_staffingScheme) {
        this.staffingSchemes.removeIf(staffingScheme -> staffingScheme.getNumber().equals(id_staffingScheme));
    }

    public List<Specialization> getSpecializationListByScheme() {
        List<Specialization> specializations = new ArrayList<>();
        for (StaffingScheme staffingScheme : this.staffingSchemes) {
            if (staffingScheme.getCount() > 0) {
                if (!specializations.contains(staffingScheme.getSpecialization())) {
                    specializations.add(staffingScheme.getSpecialization());
                }
            }
        }

        return specializations;
    }

    public List<Doctor> findBySpecialization(Specialization specialization) {
        List<Doctor> doctors = new ArrayList<>();
        for (Doctor doctor : this.getDoctors()) {
            if (doctor.getSpecialization().equals(specialization)) {
                doctors.add(doctor);
            }
        }

        return doctors;
    }

    public void changeCountDueToSpecialization(Specialization specialization, int i) {
        double num = 0;
        for (StaffingScheme staffingScheme : this.staffingSchemes) {
            if (staffingScheme.getSpecialization().equals(specialization)) {
                num = staffingScheme.getCount() + i;
                if (staffingScheme.getMaxCount() >= num && num >= 0) {
                    staffingScheme.setCount(num);
                }
            }
        }
    }

}
