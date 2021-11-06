package com.boots.controller;

import com.boots.entity.Doctor;
import com.boots.entity.Patient;
import com.boots.entity.User;
import com.boots.entity.Visit;
import com.boots.service.DoctorService;
import com.boots.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import java.util.List;
@RestController
@RequestMapping("/patient")
@PreAuthorize("hasRole('ROLE_PATIENT')")
public class PatientController{
    protected PatientService patientService;

    @Autowired
    public void setPatientService(PatientService patientService) {
        this.patientService = patientService;
    }

    //patient
    public Patient getAuthPatient()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();
        return patientService.patientByUser(user);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView allVisits() {

        Patient patient = getAuthPatient();

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("patient", patient);
        modelAndView.addObject("visitsList", patient.getVisits());
        modelAndView.setViewName("patient/pages/visits");

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
}
