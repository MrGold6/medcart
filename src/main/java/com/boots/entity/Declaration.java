package com.boots.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import java.sql.Date;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Declaration {
    @Id
    private String id;
    private Date date;
    private Boolean consent;
    private String document_identifier;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "electronic_card", referencedColumnName = "RNTRC")
    private Patient patient;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor", referencedColumnName = "RNTRC")
    private Doctor doctor_dec;

}
