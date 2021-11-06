package com.boots.controller;

import com.boots.entity.Patient;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/doctor1")
public class FamilyDoctorController extends DoctorController {

    @RequestMapping(value = "/add_patient", method = RequestMethod.GET)
    public ModelAndView addPagePatient(@ModelAttribute("message") String message) {
        Patient patient = new Patient();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("patient", patient);

        if(message.equals("y"))
            modelAndView.addObject("message", message);
        else
            modelAndView.addObject("message", null);

        modelAndView.setViewName("doctor/form/create_form/new_patient");
        return modelAndView;
    }

    @RequestMapping(value = "/add_patient", method = RequestMethod.POST)
    public ModelAndView addPatient(@ModelAttribute("patient") Patient patient,
                                   @ModelAttribute("sex") String sex,
                                   @ModelAttribute("Blood_type") String Blood_type,
                                   @ModelAttribute("rh") String rh) {
        ModelAndView modelAndView = new ModelAndView();

        if (patientService.checkRNTRC(patient.getRNTRC())) {
            patient.setSex(Integer.parseInt(sex));
            patient.setBlood_type(Integer.parseInt(Blood_type));
            patient.setRh(rh);
            patientService.add(patient);
            modelAndView.setViewName("redirect:/patients");

        } else {
            modelAndView.addObject("message","y");
            modelAndView.setViewName("redirect:/add_patient");
        }
        return modelAndView;
    }
}
