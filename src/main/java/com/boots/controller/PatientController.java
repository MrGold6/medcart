package com.boots.controller;

import com.boots.entity.*;
import com.boots.service.DirectionService;
import com.boots.service.DoctorService;
import com.boots.service.PatientService;
import com.boots.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

import static com.boots.transientClasses.ControllerMainTools.currentDate;
import static com.boots.transientClasses.ControllerMainTools.dateToString;
import static com.boots.transientClasses.ControllerMainTools.getIdPatientSplit;

@RestController
@RequestMapping("/patient")
@PreAuthorize("hasRole('ROLE_PATIENT')")
public class PatientController {

    protected PatientService patientService;

    protected DoctorService doctorService;

    @Autowired
    private UserService userService;

    @Autowired
    private DirectionService directionService;

    @Autowired
    public void setPatientService(PatientService patientService) {
        this.patientService = patientService;
    }

    @Autowired
    public void setDoctorService(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    public void deleteExpiredVisits(Patient patient) {
        for (Visit visit : patient.expiredVisits()) {
            if (!Objects.equals(visit.getDoctor().getSpecialization().getName(), doctorService.getByIdSpecialization(1).getName())) {
                patient.findNotActiveDirection(visit.getDoctor().getSpecialization()).setStatus(true);
            }
            patientService.deleteVisit(visit.getNumber());

        }
        patient.removeExpiredVisits();
    }


    //patient
    public Patient getAuthPatient() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return patientService.patientByUser(user);
    }


    @GetMapping("/getUserInfo")
    public ResponseEntity<?> userInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        return new ResponseEntity<>(user, HttpStatus.OK);
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
        deleteExpiredVisits(patient);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("patient", patient);
        modelAndView.addObject("visitsList", patient.getActiveVisits());

        modelAndView.setViewName("patient/pages/active_visits");

        return modelAndView;
    }

    //graph
    @GetMapping("/graph")
    public ModelAndView PageGraph() {
        Patient patient = getAuthPatient();

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("patient", patient);
        modelAndView.addObject("visitList", patient.getDoneVisits(1));
        modelAndView.addObject("diseaseList", patient.getDiseaseDoneVisits());
        modelAndView.addObject("specializationList", patient.getSpecializationDoneVisits());
        modelAndView.setViewName("patient/pages/graph");

        return modelAndView;
    }

    //direction

    @RequestMapping(value = "/directions", method = RequestMethod.GET)
    public ModelAndView allDirection() {

        Patient patient = getAuthPatient();

        ModelAndView modelAndView = new ModelAndView();

        boolean exist = patient.findActiveVisitBySpecialization(doctorService.getByIdSpecialization(1));
        //List<Direction> directions = directionService.getActiveBySpecializationAndPatient(doctor.getSpecialization(), patientService.getById(patient_id));

        modelAndView.addObject("exist", exist);
        modelAndView.addObject("patient", patient);
        modelAndView.addObject("declaration", patient.getDeclaration());
        modelAndView.addObject("directionsList", patient.getActiveDirectionsWithoutTests());
        modelAndView.setViewName("patient/pages/directions");

        return modelAndView;
    }

    //tests
    @GetMapping(value = "/tests")
    public ModelAndView allTestDirections() {
        Patient patient = getAuthPatient();
        List<Direction> directions = directionService.getActiveBySpecializationAndPatient(doctorService.getByIdSpecialization(8), patient);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("directionsList", directions);
        modelAndView.addObject("patient", patient);
        modelAndView.setViewName("patient/pages/test_directions");
        return modelAndView;
    }

    @GetMapping("/doneTests")
    public ModelAndView allTests() {
        Patient patient = getAuthPatient();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("testList", patient.getTests());
        modelAndView.addObject("patient", patient);
        modelAndView.setViewName("patient/pages/tests");
        return modelAndView;
    }

    @GetMapping("/test/{test_id}")
    public ModelAndView TestPage(@PathVariable("test_id") String test_id) {
        Patient patient = getAuthPatient();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("test", patient.findTest(test_id));
        modelAndView.setViewName("patient/pages/test");
        return modelAndView;
    }

    //symptoms
    @GetMapping(value = "/symptoms/{sort_int}")
    public ModelAndView allSymptoms(@PathVariable("sort_int") int sort_int) {
        Patient patient = getAuthPatient();

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("patient", patient);
        modelAndView.addObject("symptomsList", patient.getSymptomsHistories());
        modelAndView.setViewName("patient/pages/symptoms");

        return modelAndView;
    }

    @GetMapping(value = "/symptom/{id_symptom}")
    public ModelAndView PageSymptom(@ModelAttribute("message") String message,
                                    @PathVariable("id_symptom") String id_symptom) {

        Patient patient = getAuthPatient();
        SymptomsHistory record = patient.findSymptom(id_symptom);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("record", record);
        modelAndView.setViewName("patient/pages/symptom");

        return modelAndView;
    }

//doctor_info

    @GetMapping("/{id_doctor}/doctor_info")
    public ModelAndView PageDoctorInfo(@PathVariable("id_doctor") Long id) throws NoSuchAlgorithmException {

        ModelAndView modelAndView = new ModelAndView();
        Doctor doctor = doctorService.getById(id);

        List<Schedule> schedules = doctor.freeSchedule(LocalDate.now());
        modelAndView.addObject("doctor", doctor);

        modelAndView.setViewName("patient/pages/doctor_info");

        return modelAndView;
    }


    @GetMapping("/{id_doctor}/doctor_info/rate/{rate}")
    public ModelAndView addRate(@PathVariable("rate") int count,
                                @PathVariable("id_doctor") Long id) throws NoSuchAlgorithmException {

        ModelAndView modelAndView = new ModelAndView();
        Doctor doctor = doctorService.getById(id);
        Patient patient = getAuthPatient();

        Rate rate = new Rate();
        String id_rate = SecureRandom.getInstance("SHA1PRNG").nextInt() + "_" + doctor.getRNTRC() + "_" + getAuthPatient().getRNTRC();
        if (patient.getRateByDoctor(doctor) != null) {
            rate.setId(patient.getRateByDoctor(doctor).getId());
        } else {
            rate.setId(id_rate);
        }
        rate.setD_rate(doctor);
        rate.setPatient(patient);
        rate.setCount(count);
        doctor.addRate(rate);
        doctorService.add(doctor);

        List<Schedule> schedules = doctor.freeSchedule(LocalDate.now());
        modelAndView.addObject("doctor", doctor);
        modelAndView.addObject("date", Date.valueOf(LocalDate.now()));
        modelAndView.addObject("dateV", dateToString(Date.valueOf(LocalDate.now())));
        modelAndView.addObject("schedules", schedules);
        modelAndView.setViewName("patient/pages/doctor_info");

        return modelAndView;
    }

    @PostMapping("/add_comment")
    public ModelAndView addComment(@ModelAttribute("doctor_id") Long doctor_id,
                                   @ModelAttribute("comment") Comment comment) throws NoSuchAlgorithmException {

        ModelAndView modelAndView = new ModelAndView();
        Doctor doctor = doctorService.getById(doctor_id);

        String id_rate = SecureRandom.getInstance("SHA1PRNG").nextInt() + "_" + doctor.getRNTRC() + "_" + getAuthPatient().getRNTRC();
        comment.setId(id_rate);
        comment.setD_com(doctor);
        comment.setPatient(getAuthPatient());
        comment.setDate(currentDate());

        doctor.addComment(comment);
        doctorService.add(doctor);

        modelAndView.setViewName("redirect:/patient/" + doctor_id + "/doctor_info");

        return modelAndView;
    }

//schedule

    @RequestMapping(value = "/{id_doctor}/schedule", method = RequestMethod.GET)
    public ModelAndView PageSchedule(@PathVariable("id_doctor") Long id) {

        ModelAndView modelAndView = new ModelAndView();
        Doctor doctor = doctorService.getById(id);
        List<Schedule> schedules = doctor.freeSchedule(LocalDate.now());
        modelAndView.addObject("doctor", doctor);
        modelAndView.addObject("date", Date.valueOf(LocalDate.now()));
        modelAndView.addObject("dateV", dateToString(Date.valueOf(LocalDate.now())));
        modelAndView.addObject("schedules", schedules);
        modelAndView.setViewName("patient/pages/schedule");

        return modelAndView;
    }

    @RequestMapping(value = "/{id_doctor}/schedulesByDay", method = RequestMethod.GET)
    public ModelAndView schedulesByDay(@ModelAttribute("date1") String date,
                                       @PathVariable("id_doctor") Long id) {

        ModelAndView modelAndView = new ModelAndView();
        Doctor doctor = doctorService.getById(id);
        List<Schedule> schedules = doctor.freeSchedule(Date.valueOf(date).toLocalDate());
        modelAndView.addObject("date", Date.valueOf(date));
        modelAndView.addObject("dateV", dateToString(Date.valueOf(date)));
        modelAndView.addObject("doctor", doctor);
        modelAndView.addObject("schedules", schedules);

        modelAndView.setViewName("patient/pages/schedule");

        return modelAndView;
    }

    @RequestMapping(value = "/{id_doctor}/add_visit/{date1}/{id_schedule}", method = RequestMethod.GET)
    public ModelAndView addPageVisit(@PathVariable("id_doctor") Long id,
                                     @PathVariable("date1") String date,
                                     @PathVariable("id_schedule") int id_schedule) {

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
                                 @ModelAttribute("id_schedule") int id_schedule) throws NoSuchAlgorithmException {
        Patient patient = getAuthPatient();
        Doctor doctor = doctorService.getById(id);

        String id_visit = SecureRandom.getInstance("SHA1PRNG").nextInt() + "_" + patient.getRNTRC();
        visit.setNumber(id_visit);
        visit.setStatus(false);
        visit.setPatient(patient);
        visit.setDoctor(doctor);
        visit.setSchedule(doctor.findSchedule(id_schedule));

        if (!Objects.equals(doctor.getSpecialization().getName(), doctorService.getByIdSpecialization(1).getName())) {
            patient.findActiveDirection(doctor.getSpecialization()).setStatus(false);
        }

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
        Visit visit = patient.findVisit(id_visit);
        Doctor doctor = visit.getDoctor();

        if (!Objects.equals(doctor.getSpecialization().getName(), doctorService.getByIdSpecialization(1).getName())) {
            patient.findNotActiveDirection(doctor.getSpecialization()).setStatus(true);
        }

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
    public ModelAndView allDoctorsBySpecialization(@PathVariable("specialization") int specialization) {

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

        if (!user_id.isEmpty()) {
            patient.setUser(userService.findUserById(user_id));
        }
        patientService.add(patient);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/patient/profile");

        return modelAndView;
    }
}
