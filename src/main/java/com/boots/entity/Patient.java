package com.boots.entity;

import javax.persistence.*;
import java.util.*;

import static java.util.Comparator.nullsFirst;

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

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Direction> directions = new ArrayList <>();

    public Patient() {}


    public List<User> getUsers(List<User> allUsers, int id) {
        List<User> patientUsers = new ArrayList<>();

        for(User user:allUsers) {
            Optional<Role> c_role =  user.getRoles().stream().findFirst();
            if (c_role.orElse(new Role()).getId()==id && !user.isSelected())
                patientUsers.add(user);
        }
        return patientUsers;
    }

    public List<Visit> getVisits() {
        visits.sort(Collections.reverseOrder(Comparator.comparing(Visit::getDate)));
        return visits;
    }

    public List<Visit> getDoneVisits(int i) {
        List<Visit> done = new ArrayList<>();
        for (Visit visit : this.visits)
            if(visit.getStatus())
                done.add(visit);

            switch (i) {
                case 1: {
                    done.sort(Collections.reverseOrder(Comparator.comparing(Visit::getDate)));
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
                    // done.sort(nullsFirst(Comparator.comparing(Visit::getMedicine)));
                    done.sort( Comparator.comparing(Visit::getMedicine, Comparator.nullsLast(Comparator.reverseOrder()))
                            .thenComparing(Visit::getMedicine));

                    break;
                }

            }

        return done;
    }

    public List<Visit> getActiveVisits() {
        List<Visit> active = new ArrayList<>();
        for (Visit visit : this.visits)
            if(!visit.getStatus())
                active.add(visit);
        active.sort(Collections.reverseOrder(Comparator.comparing(Visit::getDate)));
        return active;
    }

    public boolean findActiveVisitBySpecialization(Specialization specialization) {
        for (Visit visit : this.visits)
            if((!visit.getStatus()) && visit.getDoctor().getSpecialization().getName().equals(specialization.getName()))
                return true;
        return false;
    }

    public void setVisits(List<Visit> visits) {

        this.visits = visits;
    }

    public void addVisit(Visit visit) {

        visit.setPatient(this);
        this.visits.add(visit);
    }


    public List<Direction> getDirections() {

        return directions;
    }

    public List<Direction> getActiveDirections() {
        List<Direction> active = new ArrayList<>();
        for (Direction direction : this.directions)
            if(direction.getStatus())
                active.add(direction);
        return active;
    }


    public void setDirections(List<Direction> directions) {

        this.directions = directions;
    }

    public void addDirection(Direction direction1) {
        boolean canAdd=true;

        if (this.directions.isEmpty()) {
            direction1.setPatient(this);
            this.directions.add(direction1);
        }

        else {
            for (Direction direction : this.directions)
                if (direction.getStatus() && direction.getSpecialization().getName().equals(direction1.getSpecialization().getName())) {
                    canAdd = false;
                    break;
                }

            if(canAdd) {
                direction1.setPatient(this);
                this.directions.add(direction1);
            }
        }
    }

    public Direction findActiveDirection(Specialization specialization) {

        for (Direction direction :  this.directions)
            if (direction.getStatus() && direction.getSpecialization().getName().equals(specialization.getName()))
                return direction;

        return null;
    }

    public Direction findNotActiveDirection(Specialization specialization) {

        for (Direction direction :  this.directions)
            if (!direction.getStatus() && direction.getSpecialization().getName().equals(specialization.getName()))
                return direction;

        return null;
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
