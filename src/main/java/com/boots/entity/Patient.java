package com.boots.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Entity
@Table(name = "electronic_card")

public class Patient extends Human {
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "Blood_type")
    private int blood_type;

    @Column(name = "Chronic_disease")
    private String chronic_disease;

    @Column(name = "Allergic_history")
    private String allergic_history;

    @Column(name = "Rh")
    private String Rh;


    @Column(name = "Count_of_recipe")
    private int Count_of_recipe;

    @Column(name = "Count_of_sick_leave")
    private int Count_of_sick_leave;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Visit> visits = new ArrayList <>();

    public Patient() {}



    public List<Visit> getVisits() {

        visits.sort(Collections.reverseOrder(Comparator.comparing(Visit::getDate)));
        return visits;
    }

    public void setVisits(List<Visit> visits) {

        this.visits = visits;
    }

    public void addVisit(Visit visit) {

        visit.setPatient(this);
        this.visits.add(visit);
    }

    public Visit findVisit(String id)
    {
        for (Visit visit : visits)
            if (visit.getNumber().equals(id))
                return visit;

        return null;
    }

    public int getBlood_type() {
        return blood_type;
    }

    public void setBlood_type(int blood_type) {
        this.blood_type = blood_type;
    }

    public String getChronic_disease() {
        return chronic_disease;
    }

    public void setChronic_disease(String chronic_disease) {
        this.chronic_disease = chronic_disease;
    }

    public String getAllergic_history() {
        return allergic_history;
    }

    public void setAllergic_history(String allergic_history) {
        this.allergic_history = allergic_history;
    }

    public String getRh() {
        return Rh;
    }

    public void setRh(String rh) {
        Rh = rh;
    }


    public int getCount_of_recipe() {
        return Count_of_recipe;
    }

    public void setCount_of_recipe(int count_of_recipe) {
        Count_of_recipe = count_of_recipe;
    }

    public int getCount_of_sick_leave() {
        return Count_of_sick_leave;
    }

    public void setCount_of_sick_leave(int count_of_sick_leave) {
        Count_of_sick_leave = count_of_sick_leave;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
