package com.boots.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import javax.persistence.Table;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "schedule")
public class Schedule {

    @Id
    private int id;
    private int day;
    private String time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor", referencedColumnName = "RNTRC")
    private Doctor doctor1;

    public Doctor getDoctor() {
        return doctor1;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor1 = doctor;
    }

    public String getDayName() {
        String name = null;
        switch (this.day) {
            case 1: {
                name = "Неділя";
                break;
            }
            case 2: {
                name = "Понеділок";
                break;
            }
            case 3: {
                name = "Вівторок";
                break;
            }
            case 4: {
                name = "Середа";
                break;
            }
            case 5: {
                name = "Четвер";
                break;
            }
            case 6: {
                name = "П'ятниця";
                break;
            }
            case 7: {
                name = "Субота";
                break;
            }
        }

        return name;
    }

}
