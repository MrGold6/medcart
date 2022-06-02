package com.boots.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import java.sql.Date;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "human")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Human {

    @Id
    protected Long RNTRC;
    protected String name;
    protected String surname;
    protected String middle_name;
    protected int telephone_number;
    protected Date date_of_birth;
    protected int sex;
    protected String address;
    protected String email;

}

