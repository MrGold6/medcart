package com.boots.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import java.time.LocalDate;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "electronic_card")
public class Patient extends Human {

    private int blood_type;
    private String chronic_disease;
    private String allergic_history;
    private String Rh;
    private double height;
    private double width;
    private double chest_circumference;
    private double abdominal_circumference;
    private int count_of_recipe;
    private int count_of_sick_leave;
    private int count_of_directionAnalysis;
    private int count_of_directionToHospital;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Visit> visits = new ArrayList<>();

    @OneToMany(mappedBy = "patient")
    public List<Direction> directions = new ArrayList<>();

    @OneToMany(mappedBy = "patient")
    public List<Test> tests = new ArrayList<>();

    @OneToMany(mappedBy = "patient")
    public List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "patient")
    public List<Rate> rates = new ArrayList<>();

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "declaration", referencedColumnName = "id")
    private Declaration declaration;

    @OneToMany
    public List<SymptomsHistory> symptomsHistories = new ArrayList<>();

    public void addRate(Rate rate) {
        rate.setPatient(this);
        this.rates.add(rate);
    }

    public Rate getRateByDoctor(Doctor doctor) {

        for (Rate rate : this.rates) {
           if(Objects.equals(rate.getD_rate(), doctor))
               return rate;
        }
        return null;
    }

    public List<User> getUsers(List<User> allUsers, int id) {
        List<User> patientUsers = new ArrayList<>();

        for (User user : allUsers) {
            Optional<Role> c_role = user.getRoles().stream().findFirst();
            if (c_role.orElse(new Role()).getId() == id && !user.isSelected()) {
                patientUsers.add(user);
            }
        }
        return patientUsers;
    }

    public List<Visit> getVisits() {
        visits.sort(Collections.reverseOrder(Comparator.comparing(Visit::getDate)));
        return visits;
    }

    public List<Specialization> getSpecializationDoneVisits() {
        List<Specialization> specializations = new ArrayList<>();
        for (Visit visit : this.getDoneVisits(1)) {

            if (!specializations.contains(visit.getDoctor().getSpecialization())) {
                specializations.add(visit.getDoctor().getSpecialization());
            }

        }
        return specializations;

    }

    public List<Disease> getDiseaseDoneVisits() {
        List<Disease> diseases = new ArrayList<>();
        for (Visit visit : this.getDoneVisits(1)) {

            if (!diseases.contains(visit.getDisease())) {
                diseases.add(visit.getDisease());
            }

        }
        return diseases;
    }


    public List<Visit> getDoneVisits(int i) {
        List<Visit> done = new ArrayList<>();
        for (Visit visit : this.visits) {
            if (visit.getStatus()) {
                done.add(visit);
            }

        }

        switch (i) {
            case 1: {
                try {
                    done.sort(Comparator.comparing(Visit::getDate).thenComparing(o -> o.getSchedule().getTime()).reversed());
                } catch (Exception e) {
                    done.sort(Comparator.comparing(Visit::getDate).reversed());
                }
                break;
            }
            case 2: {
                done.sort(Comparator.comparing(o -> o.getDisease().getName()));
                break;
            }
            case 3: {
                done.sort(Comparator.comparing(o -> o.getDoctor().getSpecialization().getName()));
                break;
            }

            case 4: {
                done.sort(Comparator.comparing(Visit::getMedicine, Comparator.nullsLast(Comparator.reverseOrder()))
                        .thenComparing(Visit::getMedicine));

                break;
            }

        }

        return done;
    }

    public List<Visit> getActiveVisits() {
        List<Visit> active = new ArrayList<>();
        for (Visit visit : this.visits) {
            if (!visit.getStatus()) {
                active.add(visit);
            }
        }
        active.sort(Comparator.comparing(Visit::getDate).thenComparing(o -> o.getSchedule().getTime()));
        return active;
    }

    public List<Visit> expiredVisits() {
        List<Visit> expired = this.getActiveVisits();
        for (Visit visit : this.visits) {
            if (visit.getDate().equals(Date.valueOf(LocalDate.now().toString())) || visit.getDate().after(Date.valueOf(LocalDate.now().toString()))) {
                expired.remove(visit);
            }
        }
        return expired;
    }

    public void removeExpiredVisits() {
        List<Visit> expired = this.expiredVisits();
        for (Visit visit : expired) {
            this.visits.remove(visit);
        }
    }

    public boolean findActiveVisitBySpecialization(Specialization specialization) {
        for (Visit visit : this.visits) {
            if ((!visit.getStatus()) && visit.getDoctor().getSpecialization().getName().equals(specialization.getName())) {
                return true;
            }
        }
        return false;
    }

    public void addComment(Comment comment) {
        comment.setPatient(this);
        this.comments.add(comment);
    }


    public void addVisit(Visit visit) {

        visit.setPatient(this);
        this.visits.add(visit);
    }

    public void addTest(Test test) {

        test.setPatient(this);
        this.tests.add(test);
    }

    public Visit findVisit(String id) {
        for (Visit visit : visits) {
            if (visit.getNumber().equals(id)) {
                return visit;
            }
        }

        return null;
    }

    public Test findTest(String id) {
        for (Test test : tests) {
            if (test.getId().equals(id)) {
                return test;
            }
        }

        return null;
    }

    public SymptomsHistory findSymptom(String id) {
        for (SymptomsHistory record : symptomsHistories) {
            if (record.getId().equals(id)) {
                return record;
            }
        }

        return null;
    }

    public List<Direction> getDirections() {

        return directions;
    }

    public List<Direction> getActiveDirectionsWithoutTests() {
        List<Direction> active = new ArrayList<>();
        for (Direction direction : this.directions) {
            if (direction.getSpecialization().getId() != 8) {
                if (direction.getStatus()) {
                    active.add(direction);
                }
            }

        }
        return active;
    }

    public List<Direction> getActiveDirectionsWithTests() {
        List<Direction> active = new ArrayList<>();
        for (Direction direction : this.directions) {
            if (direction.getSpecialization().getId() == 8) {
                if (direction.getStatus()) {
                    active.add(direction);
                }
            }

        }
        return active;
    }

    public boolean isDirectionExists(String specialization) {
        List<Direction> active = this.getActiveDirectionsWithoutTests();
        for (Direction direction : active) {
            if (direction.getStatus() && direction.getSpecialization().getName().equals(specialization)) {
                return false;
            }
        }

        return true;
    }

    public boolean isTestTypeExists(String testType) {
        List<Direction> active = this.getActiveDirectionsWithTests();
        for (Direction direction : active) {
            if (direction.getStatus() && direction.getTestsType().getName().equals(testType)) {
                return false;
            }
        }

        return true;
    }

    public void addDirection(Direction direction1) {
        boolean canAdd = true;

        if (this.directions.isEmpty()) {
            direction1.setPatient(this);
            this.directions.add(direction1);
        } else {
            for (Direction direction : this.directions) {
                if (direction.getStatus() && direction.getSpecialization().getName().equals(direction1.getSpecialization().getName())) {
                    canAdd = false;
                    break;
                }
            }

            if (canAdd) {
                direction1.setPatient(this);
                this.directions.add(direction1);
            }
        }
    }

    public Direction findActiveDirection(Specialization specialization) {

        for (Direction direction : this.directions) {
            if (direction.getStatus() && direction.getSpecialization().getName().equals(specialization.getName())) {
                return direction;
            }
        }

        return null;
    }

    public Direction findNotActiveDirection(Specialization specialization) {

        for (Direction direction : this.directions) {
            if (!direction.getStatus() && direction.getSpecialization().getName().equals(specialization.getName())) {
                return direction;
            }
        }

        return null;
    }

    public String getDateToString() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.date_of_birth);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int year = cal.get(Calendar.YEAR);
        return day + "." + month + "." + year;
    }


}
