package com.boots.controller;

import com.boots.entity.Direction;
import com.boots.entity.Doctor;
import com.boots.entity.Patient;
import com.boots.entity.Test;
import com.boots.entity.User;
import com.boots.service.DirectionService;
import com.boots.service.DoctorService;
import com.boots.service.PatientService;
import com.boots.service.TestService;
import com.boots.transientClasses.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static com.boots.transientClasses.ControllerMainTools.currentDate;
import static com.boots.transientClasses.ControllerMainTools.getIdPatientSplit;

@RestController
@RequestMapping("/lab")
public class LaboratoryController {

    protected Sort sort = new Sort();

    @Autowired
    protected DoctorService doctorService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private DirectionService directionService;
    @Autowired
    private TestService testService;

    public Doctor getAuthDoc() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return doctorService.doctorByUser(user);
    }


    @GetMapping(value = "/patients/{sort_num}")
    public ModelAndView allPatientsPage(@PathVariable("sort_num") int sort_num) {
        Set<Patient> patients = patientService.getPatientsByDirection(getAuthDoc().getSpecialization(), true);
        Doctor doctor = getAuthDoc();
        List<Patient> patients1 = new ArrayList<>(patients);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("patientsList", sort.sortPatient(patients1, sort_num));
        modelAndView.addObject("doctor", doctor);
        modelAndView.setViewName("doctor/lab/pages/patients");
        return modelAndView;
    }

    @GetMapping(value = "/patients/searchTelephone_number")
    public ModelAndView allPatientsWithCurrentTelephone_number(@ModelAttribute("telephone_number") int telephone_number) {
        Doctor doctor = getAuthDoc();
        List<Patient> patients = patientService.getPatientsByDirection(getAuthDoc().getSpecialization(), true, telephone_number);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("patientsList", patients);
        modelAndView.addObject("doctor", doctor);
        modelAndView.setViewName("doctor/lab/pages/patients");
        return modelAndView;
    }

    @GetMapping(value = "/{patient_id}/tests")
    public ModelAndView allDirectionsByPatients(@PathVariable("patient_id") Long patient_id) {
        Doctor doctor = getAuthDoc();
        List<Direction> directions = directionService.getActiveBySpecializationAndPatient(doctor.getSpecialization(), patientService.getById(patient_id));

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("directionsList", directions);
        modelAndView.addObject("patient", patientService.getById(patient_id));
        modelAndView.addObject("doctor", doctor);
        modelAndView.setViewName("doctor/lab/pages/directions");
        return modelAndView;
    }

    @GetMapping("/doneTests")
    public ModelAndView allTests() {
        Doctor doctor = getAuthDoc();
        List<Test> tests = doctor.getTests();
        tests.sort(Comparator.comparing(Test::getDate).reversed());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("testList", tests);
        modelAndView.addObject("doctor", doctor);
        modelAndView.setViewName("doctor/lab/pages/tests");
        return modelAndView;
    }

    @GetMapping("/{test_id}")
    public ModelAndView TestPage(@PathVariable("test_id") String test_id) {
        Doctor doctor = getAuthDoc();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("test", patientService.getById(getIdPatientSplit(test_id)).findTest(test_id));
        modelAndView.addObject("doctor", doctor);
        modelAndView.setViewName("doctor/lab/pages/test");
        return modelAndView;
    }

    @GetMapping(value = "/{patient_id}/test/{direction_id}")
    public ModelAndView addTestPage(@PathVariable("patient_id") Long patient_id,
                                    @PathVariable("direction_id") String direction_id) {
        Doctor doctor = getAuthDoc();
        Test test = new Test();
        test.setTestsType(directionService.getById(direction_id).getTestsType());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("test", test);
        modelAndView.addObject("doctor", doctor);
        modelAndView.addObject("patient_id", patient_id);

        modelAndView.setViewName("doctor/lab/form/create_form/test");
        return modelAndView;
    }


    @PostMapping(value = "/add_test")
    public ModelAndView addTest(@ModelAttribute("test") Test test,
                                @ModelAttribute("patient_id") Long patient_id,
                                @ModelAttribute("direction_id") String direction_id,
                                @ModelAttribute("result") String result) throws NoSuchAlgorithmException {

        Patient patient = patientService.getById(patient_id);
        Direction direction = directionService.getById(direction_id);
        Doctor doctor = getAuthDoc();

        String id_test = "test-" + SecureRandom.getInstance("SHA1PRNG").nextInt() + "_" + patient.getRNTRC();
        test.setId(id_test);
        test.setDate(currentDate());
        test.setTestsType(direction.getTestsType());

        if (result == null) {
            test.setResult("");
        }
        test.setDoc(doctor);
        test.setPatient(patient);

        testService.add(test);

        direction.setStatus(false);
        directionService.add(direction);

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("redirect:/lab/" + patient.getRNTRC() + "/tests");

        return modelAndView;
    }


}
