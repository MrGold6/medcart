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
@RequestMapping("/doctor2")

public class SpecialistDoctorController extends DoctorController{

    @RequestMapping(value = "/hi", method = RequestMethod.GET)
    public ModelAndView addjmn(@ModelAttribute("message") String message) {
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


}
