package com.boots.entity;

public class Medicine {
    private String name;
    private String dose;
    private int number_of_day;
    private String instruction;


    public Medicine() {
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber_of_day() {
        return number_of_day;
    }

    public void setNumber_of_day(int number_of_day) {
        this.number_of_day = number_of_day;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }
}
