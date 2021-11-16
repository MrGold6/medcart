package com.boots.entity;

import javax.persistence.*;

@Entity
@Table(name = "disease")
public class Disease {

    @Id
    @Column(name = "ICD_10")
    private String ICD_10;

    @Column(name = "name")
    private String name;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getICD_10() {
        return ICD_10;
    }

    public void setICD_10(String ICD_10) {
        this.ICD_10 = ICD_10;
    }

}
