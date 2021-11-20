package com.boots.entity;

import java.sql.Date;

public class DirectionToHospital  extends Document{
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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isTypeHospital() {
        return typeHospital;
    }

    public void setTypeHospital(boolean typeHospital) {
        this.typeHospital = typeHospital;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getBad_medicine() {
        return bad_medicine;
    }

    public void setBad_medicine(String bad_medicine) {
        this.bad_medicine = bad_medicine;
    }

    public boolean isIs_hepatitis() {
        return is_hepatitis;
    }

    public void setIs_hepatitis(boolean is_hepatitis) {
        this.is_hepatitis = is_hepatitis;
    }

    public String getFluorography() {
        return fluorography;
    }

    public void setFluorography(String fluorography) {
        this.fluorography = fluorography;
    }

    public String getOncoprofoglyad() {
        return oncoprofoglyad;
    }

    public void setOncoprofoglyad(String oncoprofoglyad) {
        this.oncoprofoglyad = oncoprofoglyad;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getAt() {
        return at;
    }

    public void setAt(double at) {
        this.at = at;
    }

    public int getPulse() {
        return pulse;
    }

    public void setPulse(int pulse) {
        this.pulse = pulse;
    }

    public int getRespiratory_rate() {
        return respiratory_rate;
    }

    public void setRespiratory_rate(int respiratory_rate) {
        this.respiratory_rate = respiratory_rate;
    }

    public boolean isIs_independently() {
        return is_independently;
    }

    public void setIs_independently(boolean is_independently) {
        this.is_independently = is_independently;
    }
}
