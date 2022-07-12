package com.boots.controller;

import com.boots.entity.*;
import com.boots.service.*;
import com.boots.transientClasses.Sort;

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
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    protected PatientService patientService;

    @Autowired
    protected DoctorService doctorService;

    @Autowired
    protected BodyPartService bodyPartService;

    @Autowired
    protected DirectionService directionService;

    @Autowired
    protected MedicineCatalogService medicineCatalogService;

    @Autowired
    protected SymptomsService symptomsService;

    @Autowired
    protected DiseaseService diseaseService;

    @Autowired
    protected SpecializationService specializationService;

    @Autowired
    protected TestTypeService testTypeService;

    protected Sort sort = new Sort();

    //patient
    @RequestMapping(value = "/patients/{sort_num}", method = RequestMethod.GET)
    public ModelAndView allPatientsPage(@PathVariable("sort_num") int sort_num) {
        List<Patient> patients = patientService.allPatients();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("patientsList", sort.sortPatient(patients, sort_num));
        modelAndView.setViewName("admin/tables/patients");
        return modelAndView;
    }

    @RequestMapping(value = "/patients/1", method = RequestMethod.GET, params = "search")
    public ModelAndView allPatientsWithCurrentTelephone_number(@ModelAttribute("telephone_number") int telephone_number) {

        List<Patient> patients = patientService.findTelephone_number(telephone_number);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("patientsList", sort.sortPatient(patients, 1));
        modelAndView.setViewName("admin/tables/patients");

        return modelAndView;
    }

    @GetMapping("/{id_patient}/set_user_for_patient")
    public ModelAndView addPageSetUserPatient(@PathVariable("id_patient") Long id_patient) {
        Patient patient = patientService.getById(id_patient);
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("isDoc", false);
        modelAndView.addObject("entity", patient);
        modelAndView.addObject("users", patient.getUsers(userService.allUsers(), 3));
        modelAndView.setViewName("admin/forms/form_user_for_entity");

        return modelAndView;
    }

    @PostMapping("/setUserPatient")
    public ModelAndView setUserPatient(@ModelAttribute("selected_user") String user_id,
                                       @ModelAttribute("id_patient") Long id_patient) {

        ModelAndView modelAndView = new ModelAndView();

        User user = userService.findUserById(user_id);
        user.setSelected(true);

        Patient patient = patientService.getById(id_patient);
        patient.setUser(user);
        patientService.add(patient);

        modelAndView.setViewName("redirect:/admin/patients/1");

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

        modelAndView.setViewName("admin/forms/form_patients");
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
            modelAndView.setViewName("redirect:/admin/patients/1");

        } else {
            modelAndView.addObject("message", "y");
            modelAndView.setViewName("redirect:/admin/add_patient");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/{id_patient}/edit_patient", method = RequestMethod.GET)
    public ModelAndView editPagePatient(@PathVariable("id_patient") Long id_patient) {
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
                                    @ModelAttribute("rh") String rh,
                                    @ModelAttribute("user_id") String user_id) {
        patient.setSex(Integer.parseInt(sex));
        patient.setBlood_type(Integer.parseInt(Blood_type));
        patient.setRh(rh);
        if (!user_id.isEmpty()) {
            patient.setUser(userService.findUserById(user_id));
        }

        patientService.add(patient);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/patients/1");

        return modelAndView;
    }


    @RequestMapping(value = "/{id_patient}/delete_patient", method = RequestMethod.GET)
    public ModelAndView deletePatient(@PathVariable("id_patient") Long id_patient) {
        patientService.deletePatient(id_patient);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/patients/1");

        return modelAndView;
    }

    //bodyPart
    @GetMapping("/bodyPart")
    public ModelAndView allBodyPart() {
        List<BodyPart> bodyParts = bodyPartService.allBodyPart();
        bodyParts.sort(Comparator.comparing(BodyPart::getName));

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("bodyPartList", bodyParts);
        modelAndView.setViewName("admin/tables/bodyPart");
        return modelAndView;

    }

    @RequestMapping(value = "/add_bodyPart", method = RequestMethod.GET)
    public ModelAndView addBodyPartPage(@ModelAttribute("message") String message) {
        BodyPart bodyPart = new BodyPart();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("bodyPart", bodyPart);

        if (message.equals("y")) {
            modelAndView.addObject("message", message);
        } else {
            modelAndView.addObject("message", null);
        }

        modelAndView.setViewName("admin/forms/form_bodyPart");
        return modelAndView;
    }

    @PostMapping("/add_bodyPart")
    public ModelAndView addBodyPart(@ModelAttribute("bodyPart") BodyPart bodyPart) throws NoSuchAlgorithmException {
        ModelAndView modelAndView = new ModelAndView();
        String id = SecureRandom.getInstance("SHA1PRNG").nextInt() + "";
        bodyPart.setId(id);
        bodyPartService.addBodyPart(bodyPart);
        modelAndView.setViewName("redirect:/admin/bodyPart");

        return modelAndView;
    }


    @GetMapping("/{id}/edit_bodyPart")
    public ModelAndView editPageBodyPart(@PathVariable("id") String id) {
        BodyPart bodyPart = bodyPartService.getById(id);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/forms/form_bodyPart");
        modelAndView.addObject("bodyPart", bodyPart);
        return modelAndView;
    }

    @PostMapping(value = "/add_bodyPart", params = "edit")
    public ModelAndView editBodyPart(@ModelAttribute("bodyPart") BodyPart bodyPart) {
        bodyPartService.addBodyPart(bodyPart);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/bodyPart");

        return modelAndView;
    }

    @GetMapping("/{id}/delete_bodyPart")
    public ModelAndView deleteBodyPart(@PathVariable("id") String id) {
        BodyPart bodyPart = bodyPartService.getById(id);

        bodyPartService.delete(bodyPart);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/bodyPart");

        return modelAndView;
    }
    //symptoms
    @GetMapping("/{bodyPart_id}/symptom")
    public ModelAndView allSymptomsByBodyPart(@PathVariable("bodyPart_id") String bodyPart_id) {
        BodyPart bodyPart = bodyPartService.getById(bodyPart_id);
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("symptomList", bodyPart.getSymptoms());
        modelAndView.addObject("bodyPart", bodyPart);
        modelAndView.setViewName("admin/tables/symptom");

        return modelAndView;
    }

    @GetMapping("/{bodyPart_id}/add_symptom")
    public ModelAndView addSymptomPage(@ModelAttribute("message") String message,
                                       @PathVariable("bodyPart_id") String bodyPart_id) {
        Symptom symptom = new Symptom();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("symptom", symptom);
        modelAndView.addObject("bodyPart_id", bodyPart_id);

        if (message.equals("y")) {
            modelAndView.addObject("message", message);
        } else {
            modelAndView.addObject("message", null);
        }

        modelAndView.setViewName("admin/forms/form_symptom");
        return modelAndView;
    }

    @PostMapping("/add_symptom")
    public ModelAndView addSymptom(@ModelAttribute("symptom") Symptom symptom,
                                    @ModelAttribute("bodyPart_id") String bodyPart_id) throws NoSuchAlgorithmException {
        ModelAndView modelAndView = new ModelAndView();
        String id = SecureRandom.getInstance("SHA1PRNG").nextInt() + "";
        symptom.setId(id);

        symptomsService.addSymptom(symptom, bodyPart_id);
        modelAndView.setViewName("redirect:/admin/"+bodyPart_id+"/symptom");

        return modelAndView;
    }

    //doctor
    @RequestMapping(value = "/doctor/{sort_num}", method = RequestMethod.GET)
    public ModelAndView allDoctorsPage(@PathVariable("sort_num") int sort_num) {
        List<Doctor> doctors = doctorService.allDoctors();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("doctorList", sort.sortDoctor(doctors, sort_num));
        modelAndView.setViewName("admin/tables/doctor");
        return modelAndView;
    }

    @RequestMapping(value = "/doctor/1", method = RequestMethod.GET, params = "search")
    public ModelAndView allDoctorsWithCurrentTelephone_number(@ModelAttribute("telephone_number") int telephone_number) {
        List<Doctor> doctors = doctorService.findTelephone_number(telephone_number);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("doctorList", sort.sortDoctor(doctors, 1));
        modelAndView.setViewName("admin/tables/doctor");

        return modelAndView;
    }


    @GetMapping("/{id_doctor}/set_user_for_doctor")
    public ModelAndView addPageSetUserDoctor(@PathVariable("id_doctor") Long id_doctor) {
        Doctor doctor = doctorService.getById(id_doctor);
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("isDoc", true);
        modelAndView.addObject("entity", doctor);
        modelAndView.addObject("users", doctor.getUsers(userService.allUsers()));
        modelAndView.setViewName("admin/forms/form_user_for_entity");

        return modelAndView;
    }

    @RequestMapping(value = "/setUserPatient", method = RequestMethod.POST, params = "editD")
    public ModelAndView setUserDoctor(@ModelAttribute("selected_user") String user_id,
                                      @ModelAttribute("id_patient") Long id_doctor) {

        ModelAndView modelAndView = new ModelAndView();

        User user = userService.findUserById(user_id);
        user.setSelected(true);

        Doctor doctor = doctorService.getById(id_doctor);
        doctor.setUser(user);
        doctorService.add(doctor);

        modelAndView.setViewName("redirect:/admin/doctor/1");

        return modelAndView;
    }


    @RequestMapping(value = "/add_doctor", method = RequestMethod.GET)
    public ModelAndView addPageDoctor(@ModelAttribute("message") String message) {
        Doctor doctor = new Doctor();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("doctor", doctor);
        modelAndView.addObject("specializations", specializationService.allSpecialization());

        if (message.equals("y")) {
            modelAndView.addObject("message", message);
        } else {
            modelAndView.addObject("message", null);
        }

        modelAndView.setViewName("admin/forms/form_doctor");
        return modelAndView;
    }


    @RequestMapping(value = "/add_doctor", method = RequestMethod.POST)
    public ModelAndView addDoctor(@ModelAttribute("doctor") Doctor doctor,
                                  @ModelAttribute("sex") String sex,
                                  @ModelAttribute("selected_spec") int spec_id) {
        ModelAndView modelAndView = new ModelAndView();

        if ((patientService.checkRNTRC(doctor.getRNTRC()) && doctorService.checkRNTRC(doctor.getRNTRC()))) {
            doctor.setSex(Integer.parseInt(sex));
            doctor.setSpecialization(specializationService.getById(spec_id));

            doctorService.add(doctor);
            modelAndView.setViewName("redirect:/admin/doctor/1");

        } else {
            modelAndView.addObject("message", "y");
            modelAndView.setViewName("redirect:/admin/add_doctor");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/{id_doctor}/edit_doctor", method = RequestMethod.GET)
    public ModelAndView editPageDoctor(@PathVariable("id_doctor") Long id_doctor) {
        Doctor doctor = doctorService.getById(id_doctor);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/forms/form_doctor");
        modelAndView.addObject("doctor", doctor);
        modelAndView.addObject("id_doctor", id_doctor);
        //modelAndView.addObject("user_id", doctor.getUser().getId());
        modelAndView.addObject("specializations", specializationService.allSpecialization());

        return modelAndView;
    }

    @RequestMapping(value = "/add_doctor", method = RequestMethod.POST, params = "edit")
    public ModelAndView editDoctor(@ModelAttribute("doctor") Doctor doctor,
                                   @ModelAttribute("sex") String sex,
                                   @ModelAttribute("selected_spec") int spec_id) {
        doctor.setSex(Integer.parseInt(sex));
        doctor.setSpecialization(specializationService.getById(spec_id));

        doctorService.add(doctor);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/doctor/1");

        return modelAndView;
    }


    @RequestMapping(value = "/{id_doctor}/delete_doctor", method = RequestMethod.GET)
    public ModelAndView deleteDoctor(@PathVariable("id_doctor") Long id_doctor) {
        Doctor doctor = doctorService.getById(id_doctor);
        doctorService.delete(doctor);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/doctor/1");

        return modelAndView;
    }

    //schedule
    @RequestMapping(value = "/{id_doctor}/schedule/{sort_num}", method = RequestMethod.GET)
    public ModelAndView PageSchedule(@PathVariable("id_doctor") Long id_doctor,
                                     @PathVariable("sort_num") int sort_num) {
        Doctor doctor = doctorService.getById(id_doctor);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("doctor", doctor);
        modelAndView.addObject("schedules", sort.sortSchedule(doctor.getSchedules(), sort_num));

        modelAndView.setViewName("admin/tables/schedule");
        return modelAndView;
    }

    @RequestMapping(value = "/{id_doctor}/add_schedule", method = RequestMethod.GET)
    public ModelAndView addPageSchedule(@PathVariable("id_doctor") Long id_doctor) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("id_doctor", id_doctor);
        // modelAndView.addObject("schedule", new Schedule());
        modelAndView.setViewName("admin/forms/form_new_schedule");
        return modelAndView;
    }

    @RequestMapping(value = "/add_schedule", method = RequestMethod.POST)
    public ModelAndView addSchedule(@ModelAttribute("id_doctor") Long id_doctor,
                                    @ModelAttribute("day") int day,
                                    @ModelAttribute("timeStart") String timeStart,
                                    @ModelAttribute("timeEnd") String timeEnd,
                                    @ModelAttribute("interval") int interval) throws NoSuchAlgorithmException, ParseException {

        Doctor doctor = doctorService.getById(id_doctor);
        doctor.setSchedulesByRange(day, timeStart, timeEnd, interval);
        doctorService.add(doctor);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/" + id_doctor + "/schedule/2");

        return modelAndView;
    }

    @RequestMapping(value = "/{id_doctor}/{id_schedule}/edit_schedule", method = RequestMethod.GET)
    public ModelAndView editPageSchedule(@PathVariable("id_doctor") Long id_doctor,
                                         @PathVariable("id_schedule") int id_schedule) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/forms/form_schedule");
        modelAndView.addObject("id_doctor", id_doctor);
        modelAndView.addObject("schedule", doctorService.getScheduleById(id_schedule));
        return modelAndView;
    }

    @RequestMapping(value = "/add_schedule", method = RequestMethod.POST, params = "edit")
    public ModelAndView editMedicine(@ModelAttribute("schedule") Schedule schedule,
                                     @ModelAttribute("id_doctor") Long id_doctor) {
        Doctor doctor = doctorService.getById(id_doctor);
        doctor.addSchedule(schedule);
        doctorService.add(doctor);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/" + id_doctor + "/schedule/2");

        return modelAndView;
    }


    @RequestMapping(value = "/{id_doctor}/{id_schedule}/delete_schedule", method = RequestMethod.GET)
    public ModelAndView deleteSchedule(@PathVariable("id_schedule") int id_schedule,
                                       @PathVariable("id_doctor") Long id_doctor) {

        doctorService.deleteSchedule(id_schedule);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/" + id_doctor + "/schedule/2");

        return modelAndView;
    }

    //medicineCatalog
    @RequestMapping(value = "/medicineCatalog/{sort_num}", method = RequestMethod.GET)
    public ModelAndView allMedicineCatalogPage(@PathVariable("sort_num") int sort_num) {
        List<MedicineCatalog> medicineCatalogList = patientService.allMedicines();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("medicineCatalogList", sort.sortMedicineCatalog(medicineCatalogList, sort_num));
        modelAndView.setViewName("admin/tables/medicineCatalog");
        return modelAndView;
    }

    @RequestMapping(value = "/add_medicineCatalog", method = RequestMethod.GET)
    public ModelAndView addPageMedicine(@ModelAttribute("message") String message) {
        MedicineCatalog medicine = new MedicineCatalog();

        ModelAndView modelAndView = new ModelAndView();
        if (message.equals("y")) {
            modelAndView.addObject("message", message);
        } else {
            modelAndView.addObject("message", null);
        }

        modelAndView.addObject("medicine", medicine);
        modelAndView.setViewName("admin/forms/form_medicine");
        return modelAndView;
    }


    @RequestMapping(value = "/add_medicineCatalog", method = RequestMethod.POST)
    public ModelAndView addMedicine(@ModelAttribute("medicine") MedicineCatalog medicine) {
        ModelAndView modelAndView = new ModelAndView();

        if (medicineCatalogService.checkATX(medicine.getATX())) {

            medicineCatalogService.add(medicine);
            modelAndView.setViewName("redirect:/admin/medicineCatalog/1");

        } else {
            modelAndView.addObject("message", "y");
            modelAndView.setViewName("redirect:/admin/add_medicineCatalog");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/{ATX}/edit_medicineCatalog", method = RequestMethod.GET)
    public ModelAndView editPageMedicine(@PathVariable("ATX") String ATX) {
        MedicineCatalog medicineCatalog = medicineCatalogService.getById(ATX);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/forms/form_medicine");
        modelAndView.addObject("medicine", medicineCatalog);
        return modelAndView;
    }

    @RequestMapping(value = "/add_medicineCatalog", method = RequestMethod.POST, params = "edit")
    public ModelAndView editMedicine(@ModelAttribute("medicine") MedicineCatalog medicine) {
        medicineCatalogService.add(medicine);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/medicineCatalog/1");

        return modelAndView;
    }

    @RequestMapping(value = "/{ATX}/delete_medicineCatalog", method = RequestMethod.GET)
    public ModelAndView deleteMedicine(@PathVariable("ATX") String ATX) {
        MedicineCatalog medicine = medicineCatalogService.getById(ATX);
        medicineCatalogService.delete(medicine);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/medicineCatalog/1");

        return modelAndView;
    }

    //disease

    @RequestMapping(value = "/disease/{sort_num}", method = RequestMethod.GET)
    public ModelAndView allDiseasePage(@PathVariable("sort_num") int sort_num) {
        List<Disease> diseaseList = diseaseService.allDisease();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("diseaseList", sort.sortDisease(diseaseList, sort_num));
        modelAndView.setViewName("admin/tables/disease");
        return modelAndView;
    }

    @RequestMapping(value = "/add_disease", method = RequestMethod.GET)
    public ModelAndView addPageDisease(@ModelAttribute("message") String message) {
        Disease disease = new Disease();

        ModelAndView modelAndView = new ModelAndView();
        if (message.equals("y")) {
            modelAndView.addObject("message", message);
        } else {
            modelAndView.addObject("message", null);
        }
        modelAndView.addObject("disease", disease);
        modelAndView.setViewName("admin/forms/form_disease");
        return modelAndView;
    }


    @RequestMapping(value = "/add_disease", method = RequestMethod.POST)
    public ModelAndView addDisease(@ModelAttribute("disease") Disease disease) {
        ModelAndView modelAndView = new ModelAndView();
        //disease.se

        if (diseaseService.checkICD(disease.getICD_10())) {
            diseaseService.add(disease);
            modelAndView.setViewName("redirect:/admin/disease/1");
        } else {
            modelAndView.addObject("message", "y");
            modelAndView.setViewName("redirect:/admin/add_disease");

        }

        return modelAndView;
    }

    @RequestMapping(value = "/{ICD}/edit_disease", method = RequestMethod.GET)
    public ModelAndView editPageDisease(@PathVariable("ICD") String ICD) {
        Disease disease = diseaseService.getById(ICD);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/forms/form_disease");
        modelAndView.addObject("disease", disease);
        return modelAndView;
    }

    @RequestMapping(value = "/add_disease", method = RequestMethod.POST, params = "edit")
    public ModelAndView editDisease(@ModelAttribute("disease") Disease disease) {
        diseaseService.add(disease);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/disease/1");

        return modelAndView;
    }

    @RequestMapping(value = "/{ICD}/delete_disease", method = RequestMethod.GET)
    public ModelAndView deleteDisease(@PathVariable("ICD") String ICD) {
        Disease disease = diseaseService.getById(ICD);
        diseaseService.delete(disease);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/disease/1");

        return modelAndView;
    }

    //testType
    @GetMapping("/testType/{sort_num}")
    public ModelAndView allTestTypePage(@PathVariable("sort_num") int sort_num) {
        List<TestsType> testsTypes = testTypeService.allTestTypes();


        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("testTypeList", testsTypes);
        modelAndView.setViewName("admin/tables/testType");
        return modelAndView;
    }

    @GetMapping("/add_testType")
    public ModelAndView addPageTestType(@ModelAttribute("message") String message) {
        TestsType testType = new TestsType();

        ModelAndView modelAndView = new ModelAndView();
        if (message.equals("y")) {
            modelAndView.addObject("message", message);
        } else {
            modelAndView.addObject("message", null);
        }

        modelAndView.addObject("testType", testType);
        modelAndView.setViewName("admin/forms/form_testType");
        return modelAndView;
    }


    @PostMapping("/add_testType")
    public ModelAndView addTestType(@ModelAttribute("testType") TestsType testType) throws NoSuchAlgorithmException {
        ModelAndView modelAndView = new ModelAndView();
        String id = SecureRandom.getInstance("SHA1PRNG").nextInt() + "";

        testType.setId(id);

        if (testTypeService.checkId(testType.getId())) {
            testTypeService.add(testType);
            modelAndView.setViewName("redirect:/admin/testType/1");
        } else {
            modelAndView.addObject("message", "y");
            modelAndView.setViewName("redirect:/admin/add_testType");
        }

        return modelAndView;
    }

    @GetMapping("/{id}/edit_testType")
    public ModelAndView editPageTestType(@PathVariable("id") String id) {
        TestsType testsType = testTypeService.getTestsTypeById(id);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/forms/form_testType");
        modelAndView.addObject("testType", testsType);
        return modelAndView;
    }

    @PostMapping(value = "/add_testType", params = "edit")
    public ModelAndView editTestType(@ModelAttribute("testType") TestsType testType) {
        testTypeService.add(testType);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/testType/1");

        return modelAndView;
    }

    @GetMapping("/{id}/delete_testType")
    public ModelAndView deleteTestType(@PathVariable("id") String id) {
        TestsType testsType = testTypeService.getTestsTypeById(id);

        testTypeService.delete(testsType);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/testType/1");

        return modelAndView;
    }


    //specialization
    @RequestMapping(value = "/specialization/{sort_num}", method = RequestMethod.GET)
    public ModelAndView allSpecializationPage(@PathVariable("sort_num") int sort_num) {
        List<Specialization> specializationList = specializationService.allSpecialization();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("specializationList", sort.sortSpecialization(specializationList, sort_num));
        modelAndView.setViewName("admin/tables/specialization");
        return modelAndView;
    }

    @RequestMapping(value = "/add_specialization", method = RequestMethod.GET)
    public ModelAndView addPageSpecialization(@ModelAttribute("message") String message) {
        Specialization specialization = new Specialization();

        ModelAndView modelAndView = new ModelAndView();
        if (message.equals("y")) {
            modelAndView.addObject("message", message);
        } else {
            modelAndView.addObject("message", null);
        }

        modelAndView.addObject("specialization", specialization);
        modelAndView.setViewName("admin/forms/form_specialization");
        return modelAndView;
    }


    @RequestMapping(value = "/add_specialization", method = RequestMethod.POST)
    public ModelAndView addSpecialization(@ModelAttribute("specialization") Specialization specialization) {
        ModelAndView modelAndView = new ModelAndView();

        if (specializationService.checkId(specialization.getId())) {
            specializationService.add(specialization);
            modelAndView.setViewName("redirect:/admin/specialization/1");
        } else {
            modelAndView.addObject("message", "y");
            modelAndView.setViewName("redirect:/admin/add_specialization");
        }

        return modelAndView;
    }

    @RequestMapping(value = "/{id}/edit_specialization", method = RequestMethod.GET)
    public ModelAndView editPageSpecialization(@PathVariable("id") int id) {
        Specialization specialization = specializationService.getById(id);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/forms/form_specialization");
        modelAndView.addObject("specialization", specialization);
        return modelAndView;
    }

    @RequestMapping(value = "/add_specialization", method = RequestMethod.POST, params = "edit")
    public ModelAndView editSpecialization(@ModelAttribute("specialization") Specialization specialization) {
        specializationService.add(specialization);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/specialization/1");

        return modelAndView;
    }

    @RequestMapping(value = "/{id}/delete_specialization", method = RequestMethod.GET)
    public ModelAndView deleteSpecialization(@PathVariable("id") int id) {
        Specialization specialization = specializationService.getById(id);
        specializationService.delete(specialization);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/specialization/1");

        return modelAndView;
    }

    //user
    @RequestMapping(value = "/user/{sort_num}", method = RequestMethod.GET)
    public ModelAndView allUserPage(@PathVariable("sort_num") int sort_num) {
        List<User> userList = userService.allUsers();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userList", sort.sortUser(userList, sort_num));
        modelAndView.setViewName("admin/tables/user");
        return modelAndView;
    }

    @GetMapping("/add_user")
    public ModelAndView addPageUser(@ModelAttribute("message") String message) {
        ModelAndView modelAndView = new ModelAndView();

        if (message.equals("y")) {
            modelAndView.addObject("message", message);
        } else {
            modelAndView.addObject("message", null);
        }

        modelAndView.addObject("user", new User());
        modelAndView.addObject("roles", roleService.allRole());
        modelAndView.setViewName("admin/forms/form_user");

        return modelAndView;
    }

    @PostMapping("/add_user")
    public ModelAndView addUser(@ModelAttribute("user") @Valid User user,
                                @ModelAttribute("selected_role") int role_id) throws NoSuchAlgorithmException {
        user.setId(SecureRandom.getInstance("SHA1PRNG").nextInt() + "_" + user.getUsername());
        user.setSelected(false);

        ModelAndView modelAndView = new ModelAndView();

        if (!userService.saveUser(user, role_id)) {
            modelAndView.addObject("message", "y");
            modelAndView.setViewName("redirect:/admin/add_user");
        } else {
            modelAndView.setViewName("redirect:/admin/user/1");
        }

        return modelAndView;
    }


    @RequestMapping(value = "/change_password/{id}", method = RequestMethod.GET)
    public ModelAndView editPageUserPassword(@PathVariable("id") String id) {
        User user = userService.findUserById(id);
        Optional<Role> role = user.getRoles().stream().findFirst();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/forms/form_user_password");
        modelAndView.addObject("user", user);
        modelAndView.addObject("my_role_id", role.orElse(new Role()).getId());

        return modelAndView;
    }

    @RequestMapping(value = "/change_password", method = RequestMethod.POST, params = "edit")
    public ModelAndView editUserPassword(@ModelAttribute("user") @Valid User user,
                                         @ModelAttribute("my_role_id") int my_role_id) {
        userService.editUserPassword(user, my_role_id);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/" + user.getUsername() + "/edit_user");
        return modelAndView;
    }

    @RequestMapping(value = "/{username}/edit_user", method = RequestMethod.GET)
    public ModelAndView editPageUser(@PathVariable("username") String username) {
        User user = (User) userService.loadUserByUsername(username);
        Optional<Role> role = user.getRoles().stream().findFirst();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/forms/form_user");
        modelAndView.addObject("user", user);
        modelAndView.addObject("roles", roleService.allRole());
        modelAndView.addObject("my_role", role.orElse(new Role()));

        return modelAndView;
    }

    @RequestMapping(value = "/add_user", method = RequestMethod.POST, params = "edit")
    public ModelAndView editUser(@ModelAttribute("user") @Valid User user,
                                 @ModelAttribute("selected_role") int role_id) {

        userService.editUser(user, role_id);

        Optional<Role> role = user.getRoles().stream().findFirst();

        if (!user.isSelected()) {
            if (role.orElse(new Role()).getId() == 1 || role.orElse(new Role()).getId() == 4) {
                System.out.println("doc");

                Doctor doctor = doctorService.doctorByUser(user);
                if (doctor != null) {
                    doctor.setUser(null);
                    doctorService.add(doctor);
                }

            } else if (role.orElse(new Role()).getId() == 3) {
                System.out.println("pat");
                Patient patient = patientService.patientByUser(user);
                if (patient != null) {
                    patient.setUser(null);
                    patientService.add(patient);
                }

            }
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/user/1");

        return modelAndView;
    }

    @RequestMapping(value = "/{id}/delete_user", method = RequestMethod.GET)
    public ModelAndView deleteUser(@PathVariable("id") String id) {
        userService.deleteUser(id);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/user/1");

        return modelAndView;
    }

    //role
    @RequestMapping(value = "/role/{sort_num}", method = RequestMethod.GET)
    public ModelAndView allRolePage(@PathVariable("sort_num") int sort_num) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("roleList", sort.sortRole(roleService.allRole(), sort_num));
        modelAndView.setViewName("admin/tables/role");
        return modelAndView;
    }

    @RequestMapping(value = "/add_role", method = RequestMethod.GET)
    public ModelAndView addPageRole(@ModelAttribute("message") String message) {
        Role role = new Role();

        ModelAndView modelAndView = new ModelAndView();
        if (message.equals("y")) {
            modelAndView.addObject("message", message);
        } else {
            modelAndView.addObject("message", null);
        }
        modelAndView.addObject("role", role);
        modelAndView.setViewName("admin/forms/form_role");
        return modelAndView;
    }

    @RequestMapping(value = "/add_role", method = RequestMethod.POST)
    public ModelAndView addRole(@ModelAttribute("role") Role role,
                                @ModelAttribute("name") String s_name) {
        ModelAndView modelAndView = new ModelAndView();
        role.setName("ROLE_" + s_name);

        if (roleService.checkId(role.getId())) {
            roleService.add(role);
            modelAndView.setViewName("redirect:/admin/role/1");
        } else {
            modelAndView.addObject("message", "y");
            modelAndView.setViewName("redirect:/admin/add_role");
        }

        return modelAndView;
    }

    @RequestMapping(value = "/{id}/edit_role", method = RequestMethod.GET)
    public ModelAndView editPageRole(@PathVariable("id") int id) {
        Role role = roleService.getById(id);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/forms/form_role");
        modelAndView.addObject("role", role);
        return modelAndView;
    }

    @RequestMapping(value = "/add_role", method = RequestMethod.POST, params = "edit")
    public ModelAndView editRole(@ModelAttribute("role") Role role) {
        roleService.add(role);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/role/1");

        return modelAndView;
    }

    @RequestMapping(value = "/{id}/delete_role", method = RequestMethod.GET)
    public ModelAndView deleteRole(@PathVariable("id") int id) {
        Role role = roleService.getById(id);
        roleService.delete(role);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/role/1");

        return modelAndView;
    }

    //directions
    @RequestMapping(value = "/{id_patient}/direction/{sort_num}", method = RequestMethod.GET)
    public ModelAndView allDirection(@PathVariable("id_patient") Long id_patient,
                                     @PathVariable("sort_num") int sort_num) {
        ModelAndView modelAndView = new ModelAndView();
        Patient patient = patientService.getById(id_patient);

        modelAndView.addObject("patient", patient);
        modelAndView.addObject("directionList", sort.sortDirection(patient.getDirections(), sort_num));
        modelAndView.setViewName("admin/tables/direction");
        return modelAndView;
    }

    @RequestMapping(value = "/{id_patient}/add_direction", method = RequestMethod.GET)
    public ModelAndView addPageDirection(@PathVariable("id_patient") Long id_patient) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("id_patient", id_patient);
        modelAndView.addObject("specializationsList", specializationService.allSpecialization());
        modelAndView.addObject("direction", new Direction());
        modelAndView.setViewName("admin/forms/form_direction");
        return modelAndView;
    }

    @RequestMapping(value = "/add_direction", method = RequestMethod.POST)
    public ModelAndView addDirection(@ModelAttribute("selected_spec") int selected_spec,
                                     @ModelAttribute("direction") Direction direction,
                                     @ModelAttribute("id_patient") Long id_patient) throws NoSuchAlgorithmException {
        ModelAndView modelAndView = new ModelAndView();
        Patient patient = patientService.getById(id_patient);
        if (patient.isDirectionExists(specializationService.getById(selected_spec).getName())) {
            direction.setNumber(String.valueOf(SecureRandom.getInstance("SHA1PRNG").nextInt()));
            direction.setSpecialization(specializationService.getById(selected_spec));
            direction.setStatus(true);
            direction.setPatient(patient);
            directionService.add(direction);
        }

        modelAndView.setViewName("redirect:/admin/" + patient.getRNTRC() + "/direction/2");

        return modelAndView;
    }


    @RequestMapping(value = "/{id_patient}/{id_direction}/edit_direction", method = RequestMethod.GET)
    public ModelAndView editPageDirection(@PathVariable("id_patient") Long id_patient,
                                          @PathVariable("id_direction") String id_direction) {
        Direction direction = directionService.getById(id_direction);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/forms/form_direction");
        modelAndView.addObject("id_patient", id_patient);
        modelAndView.addObject("specializationsList", specializationService.allSpecialization());
        modelAndView.addObject("direction", direction);
        return modelAndView;
    }

    @RequestMapping(value = "/add_direction", method = RequestMethod.POST, params = "edit")
    public ModelAndView editDirection(@ModelAttribute("selected_spec") int selected_spec,
                                      @ModelAttribute("direction") Direction direction,
                                      @ModelAttribute("id_patient") Long id_patient) {
        Patient patient = patientService.getById(id_patient);

        direction.setSpecialization(specializationService.getById(selected_spec));
        direction.setPatient(patient);

        directionService.add(direction);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/" + patient.getRNTRC() + "/direction/2");

        return modelAndView;
    }

    @RequestMapping(value = "/{id_patient}/{id}/delete_direction", method = RequestMethod.GET)
    public ModelAndView deleteDirection(@PathVariable("id_patient") Long id_patient,
                                        @PathVariable("id") String id) {
        Direction direction = directionService.getById(id);
        Patient patient = patientService.getById(id_patient);
        patient.getDirections().remove(direction);

        directionService.delete(direction);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/" + id_patient + "/direction/2");

        return modelAndView;
    }

    //visit

    @RequestMapping(value = "/{id_patient}/visit/{sort_num}", method = RequestMethod.GET)
    public ModelAndView allVisits(@PathVariable("id_patient") Long id_patient,
                                  @PathVariable("sort_num") int sort_num) {

        Patient patient = patientService.getById(id_patient);

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("patient", patient);
        modelAndView.addObject("visitsList", sort.sortVisit(patient.getVisits(), sort_num));

        modelAndView.setViewName("admin/tables/visit");

        return modelAndView;
    }

    @RequestMapping(value = "/{id_patient}/add_visit", method = RequestMethod.GET)
    public ModelAndView addPageVisits(@PathVariable("id_patient") Long id_patient) {

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("visit", new Visit());
        modelAndView.addObject("id_patient", id_patient);
        modelAndView.addObject("doctorList", doctorService.allDoctors());
        modelAndView.addObject("diseasesList", diseaseService.allDisease());

        modelAndView.setViewName("admin/forms/form_visit");

        return modelAndView;
    }


    @RequestMapping(value = "/add_visits", method = RequestMethod.POST)
    public ModelAndView addVisit(@ModelAttribute("visit") Visit visit,
                                 @ModelAttribute("selected_disease") String selected_disease,
                                 @ModelAttribute("selected_doctor") Long id_doctor,
                                 @ModelAttribute("id_patient") Long id_patient,
                                 @ModelAttribute("notes") String notes) throws NoSuchAlgorithmException {
        Patient patient = patientService.getById(id_patient);
        Doctor doctor = doctorService.getById(id_doctor);
        Disease disease = diseaseService.getById(selected_disease);

        String id_visit = SecureRandom.getInstance("SHA1PRNG").nextInt() + "_" + patient.getRNTRC();

        visit.setNumber(id_visit);

        if (notes == null) {
            visit.setNotes("");
        }

        visit.setDoctor(doctor);
        visit.setPatient(patient);
        visit.setDisease(disease);
        visit.setStatus(true);
        patient.addVisit(visit);

        patientService.add(patient);
        doctorService.add(doctor);//?

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/" + id_visit + "/" + patient.getRNTRC() + "/setSchedule");


        return modelAndView;
    }

    @RequestMapping(value = "/{id_patient}/{id_visit}/edit_visit", method = RequestMethod.GET)
    public ModelAndView editPageVisits(@PathVariable("id_patient") Long id_patient,
                                       @PathVariable("id_visit") String id_visit) {

        ModelAndView modelAndView = new ModelAndView();
        Patient patient = patientService.getById(id_patient);


        modelAndView.addObject("visit", patient.findVisit(id_visit));
        modelAndView.addObject("id_patient", id_patient);
        modelAndView.addObject("doctorList", doctorService.allDoctors());
        modelAndView.addObject("diseasesList", diseaseService.allDisease());

        modelAndView.setViewName("admin/forms/form_visit");

        return modelAndView;
    }


    @RequestMapping(value = "/add_visits", method = RequestMethod.POST, params = "edit")
    public ModelAndView editVisit(@ModelAttribute("visit") Visit visit,
                                  @ModelAttribute("selected_disease") String selected_disease,
                                  @ModelAttribute("selected_doctor") Long id_doctor,
                                  @ModelAttribute("id_patient") Long id_patient,
                                  @ModelAttribute("notes") String notes,
                                  @ModelAttribute("id_schedule") int id_schedule) {
        Patient patient = patientService.getById(id_patient);
        Doctor doctor = doctorService.getById(id_doctor);
        Disease disease = diseaseService.getById(selected_disease);

        if (notes == null) {
            visit.setNotes("");
        }

        visit.setDoctor(doctor);
        visit.setPatient(patient);
        visit.setDisease(disease);
        visit.setSchedule(doctor.findSchedule(id_schedule));
        patient.addVisit(visit);

        patientService.add(patient);
        doctorService.add(doctor);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/" + patient.getRNTRC() + "/visit/1");


        return modelAndView;
    }


    @RequestMapping(value = "/{id_visit}/{id_patient}/setSchedule", method = RequestMethod.GET)
    public ModelAndView PageSetSchedule(@PathVariable("id_visit") String id_visit,
                                        @PathVariable("id_patient") Long id_patient) {

        ModelAndView modelAndView = new ModelAndView();
        Patient patient = patientService.getById(id_patient);
        Visit visit = patient.findVisit(id_visit);
        Doctor doctor = visit.getDoctor();
        LocalDate date;
        date = (visit.getDate().toLocalDate());

        modelAndView.addObject("visit", visit);
        modelAndView.addObject("date", date);
        modelAndView.addObject("schedules", doctor.getSchedulesByDay(date));

        modelAndView.setViewName("admin/forms/form_set_schedule_to_visit");
        return modelAndView;
    }

    @RequestMapping(value = "/setSchedule", method = RequestMethod.POST)
    public ModelAndView setSchedule(@ModelAttribute("id_visit") String id_visit,
                                    @ModelAttribute("id_patient") Long id_patient,
                                    @ModelAttribute("selected_schedule") int selected_schedule) {

        ModelAndView modelAndView = new ModelAndView();
        Patient patient = patientService.getById(id_patient);
        Visit visit = patient.findVisit(id_visit);
        visit.setSchedule(visit.getDoctor().findSchedule(selected_schedule));
        patient.addVisit(visit);
        patientService.add(patient);


        modelAndView.setViewName("redirect:/admin/" + patient.getRNTRC() + "/visit/1");

        return modelAndView;
    }

    @RequestMapping(value = "/{id_patient}/{id}/delete_visit", method = RequestMethod.GET)
    public ModelAndView deleteVisit(@PathVariable("id_patient") Long id_patient,
                                    @PathVariable("id") String id) {
        Patient patient = patientService.getById(id_patient);
        patientService.deleteVisit(patient.findVisit(id));

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/" + id_patient + "/visit/1");

        return modelAndView;
    }

}
