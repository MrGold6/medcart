package com.boots.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "human")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Human {

    @Id
    @Column(name = "RNTRC")
    protected Long RNTRC;

    @Column(name = "name")
    protected String name;

    @Column(name = "surname")
    protected String surname;

    @Column(name = "middle_name")
    protected String middle_name;

    @Column(name = "telephone_number")
    protected int telephone_number;

    @Column(name = "date_of_birth")
    protected Date date_of_birth;

    @Column(name = "sex")
    protected int sex;

    @Column(name = "address")
    protected String address;

    @Column(name = "email")
    protected String email;



    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getRNTRC() {
        return RNTRC;
    }

    public void setRNTRC(Long RNTRC) {
        this.RNTRC = RNTRC;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getTelephone_number() {
        return telephone_number;
    }

    public void setTelephone_number(int telephone_number) {
        this.telephone_number = telephone_number;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public Date getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(Date date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public Human() {

    }
}

