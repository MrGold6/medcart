package com.boots.entity;

import com.boots.transientClasses.Medicine;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

@Entity
@Table(name = "visit")
public class Visit {
    @Id
    @Column(name = "number")
    private String number;

    @Column(name = "Date")
    private Date date;

    @Column(name = "actions")
    private String actions;

    @Column(name = "complaints")
    private String complaints;

    @Column(name = "notes")
    private String notes;

    @Column(name = "status")
    private Boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "electronic_card", referencedColumnName = "RNTRC")
    private Patient patient;
//МБ нужно фиксануть EAGER
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor", referencedColumnName = "RNTRC")
    private Doctor doctor;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="schedule")
    private Schedule schedule;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ICD_10")
    private Disease disease;

    @Column(name = "medicine")
    private String medicine;

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public Visit() {

    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }


   public Disease getDisease() {
        return disease;
    }

    public void setDisease(Disease disease) {
        this.disease = disease;
    }

    public String getMedicine() {
            return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    //проверка на пустоту
    public void setMedicineByList(List<Medicine> medicines) {
        StringBuilder med = new StringBuilder();

        for(Medicine medicine: medicines)
            if(med.length()!=0)
                med.append(", ").append(medicine.getName());
            else
                med.append(medicine.getName());


        this.medicine = med.toString();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getComplaints() {
        return complaints;
    }

    public void setComplaints(String complaints) {
        this.complaints = complaints;
    }

    public String getActions() {
        return actions;
    }

    public void setActions(String actions) {
        this.actions = actions;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public String getDateToString()
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.date);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int year = cal.get(Calendar.YEAR);
        return day+"."+month+"."+year;
    }

    public boolean getIsTodayVisit()
    {
        return this.getDate().equals(Date.valueOf(LocalDate.now().toString()));
    }
}
