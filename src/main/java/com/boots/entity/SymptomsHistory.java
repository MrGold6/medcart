package com.boots.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

import java.sql.Date;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class SymptomsHistory {

    @Id
    private String id;
    private Date date;
    private String symptoms;
    private String notes;
}
