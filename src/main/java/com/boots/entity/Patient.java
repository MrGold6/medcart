package com.boots.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;
import java.sql.Date;
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
    private int count_of_recipe;

    @Column(name = "Count_of_sick_leave")
    private int count_of_sick_leave;

    @Column(name = "Count_of_directionAnalysis")
    private int count_of_directionAnalysis;

    @Column(name = "Count_of_directionToHospital")
    private int count_of_directionToHospital;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Visit> visits = new ArrayList <>();

    @OneToMany(mappedBy = "patient")
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
                    done.sort(Comparator.comparing(Visit::getDate).thenComparing(o -> o.getSchedule().getTime()).reversed());
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
        active.sort(Comparator.comparing(Visit::getDate).thenComparing(o -> o.getSchedule().getTime()));
        return active;
    }

    public List<Visit> expiredVisits() {
        List<Visit> expired = this.getActiveVisits();
        for (Visit visit : this.visits)
          if (visit.getDate().equals(Date.valueOf(LocalDate.now().toString())) || visit.getDate().after(Date.valueOf(LocalDate.now().toString())))
                expired.remove(visit);
        return expired;
    }

    public void removeExpiredVisits() {
        List<Visit> expired = this.expiredVisits();
        for (Visit visit : expired)
            this.visits.remove(visit);
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
    public Visit findVisit(String id)
    {
        for (Visit visit : visits)
            if (visit.getNumber().equals(id))
                return visit;

        return null;
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

    public  boolean isDirectionExists(String specialization)
    {
        List<Direction> active = this.getActiveDirections();
        for (Direction direction : active)
            if(direction.getStatus() && direction.getSpecialization().getName().equals(specialization))
                return false;

        return true;
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



    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getCount_of_directionAnalysis() {
        return count_of_directionAnalysis;
    }

    public void setCount_of_directionAnalysis(int count_of_directionAnalysis) {
        this.count_of_directionAnalysis = count_of_directionAnalysis;
    }

    public int getCount_of_directionToHospital() {
        return count_of_directionToHospital;
    }

    public void setCount_of_directionToHospital(int count_of_directionToHospital) {
        this.count_of_directionToHospital = count_of_directionToHospital;
    }

    public int getCount_of_recipe() {
        return count_of_recipe;
    }

    public void setCount_of_recipe(int count_of_recipe) {
        this.count_of_recipe = count_of_recipe;
    }

    public int getCount_of_sick_leave() {
        return count_of_sick_leave;
    }

    public void setCount_of_sick_leave(int count_of_sick_leave) {
        this.count_of_sick_leave = count_of_sick_leave;
    }

    public String getDateToString()
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.date_of_birth);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int year = cal.get(Calendar.YEAR);
        return day+"."+month+"."+year;
    }

    /*
    public List<Visit> getActiveDirection() {
        List<Visit> active = new ArrayList<>();
        for (Visit visit : this.visits)
            if(!visit.getStatus())
                active.add(visit);
        active.sort(Collections.reverseOrder(Comparator.comparing(Visit::getDate)));
        return active;
    }*/

}
