package com.boots.controller;

import com.boots.entity.*;
import com.boots.service.DoctorService;
import com.boots.service.PatientService;
import com.boots.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import javax.xml.crypto.Data;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/patient")
@PreAuthorize("hasRole('ROLE_PATIENT')")
public class PatientController{
    protected PatientService patientService;

    protected DoctorService doctorService;

    @Autowired
    private UserService userService;


    @Autowired
    public void setPatientService(PatientService patientService) {
        this.patientService = patientService;
    }

    @Autowired
    public void setDoctorService(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    //patient
    public Patient getAuthPatient()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();
        return patientService.patientByUser(user);
    }

    @RequestMapping(value = "/{sort_int}", method = RequestMethod.GET)
    public ModelAndView allDoneVisits(@PathVariable("sort_int") int sort_int) {

        Patient patient = getAuthPatient();

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("patient", patient);
        modelAndView.addObject("visitsList", patient.getDoneVisits(sort_int));

        modelAndView.setViewName("patient/pages/visits");

        return modelAndView;
    }

    @RequestMapping(value = "/allActiveVisits", method = RequestMethod.GET)
    public ModelAndView allActiveVisits() {

        Patient patient = getAuthPatient();

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("patient", patient);
        modelAndView.addObject("visitsList", patient.getActiveVisits());
        modelAndView.setViewName("patient/pages/active_visits");

        return modelAndView;
    }

    @RequestMapping(value = "/directions", method = RequestMethod.GET)
    public ModelAndView allDirection() {

        Patient patient = getAuthPatient();

        ModelAndView modelAndView = new ModelAndView();

        boolean exist =patient.findActiveVisitBySpecialization(doctorService.getByIdSpecialization(1));

        modelAndView.addObject("exist", exist);
        modelAndView.addObject("patient", patient);
        modelAndView.addObject("directionsList", patient.getActiveDirections());
        modelAndView.setViewName("patient/pages/directions");

        return modelAndView;
    }

    @RequestMapping(value = "/{id_doctor}/schedule", method = RequestMethod.GET)
    public ModelAndView PageSchedule(@PathVariable("id_doctor") Long id){

        ModelAndView modelAndView = new ModelAndView();
        Doctor doctor = doctorService.getById(id);
        modelAndView.addObject("doctor", doctor);

        modelAndView.addObject("date", LocalDate.now().toString());

        modelAndView.addObject("schedules", doctor.freeSchedule(LocalDate.now()));

        modelAndView.setViewName("patient/pages/schedule");

        return modelAndView;
    }

    @RequestMapping(value = "/{id_doctor}/schedulesByDay", method = RequestMethod.GET)
    public ModelAndView schedulesByDay(@ModelAttribute("date1") String date,
                                    @PathVariable("id_doctor") Long id){

        ModelAndView modelAndView = new ModelAndView();
        Doctor doctor = doctorService.getById(id);
        modelAndView.addObject("date", date);
        modelAndView.addObject("doctor", doctor);

        modelAndView.addObject("schedules", doctor.freeSchedule(Date.valueOf(date).toLocalDate()));

        modelAndView.setViewName("patient/pages/schedule");

        return modelAndView;
    }

    @RequestMapping(value = "/{id_doctor}/add_visit/{date1}/{id_schedule}", method = RequestMethod.GET)
    public ModelAndView addPageVisit(@PathVariable("id_doctor") Long id,
                                     @PathVariable("date1") String date,
                                     @PathVariable("id_schedule") int id_schedule){

        ModelAndView modelAndView = new ModelAndView();
        Doctor doctor = doctorService.getById(id);
        modelAndView.addObject("visit", new Visit());
        modelAndView.addObject("doctor", doctor);
        modelAndView.addObject("date", date);
        modelAndView.addObject("schedule", doctor.findSchedule(id_schedule));

        modelAndView.setViewName("patient/pages/new_visit");

        return modelAndView;
    }


    @RequestMapping(value = "/add_visit_act", method = RequestMethod.POST)
    public ModelAndView addVisit(@ModelAttribute("visit") Visit visit,
                                 @ModelAttribute("id_doctor") Long id,
                                 @ModelAttribute("id_schedule") int id_schedule) {
        Patient patient = getAuthPatient();
        Doctor doctor = doctorService.getById(id);

        String id_visit = (patient.getVisits().size() + 1) + "_" + patient.getRNTRC();
        visit.setNumber(id_visit);
        visit.setStatus(false);
        visit.setPatient(patient);
        visit.setDoctor(doctor);
        visit.setSchedule(doctor.findSchedule(id_schedule));

        if (!Objects.equals(doctor.getSpecialization().getName(), doctorService.getByIdSpecialization(1).getName()))
            patient.findActiveDirection(doctor.getSpecialization()).setStatus(false);

        doctor.addVisit(visit);

        patientService.add(patient);
        doctorService.add(doctor);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/patient/directions");

        return modelAndView;
    }

    @RequestMapping(value = "/deleteVisit/{id_visit}", method = RequestMethod.GET)
    public ModelAndView deleteVisit(@PathVariable("id_visit") String id_visit) {
        Patient patient = getAuthPatient();
        Visit visit =patient.findVisit(id_visit);
        Doctor doctor = visit.getDoctor();

        if (!Objects.equals(doctor.getSpecialization().getName(), doctorService.getByIdSpecialization(1).getName()))
            patient.findNotActiveDirection(doctor.getSpecialization()).setStatus(true);

        patientService.deleteVisit(id_visit);
        patientService.add(patient);


        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/patient/directions");

        return modelAndView;
    }

    @RequestMapping(value = "/doctors", method = RequestMethod.GET)
    public ModelAndView allDoctors() {

        Patient patient = getAuthPatient();
        List<Doctor> doctors = doctorService.allDoctors();

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("patient", patient);
        modelAndView.addObject("doctorsList", doctors);
        modelAndView.setViewName("patient/pages/doctors");

        return modelAndView;
    }

    @RequestMapping(value = "/{specialization}/doctorsBySpecialization", method = RequestMethod.GET)
    public ModelAndView allDoctorsBySpecialization( @PathVariable("specialization") int specialization) {

        Patient patient = getAuthPatient();
        List<Doctor> doctors = doctorService.doctorBySpecialization(doctorService.getByIdSpecialization(specialization));

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("patient", patient);
        modelAndView.addObject("doctorsList", doctors);

        modelAndView.setViewName("patient/pages/doctors");

        return modelAndView;
    }

    @RequestMapping(value = "/{id_visit}/visit", method = RequestMethod.GET)
    public ModelAndView PageVisit(@ModelAttribute("message") String message,
                                  @PathVariable("id_visit") String id_visit) {
       Patient patient = getAuthPatient();
        Visit visit = patient.findVisit(id_visit);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("visit", visit);
        modelAndView.addObject("id_patient", patient.getRNTRC());
        modelAndView.setViewName("patient/pages/visit");

        return modelAndView;
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ModelAndView pagePatient() {
        Patient patient = getAuthPatient();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("patient", patient);
        modelAndView.setViewName("patient/pages/profile");

        return modelAndView;
    }

    @RequestMapping(value = "/edit_patient", method = RequestMethod.POST)
    public ModelAndView editPatient(@ModelAttribute("patient") Patient patient,
                                    @ModelAttribute("sex") String sex,
                                    @ModelAttribute("id_visit") String id_visit,
                                    @ModelAttribute("user_id") String user_id) {
        patient.setSex(Integer.parseInt(sex));

        if(!user_id.isEmpty())
            patient.setUser(userService.findUserById(user_id));
        patientService.add(patient);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/patient/profile");

        return modelAndView;
    }
}
