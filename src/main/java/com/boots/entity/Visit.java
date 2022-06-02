package com.boots.entity;

import com.boots.transientClasses.Medicine;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "visit")
public class Visit {

    @Id
    private String number;
    private Date date;
    private String actions;
    private String complaints;
    private String notes;
    private Boolean status;
    private String medicine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "electronic_card", referencedColumnName = "RNTRC")
    private Patient patient;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor", referencedColumnName = "RNTRC")
    private Doctor doctor;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule")
    private Schedule schedule;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ICD_10")
    private Disease disease;

    //проверка на пустоту
    public void setMedicineByList(List<Medicine> medicines) {
        StringBuilder med = new StringBuilder();

        for (Medicine medicine : medicines) {
            if (med.length() != 0) {
                med.append(", ").append(medicine.getName());
            } else {
                med.append(medicine.getName());
            }
        }

        this.medicine = med.toString();
    }

    public String getDateToString() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.date);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int year = cal.get(Calendar.YEAR);
        return day + "." + month + "." + year;
    }

    public boolean getIsTodayVisit() {
        return this.getDate().equals(Date.valueOf(LocalDate.now().toString()));
    }

}
