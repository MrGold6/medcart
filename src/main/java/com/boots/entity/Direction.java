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
import javax.persistence.Table;


@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "direction")
public class Direction {

    @Id
    private String number;
    private Boolean status;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "specialization", referencedColumnName = "id")
    private Specialization specialization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "electronic_card", referencedColumnName = "RNTRC")
    private Patient patient;

}
