package com.boots.controller;

import com.boots.entity.*;
import com.boots.service.*;
import com.boots.transientClasses.Medicine;
import com.boots.transientClasses.Recipe;
import com.boots.transientClasses.Sick_leave;
import com.boots.transientClasses.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

@Controller
@SessionAttributes(value = {"recipeJSP", "sick_leaveJSP"})
public class DoctorController {
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


    public String dateToString(Date date)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int year = cal.get(Calendar.YEAR);
        return day+"."+month+"."+year;
    }

    public Long getIdPatientSplit(String id_visit)
    {
        String[] words = id_visit.split("_");
        return Long.parseLong(words[1]);
    }

    public void deleteExpiredVisits(Doctor doctor)
    {
        for (Visit visit : doctor.expiredVisits()) {
            if (!Objects.equals(visit.getDoctor().getSpecialization().getName(), doctorService.getByIdSpecialization(1).getName()))
                visit.getPatient().findNotActiveDirection(visit.getDoctor().getSpecialization()).setStatus(true);
            patientService.deleteVisit(visit.getNumber());
        }
        doctor.removeExpiredVisits();
    }

    //doctor
    public Doctor getAuthDoc()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();
        return doctorService.doctorByUser(user);
    }

    @RequestMapping(value = "/patients/doctor", method = RequestMethod.GET)
    public ModelAndView PageDoctor() {
        Doctor doctor = getAuthDoc();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("doctor", doctor);
        modelAndView.addObject("visitsList", doctor.getDoneVisitsByMonth(LocalDate.now()));
        modelAndView.addObject("todayVisitsList", doctor.getDoneVisitsByDay(LocalDate.now()));
        modelAndView.setViewName("doctor/pages/doctor");

        return modelAndView;
    }



//patient
    @RequestMapping(value = "/{id_visit}/visits/edit_patient", method = RequestMethod.GET)
    public ModelAndView editPagePatient(@PathVariable("id_visit") String id_visit,
                                        @ModelAttribute("message") String message) {
        Patient patient = patientService.getById(getIdPatientSplit(id_visit));

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("doctor/form/edit_form/edit_patient");
        modelAndView.addObject("patient", patient);
        modelAndView.addObject("id_visit", id_visit);
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
        modelAndView.setViewName("redirect:/"+id_visit+"/visits/1");

        return modelAndView;
    }

    //visits
    @RequestMapping(value = "/today_visits", method = RequestMethod.GET)
    public ModelAndView today_Visits() {
        Doctor doctor=getAuthDoc();
        deleteExpiredVisits(doctor);
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("visitsList", doctor.getActiveVisitsByDay(LocalDate.now()));
        modelAndView.addObject("doctor", doctor);


        modelAndView.setViewName("doctor/pages/today_visits");

        return modelAndView;
    }

    @RequestMapping(value = "/visitsByDay", method = RequestMethod.GET)
    public ModelAndView visitsByDay(@ModelAttribute("date1") String date) {
        Doctor doctor=getAuthDoc();

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("visitsList", doctor.getActiveVisitsByDay(Date.valueOf(date).toLocalDate()));
        modelAndView.addObject("doctor", doctor);
        modelAndView.setViewName("doctor/pages/today_visits");

        return modelAndView;
    }



    @RequestMapping(value = "/{id_visit}/visits/{sort_int}", method = RequestMethod.GET)
    public ModelAndView allVisits(@PathVariable("id_visit") String id_visit,
                                  @PathVariable("sort_int") int sort_int) {
        Doctor doctor=getAuthDoc();
        Patient patient = patientService.getById(getIdPatientSplit(id_visit));

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("isActive", !patient.findVisit(id_visit).getStatus());
        modelAndView.addObject("patient", patient);
        modelAndView.addObject("id_visit", id_visit);
        modelAndView.addObject("visitsList", patient.getDoneVisits(sort_int));
        modelAndView.addObject("doctor", doctor);
        modelAndView.setViewName("doctor/pages/visits");

        return modelAndView;
    }

    @RequestMapping(value = "/{id_visit}/add_visit", method = RequestMethod.GET)
    public ModelAndView addPageVisit(@ModelAttribute("message") String message,
                                     @PathVariable("id_visit") String id_visit){
        List<Disease> diseases = patientService.allDiseases();
        Patient patient= patientService.getById(getIdPatientSplit(id_visit));

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("visit",  patient.findVisit(id_visit));
        modelAndView.addObject("id_visit", id_visit);
        modelAndView.addObject("diseasesList", sort.sortDisease(diseases,2));
        modelAndView.addObject("notes", "");

        modelAndView.setViewName("doctor/form/create_form/new_visit");

        return modelAndView;
    }

    @RequestMapping(value = "/add_visit_act", method = RequestMethod.POST)
    public ModelAndView addVisit(@ModelAttribute("visit") Visit visit,
                                 @ModelAttribute("selected_disease") String selected_disease,
                                 @ModelAttribute("notes") String notes,
                                 @ModelAttribute("id_visit") String id_visit,
                                 @ModelAttribute("id_schedule") int id_schedule) {
        Doctor doctor=getAuthDoc();
        Patient patient = patientService.getById(getIdPatientSplit(id_visit));
        Disease disease = patientService.getByIdDisease(selected_disease);

        visit.setNumber(id_visit);
        visit.setSchedule(doctor.findSchedule(id_schedule));

        if(notes==null)
            visit.setNotes("");

        visit.setDoctor(doctor);
        visit.setPatient(patient);
        visit.setDisease(disease);
        visit.setStatus(true);
        patient.addVisit(visit);

        patientService.add(patient);
        doctorService.add(doctor);

        ModelAndView modelAndView = new ModelAndView();
        if(doctor.getSpecialization() == doctorService.getByIdSpecialization(1))
            modelAndView.setViewName("redirect:/doctor1/"+visit.getNumber()+"/choose_action_direction");
        else
            modelAndView.setViewName("redirect:/"+visit.getNumber()+"/choose_action_med");


        return modelAndView;
    }

    @RequestMapping(value = "{id_c_visit}/{id_visit}/visit", method = RequestMethod.GET)
    public ModelAndView PageVisit(@ModelAttribute("message") String message,
                                  @PathVariable("id_visit") String id_visit,
                                  @PathVariable("id_c_visit") String id_c_visit) {

        Patient patient = patientService.getById(getIdPatientSplit(id_c_visit));
        Visit visit = patient.findVisit(id_visit);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("id_visit", id_c_visit);
        modelAndView.addObject("visit", visit);
        modelAndView.setViewName("doctor/pages/visit");

        return modelAndView;
    }


//recipe
    @RequestMapping(value = "/{id_visit}/choose_action_med", method = RequestMethod.GET)
    public ModelAndView choose_actionMedPage(@ModelAttribute("message") String message,
                                             @ModelAttribute("recipeJSP") Recipe recipe,
                                             @PathVariable("id_visit") String id_visit){
        recipe.setMedicineList(new ArrayList<>());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("recipeJSP", new Recipe());
        modelAndView.addObject("id_visit",id_visit);
        modelAndView.setViewName("doctor/form/choose_form/choose_action_med");
        return modelAndView;
    }

    @ModelAttribute("recipeJSP")
    public Recipe createRecipe() {
        return new Recipe();
    }

    @RequestMapping(value = "/{id_visit}/add_new_medicine", method = RequestMethod.GET)
    public ModelAndView addPageMedicine(@ModelAttribute("message") String message,
                                        @ModelAttribute("recipeJSP") Recipe recipe,
                                        @PathVariable("id_visit") String id_visit) {

        List<MedicineCatalog> medicineCatalogList = patientService.allMedicines();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("medicineCatalogList", sort.sortMedicineCatalog(medicineCatalogList, 2 ));
        modelAndView.addObject("recipeJSP", recipe);
        modelAndView.addObject("id_visit",id_visit);
        modelAndView.setViewName("doctor/form/create_form/new_medicine");

        return modelAndView;
    }

    @RequestMapping(value = "/add_new_medicine_act", method = RequestMethod.POST, params = "add_new_medicine")
    public ModelAndView addNewMedicine(@ModelAttribute("medicine") Medicine medicine,
                                       @ModelAttribute("selected_medicine") String selected_medicine,
                                       @ModelAttribute("recipeJSP") Recipe recipe,
                                       @ModelAttribute("id_visit") String id_visit) {
        medicine.setName(selected_medicine);
        recipe.addMedicine(medicine);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/"+id_visit+"/add_new_medicine");
        return modelAndView;
    }



    @RequestMapping(value = "/add_new_medicine_act", method = RequestMethod.POST, params = "safe_Recipe")
    public ModelAndView safeRecipe(@ModelAttribute("recipeJSP") Recipe recipe,
                                   @ModelAttribute("id_visit") String id_visit) {
        Patient patient = patientService.getById(getIdPatientSplit(id_visit));
        Visit visit = patient.findVisit(id_visit);

        visit.setMedicineByList(recipe.getMedicineList());
        patient.setCount_of_recipe(patient.getCount_of_recipe()+1);
        patientService.add(patient);
        recipe.setVisit(visit);

        ModelAndView modelAndView = new ModelAndView();
        if(recipe.getMedicineList().isEmpty())
            modelAndView.setViewName("redirect:/"+id_visit+"/choose_action_sickLeave");
        else
            modelAndView.setViewName("redirect:/"+id_visit+"/recipe");
        return modelAndView;
    }


    @RequestMapping(value = "/{id_visit}/recipe", method = RequestMethod.GET)
    public ModelAndView RecipePage(@ModelAttribute("message") String message,
                                   @ModelAttribute("recipeJSP") Recipe recipe,
                                   @PathVariable("id_visit") String id_visit){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("id_visit", id_visit);
        modelAndView.addObject("recipe", recipe);
        modelAndView.addObject("date", dateToString(recipe.getVisit().getDate()));
        modelAndView.setViewName("doctor/document/recipe");
        return modelAndView;
    }

    //sickLeave
    @RequestMapping(value = "/{id_visit}/choose_action_sickLeave", method = RequestMethod.GET)
    public ModelAndView choose_actionPageSickLeave(@ModelAttribute("message") String message,
                                                   @PathVariable("id_visit") String id_visit){
        boolean is_family_role= getAuthDoc().getSpecialization().getId() == 1;

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("id_visit", id_visit);
        modelAndView.addObject("is_family_role", is_family_role);

        modelAndView.setViewName("doctor/form/choose_form/choose_action_sickLeave");
        return modelAndView;
    }

    @RequestMapping(value = "/{id_visit}/add_sick_leave", method = RequestMethod.GET)
    public ModelAndView addPageSickLeave(@ModelAttribute("message") String message,
                                         @PathVariable("id_visit") String id_visit){

        List<Disease> diseases = patientService.allDiseases();
        Patient patient = patientService.getById(getIdPatientSplit(id_visit));
        Visit visit = patient.findVisit(id_visit);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("id_visit", id_visit);
        modelAndView.addObject("visit", visit);
        modelAndView.addObject("diseasesList", diseases);
        modelAndView.addObject("sick_leaveJSP", new Sick_leave());
        modelAndView.setViewName("doctor/form/create_form/new_sick_leave");

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
        Disease disease =patientService.getByIdDisease(selected_disease1);

        patient.setCount_of_sick_leave(patient.getCount_of_sick_leave()+1);
        patientService.add(patient);
        sick_leave.setVisit(visit);
        sick_leave.setStart_disease(disease);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/"+id_visit+"/sick_leave");

        return modelAndView;
    }

    @RequestMapping(value = "/{id_visit}/sick_leave", method = RequestMethod.GET)
    public ModelAndView SickLeave(@ModelAttribute("message") String message,
                                 // @ModelAttribute("recipeJSP") Recipe recipe,
                                  @ModelAttribute("sick_leaveJSP") Sick_leave sick_leave,
                                  @PathVariable("id_visit") String id_visit){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("sick_leave", sick_leave);
        modelAndView.addObject("id_visit", id_visit);
        modelAndView.addObject("date",dateToString(sick_leave.getVisit().getDate()));
        modelAndView.addObject("start_date",dateToString(sick_leave.getStart_date()));
        modelAndView.setViewName("doctor/document/sick_leave");
        return modelAndView;
    }
}
