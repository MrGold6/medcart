package com.boots.controller;

import com.boots.entity.Doctor;
import com.boots.entity.Patient;
import com.boots.service.DoctorService;
import com.boots.service.PatientService;
import com.boots.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    protected PatientService patientService;

    @Autowired
    public void setPatientService(PatientService patientService) {
        this.patientService = patientService;
    }


    @RequestMapping(value = "/patients", method = RequestMethod.GET)
    public ModelAndView allPatientsPage() {
        List<Patient> patients = patientService.allPatients();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("patientsList", patients);
        modelAndView.setViewName("admin/tables/patients");
        return modelAndView;
    }

    @RequestMapping(value = "/add_patient", method = RequestMethod.GET)
    public ModelAndView addPagePatient(@ModelAttribute("message") String message) {
        Patient patient = new Patient();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("patient", patient);

        if(message.equals("y"))
            modelAndView.addObject("message", message);
        else
            modelAndView.addObject("message", null);

        modelAndView.setViewName("admin/forms/form_patients");
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
            modelAndView.setViewName("redirect:/admin/patients");

        } else {
            modelAndView.addObject("message","y");
            modelAndView.setViewName("redirect:/admin/add_patient");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/{id_patient}/edit_patient", method = RequestMethod.GET)
    public ModelAndView editPagePatient(@PathVariable("id_patient") Long id_patient,
                                        @ModelAttribute("message") String message) {
        Patient patient = patientService.getById(id_patient);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/forms/form_patients");
        modelAndView.addObject("patient", patient);
        modelAndView.addObject("id_patient", id_patient);
        return modelAndView;
    }

    @RequestMapping(value = "/add_patient", method = RequestMethod.POST, params = "edit")
    public ModelAndView editPatient(@ModelAttribute("patient") Patient patient,
                                    @ModelAttribute("sex") String sex,
                                    @ModelAttribute("Blood_type") String Blood_type,
                                    @ModelAttribute("rh") String rh) {
        patient.setSex(Integer.parseInt(sex));
        patient.setBlood_type(Integer.parseInt(Blood_type));
        patient.setRh(rh);
        patientService.add(patient);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/patients");

        return modelAndView;
    }

//deletePatient
@RequestMapping(value = "/{id_patient}/delete_patient", method = RequestMethod.GET)
public ModelAndView deletePatient(@PathVariable("id_patient") Long id_patient) {
    patientService.deletePatient(id_patient);

    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("redirect:/admin/patients");

    return modelAndView;
}

    /*
    @GetMapping("/admin")
    public String userList(Model model) {
        model.addAttribute("allUsers", userService.allUsers());
        return "admin";
    }

    @PostMapping("/admin")
    public String  deleteUser(@RequestParam(required = true, defaultValue = "" ) Long userId,
                              @RequestParam(required = true, defaultValue = "" ) String action,
                              Model model) {
        if (action.equals("delete")){
            userService.deleteUser(userId);
        }
        return "redirect:/admin";
    }

    @GetMapping("/admin/gt/{userId}")
    public String  gtUser(@PathVariable("userId") Long userId, Model model) {
        model.addAttribute("allUsers", userService.usergtList(userId));
        return "admin";
    }*/
}
