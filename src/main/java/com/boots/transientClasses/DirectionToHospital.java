package com.boots.transientClasses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.Calendar;

@NoArgsConstructor
@Getter
@Setter
public class DirectionToHospital extends Document {

    private int number;
    private boolean typeHospital;
    private Date date;
    private String hospital;
    private String department;
    private String disease;
    private String bad_medicine;
    private boolean is_hepatitis;
    private String fluorography;
    private String oncoprofoglyad;
    private double temperature;
    private double at;
    private int pulse;
    private int respiratory_rate;
    private boolean is_independently;

    public String getDateToString() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.date);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int year = cal.get(Calendar.YEAR);
        return day + "." + month + "." + year;
    }

    public boolean getHepatitis() {
        return is_hepatitis;
    }

    public boolean getIndependently() {
        return is_independently;
    }
}
