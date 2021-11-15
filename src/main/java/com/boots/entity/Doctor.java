package com.boots.entity;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;


@Entity
@Table(name = "doctor")

public class Doctor extends Human {

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "specialization", referencedColumnName = "id")
    private Specialization specialization;


    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    // другой правильній варіант https://stackoverflow.com/questions/22821695/how-to-fix-hibernate-lazyinitializationexception-failed-to-lazily-initialize-a
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true, fetch=FetchType.EAGER)
    public List<Visit> visits = new ArrayList<>();

    @OneToMany(mappedBy = "doctor1", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Schedule> schedules;

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    public List<Schedule> getSchedulesByDay(int dayOfWeek) {

        List<Schedule> scheduleByDay = new ArrayList<>();

        for (Schedule schedule: this.getSchedules())
            if (schedule.getDay() == dayOfWeek)
                scheduleByDay.add(schedule);

        return scheduleByDay;
    }
    public Schedule findSchedule(int id)
    {
        for (Schedule schedule: this.getSchedules())
            if (schedule.getId()==id)
                return schedule;

        return null;
    }

    public List<Schedule> freeSchedule(LocalDate date)
    {
        Calendar c = Calendar.getInstance();
        //monday - 2
        c.setTime(Date.valueOf(date));
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        List<Schedule> freeSchedule =new ArrayList<>();
        if(date.equals(LocalDate.now()))
            freeSchedule = this.getSchedulesByDayAndNotAfterCurrentTime(dayOfWeek);
        else
            freeSchedule = this.getSchedulesByDay(dayOfWeek);

        for (Visit visit: this.getVisits())
        {
            for (Schedule schedule: this.getSchedules()) {
                if (!visit.getStatus() && visit.getSchedule().getId() == schedule.getId()&& visit.getDate().toLocalDate().equals(date)) {
                    freeSchedule.remove(schedule);
                    break;
                }
            }
        }

        return freeSchedule;
    }

    public List<Schedule> getSchedulesByDayAndNotAfterCurrentTime(int dayOfWeek) {

        List<Schedule> scheduleByDay = new ArrayList<>();

        for (Schedule schedule: this.getSchedules())
            if (schedule.getDay() == dayOfWeek && !LocalTime.now().isAfter(LocalTime.parse(schedule.getTime())))
                scheduleByDay.add(schedule);

        return scheduleByDay;
    }

    public List<Visit> getVisits() {
        return visits;
    }

    public void setVisits(List<Visit> visits) {
        this.visits= visits;
    }

    public void addVisit(Visit visit) {
        visit.setDoctor(this);
        this.visits.add(visit);
    }

    public List<Visit> getActiveVisitsByDay(LocalDate date)
    {
        List<Visit> visits =  new ArrayList <>();
        for (Visit visit : this.getVisits())
            if (!visit.getStatus() && visit.getDate().toLocalDate().equals(date))
                visits.add(visit);

        return visits;
    }

    public List<Visit> getDoneVisits()
    {
        List<Visit> visits =  new ArrayList <>();
        for (Visit visit : this.getVisits())
            if (visit.getStatus())
                visits.add(visit);

        return visits;
    }

    public List<Visit> getDoneVisitsByDay(LocalDate date)
    {
        List<Visit> visits =  new ArrayList <>();
        for (Visit visit : this.getVisits())
            if (visit.getStatus() && visit.getDate().toLocalDate().equals(date))
                visits.add(visit);

        return visits;
    }

    public List<Visit> getDoneVisitsByMonth(LocalDate date)
    {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(Date.valueOf(date));
        int month = c1.get(Calendar.MONTH);

        List<Visit> visits =  new ArrayList <>();
        for (Visit visit : this.getVisits()) {
            Calendar c = Calendar.getInstance();
            c.setTime(visit.getDate());
            if (visit.getStatus() && c.get(Calendar.MONTH) ==month)
                visits.add(visit);
        }

        return visits;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

}
