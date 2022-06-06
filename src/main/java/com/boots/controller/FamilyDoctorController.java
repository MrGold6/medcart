package com.boots.controller;

import com.boots.entity.Direction;
import com.boots.entity.Doctor;
import com.boots.entity.Patient;
import com.boots.entity.Specialization;
import com.boots.entity.User;
import com.boots.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;

@RestController
@RequestMapping("/doctor1")
public class FamilyDoctorController extends DoctorController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/patients/{sort_num}", method = RequestMethod.GET)
    public ModelAndView allPatientsPage(@PathVariable("sort_num") int sort_num) {
        List<Patient> patients = patientService.allPatients();
        Doctor doctor = getAuthDoc();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("patientsList", sort.sortPatient(patients, sort_num));
        modelAndView.addObject("doctor", doctor);
        modelAndView.setViewName("doctor/familyDoctor/pages/patients");
        return modelAndView;
    }

    @RequestMapping(value = "/patients/searchTelephone_number", method = RequestMethod.GET)
    public ModelAndView allPatientsWithCurrentTelephone_number(@ModelAttribute("telephone_number") int telephone_number) {
        Doctor doctor = getAuthDoc();
        List<Patient> patients = patientService.findTelephone_number(telephone_number);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("patientsList", patients);
        modelAndView.addObject("doctor", doctor);
        modelAndView.setViewName("doctor/familyDoctor/pages/patients");
        return modelAndView;
    }

    @RequestMapping(value = "/add_patient", method = RequestMethod.GET)
    public ModelAndView addPagePatient(@ModelAttribute("message") String message) {
        Patient patient = new Patient();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("patient", patient);

        if (message.equals("y")) {
            modelAndView.addObject("message", message);
        } else {
            modelAndView.addObject("message", null);
        }

        modelAndView.setViewName("doctor/familyDoctor/form/create_form/new_patient");
        return modelAndView;
    }

    @RequestMapping(value = "/add_patient", method = RequestMethod.POST)
    public ModelAndView addPatient(@ModelAttribute("patient") Patient patient,
                                   @ModelAttribute("sex") String sex,
                                   @ModelAttribute("Blood_type") String Blood_type,
                                   @ModelAttribute("rh") String rh) {
        ModelAndView modelAndView = new ModelAndView();

        if (patientService.checkRNTRC(patient.getRNTRC()) && doctorService.checkRNTRC(patient.getRNTRC())) {
            patient.setSex(Integer.parseInt(sex));
            patient.setBlood_type(Integer.parseInt(Blood_type));
            patient.setRh(rh);
            patientService.add(patient);
            modelAndView.setViewName("redirect:/doctor1/" + patient.getRNTRC() + "/add_user");

        } else {
            modelAndView.addObject("message", "y");
            modelAndView.setViewName("redirect:/doctor1/add_patient");
        }
        return modelAndView;
    }

    @GetMapping("/{id_patient}/add_user")
    public ModelAndView addPageUser(@PathVariable("id_patient") Long id_patient,
                                    @ModelAttribute("message") String message) {
        ModelAndView modelAndView = new ModelAndView();

        if (message.equals("y")) {
            modelAndView.addObject("message", message);
        } else {
            modelAndView.addObject("message", null);
        }

        modelAndView.addObject("user", new User());
        modelAndView.addObject("id_patient", id_patient);
        modelAndView.setViewName("doctor/familyDoctor/form/create_form/new_user");

        return modelAndView;
    }

    @PostMapping("/add_user")
    public ModelAndView addUser(@ModelAttribute("user") @Valid User user,
                                @ModelAttribute("id_patient") Long id_patient) throws NoSuchAlgorithmException {

        ModelAndView modelAndView = new ModelAndView();

        user.setId(SecureRandom.getInstance("SHA1PRNG").nextInt() + "_" + user.getUsername());
        user.setSelected(true);

        if (!userService.saveUser(user, 3)) {
            modelAndView.addObject("message", "y");
            modelAndView.setViewName("doctor/familyDoctor/form/create_form/new_user");
        } else {
            Patient patient = patientService.getById(id_patient);
            patient.setUser(user);
            patientService.add(patient);
            modelAndView.setViewName("redirect:/doctor1/patients/1");
        }


        return modelAndView;
    }

    //direction
    @RequestMapping(value = "/{id_visit}/choose_action_direction", method = RequestMethod.GET)
    public ModelAndView choose_actionPageDirection(@ModelAttribute("message") String message,
                                                   @PathVariable("id_visit") String id_visit) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("id_visit", id_visit);
        modelAndView.setViewName("doctor/familyDoctor/form/choose_form/choose_action_direction");
        return modelAndView;
    }

    @RequestMapping(value = "/{id_visit}/add_new_direction", method = RequestMethod.GET)
    public ModelAndView addPageDirection(@ModelAttribute("message") String message,
                                         @PathVariable("id_visit") String id_visit) {
        List<Specialization> specializationsList = doctorService.allSpecializations();
        specializationsList.remove(doctorService.getByIdSpecialization(8));

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("id_visit", id_visit);
        modelAndView.addObject("specializationsList", specializationsList);
        modelAndView.addObject("direction", new Direction());
        modelAndView.setViewName("doctor/familyDoctor/form/create_form/direction");

        return modelAndView;
    }

    @RequestMapping(value = "/add_direction", method = RequestMethod.POST)
    public ModelAndView addDirection(@ModelAttribute("selected_spec") int selected_spec,
                                     @ModelAttribute("direction") Direction direction,
                                     @ModelAttribute("id_visit") String id_visit) throws NoSuchAlgorithmException {
        Patient patient = patientService.getById(getIdPatientSplit(id_visit));
        if (patient.isDirectionExists(specializationService.getById(selected_spec).getName())) {
            direction.setNumber(String.valueOf(SecureRandom.getInstance("SHA1PRNG").nextInt()));
            direction.setSpecialization(specializationService.getById(selected_spec));
            direction.setStatus(true);
            direction.setPatient(patient);
            directionService.add(direction);
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/" + id_visit + "/choose_action_lab_direction");

        return modelAndView;
    }

}
