package com.boots.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class StaffingScheme {

    @Id
    private String number;
    private double count;
    private double maxCount;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "specialization", referencedColumnName = "id")
    private Specialization specialization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_s")
    private Department department_s;

    public void setCountDueToMaxCount(int countOfDoctors) {
        this.setCount(this.getMaxCount() - countOfDoctors);
    }
}
