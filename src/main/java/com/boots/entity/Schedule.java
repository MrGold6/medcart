package com.boots.entity;

import javax.persistence.*;

@Entity
@Table(name = "schedule")
public class Schedule {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "day")
    private int day;

    @Column(name = "time")
    private String time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor", referencedColumnName = "RNTRC")
    private Doctor doctor1;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Doctor getDoctor() {
        return doctor1;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor1 = doctor;
    }
}
