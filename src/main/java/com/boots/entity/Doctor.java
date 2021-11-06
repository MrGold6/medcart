package com.boots.entity;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


@Entity
@Table(name = "doctor")

public class Doctor extends Human {

    @Column(name = "specialization")
    private String specialization;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    // другой правильній варіант https://stackoverflow.com/questions/22821695/how-to-fix-hibernate-lazyinitializationexception-failed-to-lazily-initialize-a
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true, fetch=FetchType.EAGER)
    public List<Visit> visits = new ArrayList<>();


    public List<Visit> getVisits() {
        return visits;
    }

    public void addVisit(Visit visit) {
        visit.setDoctor(this);
        this.visits.add(visit);
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
