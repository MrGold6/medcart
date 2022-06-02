package com.boots.controller;

import com.boots.entity.*;
import com.boots.transientClasses.Analysis;
import com.boots.transientClasses.DirectionToHospital;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/doctor2")

@SessionAttributes(value = {"analysis_JSP", "directionToHospital_JSP"})
public class SpecialistDoctorController extends DoctorController {

    //analysis
    @RequestMapping(value = "/{id_visit}/choose_action_analysis", method = RequestMethod.GET)
    public ModelAndView choose_actionPageAnalysis(@ModelAttribute("message") String message,
                                                  @PathVariable("id_visit") String id_visit) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("id_visit", id_visit);
        modelAndView.setViewName("doctor/specialistDoctor/form/choose_form/choose_action_analysis");
        return modelAndView;
    }

    @RequestMapping(value = "/{id_visit}/add_analysis", method = RequestMethod.GET)
    public ModelAndView addPageAnalysis(@ModelAttribute("message") String message,
                                        @PathVariable("id_visit") String id_visit) {

        Patient patient = patientService.getById(getIdPatientSplit(id_visit));
        Visit visit = patient.findVisit(id_visit);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("id_visit", id_visit);
        modelAndView.addObject("visit", visit);
        modelAndView.addObject("analysis_JSP", new Analysis());
        modelAndView.setViewName("doctor/specialistDoctor/form/create_form/new_analysis");

        return modelAndView;
    }

    @ModelAttribute("analysis_JSP")
    public Analysis createAnalysis() {
        return new Analysis();
    }

    @RequestMapping(value = "/add_analysis", method = RequestMethod.POST)
    public ModelAndView addAnalysis(@ModelAttribute("analysis_JSP") Analysis analysis,
                                    @ModelAttribute("id_visit") String id_visit) {
        Patient patient = patientService.getById(getIdPatientSplit(id_visit));
        Visit visit = patient.findVisit(id_visit);
        analysis.setNumber(patient.getCount_of_directionAnalysis() + 1);
        analysis.setVisit(visit);

        patient.setCount_of_directionAnalysis(patient.getCount_of_directionAnalysis() + 1);
        patientService.add(patient);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/doctor2/" + id_visit + "/analysis");

        return modelAndView;
    }

    @RequestMapping(value = "/{id_visit}/analysis", method = RequestMethod.GET)
    public ModelAndView AnalysisPage(@ModelAttribute("message") String message,
                                     @ModelAttribute("analysis_JSP") Analysis analysis,
                                     @PathVariable("id_visit") String id_visit) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("id_visit", id_visit);
        modelAndView.addObject("analysis_JSP", analysis);
        modelAndView.setViewName("doctor/specialistDoctor/document/analysis");
        return modelAndView;
    }

    //direction to hospital
    @RequestMapping(value = "/{id_visit}/choose_action_directionToHospital", method = RequestMethod.GET)
    public ModelAndView choose_actionPageDirectionToHospital(@ModelAttribute("message") String message,
                                                             @PathVariable("id_visit") String id_visit) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("id_visit", id_visit);
        modelAndView.setViewName("doctor/specialistDoctor/form/choose_form/choose_action_directionToHospital");
        return modelAndView;
    }

    @RequestMapping(value = "/{id_visit}/add_directionToHospital", method = RequestMethod.GET)
    public ModelAndView addPageDirectionToHospital(@ModelAttribute("message") String message,
                                                   @PathVariable("id_visit") String id_visit) {

        Patient patient = patientService.getById(getIdPatientSplit(id_visit));
        Visit visit = patient.findVisit(id_visit);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("id_visit", id_visit);
        modelAndView.addObject("visit", visit);
        modelAndView.addObject("directionToHospital_JSP", new DirectionToHospital());
        modelAndView.setViewName("doctor/specialistDoctor/form/create_form/new_directionToHospital");

        return modelAndView;
    }

    @ModelAttribute("directionToHospital_JSP")
    public DirectionToHospital createDirectionToHospital() {
        return new DirectionToHospital();
    }

    @RequestMapping(value = "/add_directionToHospital", method = RequestMethod.POST)
    public ModelAndView addDirectionToHospital(@ModelAttribute("directionToHospital_JSP") DirectionToHospital directionToHospital,
                                               @ModelAttribute("id_visit") String id_visit,
                                               @ModelAttribute("typeHospital") boolean typeHospital,
                                               @ModelAttribute("is_hepatitis") boolean is_hepatitis,
                                               @ModelAttribute("is_independently") boolean is_independently) {
        Patient patient = patientService.getById(getIdPatientSplit(id_visit));
        Visit visit = patient.findVisit(id_visit);
        directionToHospital.setNumber(patient.getCount_of_directionToHospital() + 1);
        directionToHospital.setVisit(visit);
        directionToHospital.setTypeHospital(typeHospital);
        directionToHospital.set_hepatitis(is_hepatitis);
        directionToHospital.set_independently(is_independently);

        patient.setCount_of_directionToHospital(patient.getCount_of_directionToHospital() + 1);
        patientService.add(patient);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/doctor2/" + id_visit + "/directionToHospital");

        return modelAndView;
    }

    @RequestMapping(value = "/{id_visit}/directionToHospital", method = RequestMethod.GET)
    public ModelAndView DirectionToHospitalPage(@ModelAttribute("message") String message,
                                                @ModelAttribute("directionToHospital_JSP") DirectionToHospital directionToHospital,
                                                @PathVariable("id_visit") String id_visit) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("id_visit", id_visit);
        modelAndView.addObject("directionToHospital", directionToHospital);
        modelAndView.setViewName("doctor/specialistDoctor/document/directionToHospital");
        return modelAndView;
    }


}
