package com.boots.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class BodyPart {

    @Id
    private String id;
    private String name;
    @OneToMany
    public List<Symptom> symptoms = new ArrayList<>();

    public void addSymptom(Symptom symptom) {
        this.symptoms.add(symptom);
    }
}
