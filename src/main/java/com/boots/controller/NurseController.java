package com.boots.controller;

import com.boots.entity.Disease;
import com.boots.entity.Doctor;
import com.boots.entity.Patient;

import com.boots.entity.SymptomsHistory;
import com.boots.entity.User;
import com.boots.entity.Visit;
import com.boots.service.DirectionService;
import com.boots.service.DoctorService;
import com.boots.service.MedicineCatalogService;
import com.boots.service.PatientService;
import com.boots.service.SpecializationService;
import com.boots.service.UserService;
import com.boots.transientClasses.Sick_leave;
import com.boots.transientClasses.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import static com.boots.transientClasses.ControllerMainTools.currentDate;
import static com.boots.transientClasses.ControllerMainTools.dateToString;
import static com.boots.transientClasses.ControllerMainTools.getIdPatientSplit;

@RestController
@RequestMapping("/nurse")
@SessionAttributes(value = {"recipeJSP", "sick_leaveJSP"})
public class NurseController {

    protected PatientService patientService;
    protected DoctorService doctorService;
    @Autowired
    private UserService userService;

    @Autowired
    protected SpecializationService specializationService;

    @Autowired
    protected MedicineCatalogService medicineCatalogService;

    @Autowired
    protected DirectionService directionService;

    protected Sort sort = new Sort();

    @Autowired
    public void setPatientService(PatientService patientService) {
        this.patientService = patientService;
    }

    @Autowired
    public void setDoctorService(DoctorService doctorService) {
        this.doctorService = doctorService;
    }


    //doctor
    public Doctor getAuthDoc() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return doctorService.doctorByUser(user);
    }


    @RequestMapping(value = "/doctor", method = RequestMethod.GET)
    public ModelAndView PageNurse() {
        Doctor doctor = getAuthDoc();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("doctor", doctor);
        modelAndView.addObject("visitsList", doctor.getDoneVisitsByMonth(LocalDate.now()));
        modelAndView.addObject("todayVisitsList", doctor.getDoneVisitsByDay(LocalDate.now()));
        modelAndView.addObject("standing", doctor.getStanding());
        modelAndView.setViewName("nurse/pages/doctor");

        return modelAndView;
    }

    @RequestMapping(value = "/patients/{sort_num}", method = RequestMethod.GET)
    public ModelAndView allPatientsPage(@PathVariable("sort_num") int sort_num) {
        List<Patient> patients = patientService.allPatients();
        Doctor doctor = getAuthDoc();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("patientsList", sort.sortPatient(patients, sort_num));
        modelAndView.addObject("doctor", doctor);
        modelAndView.setViewName("nurse/pages/patients");
        return modelAndView;
    }

    @RequestMapping(value = "/patients/searchTelephone_number", method = RequestMethod.GET)
    public ModelAndView allPatientsWithCurrentTelephone_number(@ModelAttribute("telephone_number") int telephone_number) {
        Doctor doctor = getAuthDoc();
        List<Patient> patients = patientService.findTelephone_number(telephone_number);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("patientsList", patients);
        modelAndView.addObject("doctor", doctor);
        modelAndView.setViewName("nurse/pages/patients");
        return modelAndView;
    }

    @RequestMapping(value = "/{id_patient}/visits/{sort_int}", method = RequestMethod.GET)
    public ModelAndView allVisitsNurse(@PathVariable("id_patient") Long id,
                                       @PathVariable("sort_int") int sort_int) {
        Doctor doctor = getAuthDoc();
        Patient patient = patientService.getById(id);

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("patient", patient);
        modelAndView.addObject("visitsList", patient.getDoneVisits(sort_int));
        modelAndView.addObject("doctor", doctor);
        modelAndView.setViewName("nurse/pages/visits");

        return modelAndView;
    }

    //graph
    @GetMapping("/{id_patient}/graph")
    public ModelAndView PageGraph(@PathVariable("id_patient") Long id) {

        Doctor doctor = getAuthDoc();
        Patient patient = patientService.getById(id);

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("patient", patient);
        modelAndView.addObject("doctor", doctor);
        modelAndView.addObject("visitList", patient.getDoneVisits(1));
        modelAndView.addObject("diseaseList", patient.getDiseaseDoneVisits());
        modelAndView.addObject("specializationList", patient.getSpecializationDoneVisits());
        modelAndView.setViewName("nurse/pages/graph");

        return modelAndView;
    }


    @RequestMapping(value = "/{id_patient}/symptoms/{sort_int}", method = RequestMethod.GET)
    public ModelAndView allSymptomsNurse(@PathVariable("id_patient") Long id_patient,
                                         @PathVariable("sort_int") int sort_int) {
        Doctor doctor = getAuthDoc();
        Patient patient = patientService.getById(id_patient);

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("patient", patient);
        modelAndView.addObject("symptomsList", patient.getSymptomsHistories());
        modelAndView.addObject("doctor", doctor);
        modelAndView.setViewName("nurse/pages/symptoms");

        return modelAndView;
    }

    @RequestMapping(value = "/{id_patient}/{id_symptom}/symptom", method = RequestMethod.GET)
    public ModelAndView PageSymptom(@ModelAttribute("message") String message,
                                    @PathVariable("id_patient") Long id_patient,
                                    @PathVariable("id_symptom") String id_symptom) {

        Patient patient = patientService.getById(id_patient);
        SymptomsHistory record = patient.findSymptom(id_symptom);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("id_patient", id_patient);
        modelAndView.addObject("record", record);
        modelAndView.setViewName("nurse/pages/symptom");

        return modelAndView;
    }

    //visit
    @RequestMapping(value = "/{id_patient}/{id_visit}/visit", method = RequestMethod.GET)
    public ModelAndView PageVisit(@ModelAttribute("message") String message,
                                  @PathVariable("id_visit") String id_visit,
                                  @PathVariable("id_patient") Long id_patient) {

        Patient patient = patientService.getById(id_patient);
        Visit visit = patient.findVisit(id_visit);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("id_patient", id_patient);
        modelAndView.addObject("visit", visit);
        modelAndView.setViewName("nurse/pages/visit");

        return modelAndView;
    }

    @RequestMapping(value = "/{id_patient}/add_visit", method = RequestMethod.GET)
    public ModelAndView addPageVisit(@ModelAttribute("message") String message,
                                     @PathVariable("id_patient") Long id_patient) {
        List<Disease> diseases = patientService.allDiseases();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("visit", new Visit());
        modelAndView.addObject("id_patient", id_patient);
        modelAndView.addObject("diseasesList", sort.sortDisease(diseases, 2));
        modelAndView.addObject("notes", "");

        modelAndView.setViewName("nurse/form/create_form/new_visit");

        return modelAndView;
    }

    @RequestMapping(value = "/add_visit_act", method = RequestMethod.POST)
    public ModelAndView addVisit(@ModelAttribute("visit") Visit visit,
                                 @ModelAttribute("id_patient") Long id_patient,
                                 @ModelAttribute("selected_disease") String selected_disease,
                                 @ModelAttribute("notes") String notes) throws NoSuchAlgorithmException {

        Patient patient = patientService.getById(id_patient);
        Doctor doctor = getAuthDoc();
        Disease disease = patientService.getByIdDisease(selected_disease);

        String id_visit = SecureRandom.getInstance("SHA1PRNG").nextInt() + "_" + patient.getRNTRC();
        visit.setNumber(id_visit);
        visit.setStatus(true);
        visit.setPatient(patient);
        visit.setDate(currentDate());

        if (notes == null) {
            visit.setNotes("");
        }

        visit.setDoctor(doctor);
        visit.setDisease(disease);

        patient.addVisit(visit);

        patientService.add(patient);

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("redirect:/nurse/" + visit.getNumber() + "/choose_action_sickLeave");


        return modelAndView;
    }

    //sickLeave
    @RequestMapping(value = "/{id_visit}/choose_action_sickLeave", method = RequestMethod.GET)
    public ModelAndView choose_actionPageSickLeave(@ModelAttribute("message") String message,
                                                   @PathVariable("id_visit") String id_visit) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("id_visit", id_visit);
        modelAndView.addObject("id_patient", getIdPatientSplit(id_visit));

        modelAndView.setViewName("nurse/form/choose_form/choose_action_sickLeave");
        return modelAndView;
    }

    @RequestMapping(value = "/{id_visit}/add_sick_leave", method = RequestMethod.GET)
    public ModelAndView addPageSickLeave(@ModelAttribute("message") String message,
                                         @PathVariable("id_visit") String id_visit) {

        List<Disease> diseases = patientService.allDiseases();
        Patient patient = patientService.getById(getIdPatientSplit(id_visit));
        Visit visit = patient.findVisit(id_visit);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("id_visit", id_visit);
        modelAndView.addObject("visit", visit);
        modelAndView.addObject("diseasesList", diseases);
        modelAndView.addObject("sick_leaveJSP", new Sick_leave());
        modelAndView.setViewName("nurse/form/create_form/new_sick_leave");

        return modelAndView;
    }

    @ModelAttribute("sick_leaveJSP")
    public Sick_leave createSick_leave() {
        return new Sick_leave();
    }

    //сравнение дат
    @RequestMapping(value = "/add_sick_leave_act", method = RequestMethod.POST)
    public ModelAndView addSickLeave(@ModelAttribute("selected_disease1") String selected_disease1,
                                     @ModelAttribute("sick_leaveJSP") Sick_leave sick_leave,
                                     @ModelAttribute("id_visit") String id_visit) {
        Patient patient = patientService.getById(getIdPatientSplit(id_visit));
        Visit visit = patient.findVisit(id_visit);
        Disease disease = patientService.getByIdDisease(selected_disease1);

        patient.setCount_of_sick_leave(patient.getCount_of_sick_leave() + 1);
        patientService.add(patient);
        sick_leave.setVisit(visit);
        sick_leave.setStart_disease(disease);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/nurse/" + id_visit + "/sick_leave");

        return modelAndView;
    }

    @RequestMapping(value = "/{id_visit}/sick_leave", method = RequestMethod.GET)
    public ModelAndView SickLeave(@ModelAttribute("message") String message,
                                  // @ModelAttribute("recipeJSP") Recipe recipe,
                                  @ModelAttribute("sick_leaveJSP") Sick_leave sick_leave,
                                  @PathVariable("id_visit") String id_visit) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("sick_leave", sick_leave);
        modelAndView.addObject("id_visit", id_visit);
        modelAndView.addObject("id_patient", getIdPatientSplit(id_visit));
        modelAndView.addObject("date", dateToString(sick_leave.getVisit().getDate()));
        modelAndView.addObject("start_date", dateToString(sick_leave.getStart_date()));
        modelAndView.setViewName("nurse/document/sick_leave");
        return modelAndView;
    }

    @RequestMapping(value = "/{id_visit}/choose_patient_spec_data", method = RequestMethod.GET)
    public ModelAndView choose_actionPatientSpecData(@ModelAttribute("message") String message,
                                                   @PathVariable("id_visit") String id_visit) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("id_visit", id_visit);
        modelAndView.addObject("id_patient", getIdPatientSplit(id_visit));

        modelAndView.setViewName("nurse/form/choose_form/choose_action_specData");
        return modelAndView;
    }

    @RequestMapping(value = "/{id_visit}/edit_patient_spec_data", method = RequestMethod.GET)
    public ModelAndView editPagePatientSpecData(@PathVariable("id_visit") String id_visit) {
        Patient patient = patientService.getById(getIdPatientSplit(id_visit));

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("nurse/form/create_form/new_specDataPatient");
        modelAndView.addObject("patient", patient);
        modelAndView.addObject("id_visit", id_visit);
        return modelAndView;
    }

    @RequestMapping(value = "/edit_patient_spec_data", method = RequestMethod.POST)
    public ModelAndView editPatientSpecData(@ModelAttribute("patient") Patient p,
                                            @ModelAttribute("id_visit") String id_visit) {
        Patient patient = patientService.getById(getIdPatientSplit(id_visit));
        patient.setAbdominal_circumference(p.getAbdominal_circumference());
        patient.setChest_circumference(p.getChest_circumference());
        patient.setWidth(p.getWidth());
        patient.setHeight(p.getHeight());
        patientService.add(patient);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/nurse/" + getIdPatientSplit(id_visit) + "/visits/1");

        return modelAndView;
    }

    //patient

    @RequestMapping(value = "/{id_patient}/visits/edit_patient", method = RequestMethod.GET)
    public ModelAndView editPagePatient(@PathVariable("id_patient") Long id_patient,
                                        @ModelAttribute("message") String message) {
        Patient patient = patientService.getById(id_patient);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("nurse/form/edit_form/edit_patient");
        modelAndView.addObject("patient", patient);
        modelAndView.addObject("id_patient", id_patient);
        return modelAndView;
    }

    @RequestMapping(value = "/edit_patient", method = RequestMethod.POST)
    public ModelAndView editPatient(@ModelAttribute("patient") Patient patient,
                                    @ModelAttribute("sex") String sex,
                                    @ModelAttribute("id_patient") Long id_patient,
                                    @ModelAttribute("user_id") String user_id) {
        patient.setSex(Integer.parseInt(sex));
        if (!user_id.isEmpty()) {
            patient.setUser(userService.findUserById(user_id));
        }
        patientService.add(patient);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/nurse/" + id_patient + "/visits/1");

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

        modelAndView.setViewName("nurse/form/create_form/new_patient");
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
            modelAndView.setViewName("redirect:/nurse/" + patient.getRNTRC() + "/add_user");

        } else {
            modelAndView.addObject("message", "y");
            modelAndView.setViewName("redirect:/nurse/add_patient");
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
        modelAndView.setViewName("nurse/form/create_form/new_user");

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
            modelAndView.setViewName("nurse/form/create_form/new_user");
        } else {
            Patient patient = patientService.getById(id_patient);
            patient.setUser(user);
            patientService.add(patient);
            modelAndView.setViewName("redirect:/nurse/patients/1");
        }


        return modelAndView;
    }


}
