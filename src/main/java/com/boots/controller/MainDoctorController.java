package com.boots.controller;

import com.boots.entity.Department;
import com.boots.entity.Disease;
import com.boots.entity.Doctor;
import com.boots.entity.Specialization;
import com.boots.entity.StaffingScheme;
import com.boots.entity.Unit;
import com.boots.repository.DepartmentRepository;
import com.boots.service.DepartmentService;
import com.boots.service.DiseaseService;
import com.boots.service.DoctorService;
import com.boots.service.PatientService;
import com.boots.service.SpecializationService;
import com.boots.service.StaffingSchemeService;
import com.boots.service.UnitService;
import com.boots.transientClasses.DiseaseStatistic;
import com.boots.transientClasses.DoctorStatistic;
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

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.boots.transientClasses.ControllerMainTools.firstMinusSecondArraysDepartment;
import static com.boots.transientClasses.ControllerMainTools.firstMinusSecondArraysDoctor;
import static com.boots.transientClasses.ControllerMainTools.firstMinusSecondArraysSpecialization;

@RestController
@RequestMapping("/main_doctor")
public class MainDoctorController {

    @Autowired
    protected SpecializationService specializationService;

    @Autowired
    protected PatientService patientService;

    @Autowired
    protected DoctorService doctorService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private StaffingSchemeService staffingSchemeService;

    @Autowired
    private UnitService unitService;

    @Autowired
    protected DiseaseService diseaseService;

    protected Sort sort = new Sort();

    //familyDoctor

    @GetMapping("/family_doctor/{sort_num}")
    public ModelAndView allFamilyDoctorsPage(@PathVariable("sort_num") int sort_num) {
        List<Doctor> doctors = doctorService.doctorBySpecialization(specializationService.getById(1));

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("doctorList", sort.sortDoctor(doctors, sort_num));
        modelAndView.setViewName("main_doctor/tables/familyDoctor");
        return modelAndView;
    }

    @GetMapping(value = "/family_doctor/1", params = "search")
    public ModelAndView allFamilyDoctorsWithCurrentTelephone_number(@ModelAttribute("telephone_number") int telephone_number) {

        List<Doctor> doctorsByTelephone = new ArrayList<>();
        for (Doctor doctor : doctorService.doctorBySpecialization(specializationService.getById(1))) {
            if (doctor.getTelephone_number() == telephone_number) {
                doctorsByTelephone.add(doctor);
            }
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("doctorList", sort.sortDoctor(doctorsByTelephone, 1));
        modelAndView.setViewName("main_doctor/tables/familyDoctor");

        return modelAndView;
    }

    @GetMapping("/{id}/edit_family_doctor")
    public ModelAndView editPageFamilyDoctorCountOfDeclaration(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("doctor", doctorService.getById(id));

        modelAndView.setViewName("main_doctor/forms/form_family_doctor");
        return modelAndView;
    }

    @PostMapping(value = "/add_family_doctor", params = "edit")
    public ModelAndView editFamilyDoctorCountOfDeclaration(@ModelAttribute("maxCount") int maxCount,
                                                           @ModelAttribute("id_doc") Long id_doc) {
        Doctor doctor = doctorService.getById(id_doc);
        doctor.setMaxCountOfDeclaration(maxCount);
        doctor.setCountOfDeclaration();
        doctorService.add(doctor);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/main_doctor/family_doctor/1");

        return modelAndView;
    }

    @GetMapping("/add_family_doctor")
    public ModelAndView addPageAllFamilyDoctorCountOfDeclaration() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("doctor", new Doctor());

        modelAndView.setViewName("main_doctor/forms/form_family_doctor");
        return modelAndView;
    }

    @PostMapping("/add_family_doctor")
    public ModelAndView addAllFamilyDoctorCountOfDeclaration(@ModelAttribute("maxCount") int maxCount) {

        for (Doctor doctor : doctorService.doctorBySpecialization(specializationService.getById(1))) {
            doctor.setMaxCountOfDeclaration(maxCount);
            doctor.setCountOfDeclaration();
            doctorService.add(doctor);
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/main_doctor/family_doctor/1");

        return modelAndView;
    }

    //doctor
    @RequestMapping(value = "/doctor/{sort_num}", method = RequestMethod.GET)
    public ModelAndView allDoctorsPage(@PathVariable("sort_num") int sort_num) {
        List<Doctor> doctors = doctorService.doctorByNotSpecialization(specializationService.getById(10));

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("doctorList", sort.sortDoctor(doctors, sort_num));
        modelAndView.setViewName("main_doctor/tables/doctor");
        return modelAndView;
    }

    @RequestMapping(value = "/doctor/1", method = RequestMethod.GET, params = "search")
    public ModelAndView allDoctorsWithCurrentTelephone_number(@ModelAttribute("telephone_number") int telephone_number) {
        List<Doctor> doctors = doctorService.findTelephone_number(telephone_number);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("doctorList", sort.sortDoctor(doctors, 1));
        modelAndView.setViewName("main_doctor/tables/doctor");

        return modelAndView;
    }

    @GetMapping("/{id_doctor}/doctor_info/done")
    public ModelAndView PageDoctorDone(@PathVariable("id_doctor") Long id_doctor,
                                       @ModelAttribute("message") String message) {

        LocalDate currentdate = LocalDate.now();
        Month month = currentdate.getMonth();

        Doctor doctor = doctorService.getById(id_doctor);
        List<DoctorStatistic> countVisits = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            countVisits.add(new DoctorStatistic(i, doctor.getDoneVisitsByWeek(i, month.getValue()).size()));
        }

        int maxCountDisease = countVisits.stream()
                .max(Comparator.comparingInt(DoctorStatistic::getCount))
                .get().getCount();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("countVisits", countVisits);
        modelAndView.addObject("date", month.getValue());
        modelAndView.addObject("maxCountDisease", maxCountDisease);
        modelAndView.addObject("doctor", doctorService.getById(id_doctor));

        modelAndView.setViewName("main_doctor/tables/doctor_info_done_visits");
        return modelAndView;
    }

    @GetMapping(value = "/{id_doctor}/doctor_info/done", params = "search")
    public ModelAndView DoctorInfoByMonthDonePage(@PathVariable("id_doctor") Long id_doctor,
                                                  @ModelAttribute("message") String message,
                                                  @ModelAttribute("month") int month) {

        Doctor doctor = doctorService.getById(id_doctor);
        List<DoctorStatistic> countVisits = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            countVisits.add(new DoctorStatistic(i, doctor.getDoneVisitsByWeek(i, month).size()));
        }

        int maxCountDisease = countVisits.stream()
                .max(Comparator.comparingInt(DoctorStatistic::getCount))
                .get().getCount();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("countVisits", countVisits);
        modelAndView.addObject("date", month);
        modelAndView.addObject("maxCountDisease", maxCountDisease);
        modelAndView.addObject("doctor", doctorService.getById(id_doctor));

        modelAndView.setViewName("main_doctor/tables/doctor_info_done_visits");
        return modelAndView;
    }

    @GetMapping("/{id_doctor}/doctor_info")
    public ModelAndView PageDoctorInfo(@PathVariable("id_doctor") Long id_doctor,
                                       @ModelAttribute("message") String message) {

        LocalDate currentdate = LocalDate.now();
        Month month = currentdate.getMonth();

        Doctor doctor = doctorService.getById(id_doctor);
        List<DoctorStatistic> countVisits = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            countVisits.add(new DoctorStatistic(i, doctor.getActiveVisitsByWeek(i, month.getValue()).size()));
        }

        int maxCountDisease = countVisits.stream()
                .max(Comparator.comparingInt(DoctorStatistic::getCount))
                .get().getCount();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("countVisits", countVisits);
        modelAndView.addObject("date", month.getValue());
        modelAndView.addObject("maxCountDisease", maxCountDisease);
        modelAndView.addObject("doctor", doctorService.getById(id_doctor));

        modelAndView.setViewName("main_doctor/tables/doctor_info");
        return modelAndView;
    }

    @GetMapping(value = "/{id_doctor}/doctor_info", params = "search")
    public ModelAndView DoctorInfoByMonthPage(@PathVariable("id_doctor") Long id_doctor,
                                              @ModelAttribute("message") String message,
                                              @ModelAttribute("month") int month) {

        Doctor doctor = doctorService.getById(id_doctor);
        List<DoctorStatistic> countVisits = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            countVisits.add(new DoctorStatistic(i, doctor.getActiveVisitsByWeek(i, month).size()));
        }

        int maxCountDisease = countVisits.stream()
                .max(Comparator.comparingInt(DoctorStatistic::getCount))
                .get().getCount();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("countVisits", countVisits);
        modelAndView.addObject("date", month);
        modelAndView.addObject("maxCountDisease", maxCountDisease);
        modelAndView.addObject("doctor", doctorService.getById(id_doctor));

        modelAndView.setViewName("main_doctor/tables/doctor_info");
        return modelAndView;
    }


    //specialization
    @GetMapping("/specialization/{sort_num}")
    public ModelAndView allSpecializationPage(@PathVariable("sort_num") int sort_num) {
        List<Specialization> specializationList = specializationService.allSpecialization();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("specializationList", sort.sortSpecialization(specializationList, sort_num));
        modelAndView.setViewName("main_doctor/tables/specialization");
        return modelAndView;
    }

    @GetMapping("/add_specialization")
    public ModelAndView addPageSpecialization(@ModelAttribute("message") String message) {
        Specialization specialization = new Specialization();

        ModelAndView modelAndView = new ModelAndView();
        if (message.equals("y")) {
            modelAndView.addObject("message", message);
        } else {
            modelAndView.addObject("message", null);
        }

        modelAndView.addObject("specialization", specialization);
        modelAndView.setViewName("main_doctor/forms/form_specialization");
        return modelAndView;
    }


    @PostMapping("/add_specialization")
    public ModelAndView addSpecialization(@ModelAttribute("specialization") Specialization specialization) {
        ModelAndView modelAndView = new ModelAndView();

        if (specializationService.checkId(specialization.getId())) {
            specializationService.add(specialization);
            modelAndView.setViewName("redirect:/main_doctor/specialization/1");
        } else {
            modelAndView.addObject("message", "y");
            modelAndView.setViewName("redirect:/main_doctor/add_specialization");
        }

        return modelAndView;
    }

    @GetMapping("/{id}/edit_specialization")
    public ModelAndView editPageSpecialization(@PathVariable("id") int id) {
        Specialization specialization = specializationService.getById(id);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("main_doctor/forms/form_specialization");
        modelAndView.addObject("specialization", specialization);
        return modelAndView;
    }

    @PostMapping(value = "/add_specialization", params = "edit")
    public ModelAndView editSpecialization(@ModelAttribute("specialization") Specialization specialization) {
        specializationService.add(specialization);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/main_doctor/specialization/1");

        return modelAndView;
    }

    @GetMapping("/{id}/delete_specialization")
    public ModelAndView deleteSpecialization(@PathVariable("id") int id) {
        Specialization specialization = specializationService.getById(id);
        specializationService.delete(specialization);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/main_doctor/specialization/1");

        return modelAndView;
    }

    //unit
    @GetMapping("/unit/{sort_num}")
    public ModelAndView allUnitsPage(@PathVariable("sort_num") int sort_num) {
        List<Unit> unitList = unitService.allUnit();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("unitList", sort.sortUnit(unitList, sort_num));
        modelAndView.setViewName("main_doctor/tables/unit");
        return modelAndView;
    }

    @GetMapping("/add_unit")
    public ModelAndView addPageUnit(@ModelAttribute("message") String message) {
        Unit unit = new Unit();

        ModelAndView modelAndView = new ModelAndView();
        if (message.equals("y")) {
            modelAndView.addObject("message", message);
        } else {
            modelAndView.addObject("message", null);
        }

        modelAndView.addObject("unit", unit);
        modelAndView.setViewName("main_doctor/forms/form_unit");
        return modelAndView;
    }


    @PostMapping("/add_unit")
    public ModelAndView addUnit(@ModelAttribute("unit") Unit unit) throws NoSuchAlgorithmException {
        ModelAndView modelAndView = new ModelAndView();
        String id_unit = SecureRandom.getInstance("SHA1PRNG").nextInt() + "";

        unit.setId(id_unit);
        unitService.add(unit);

        modelAndView.setViewName("redirect:/main_doctor/unit/1");

        return modelAndView;
    }

    @GetMapping("/{id}/edit_unit")
    public ModelAndView editPageUnit(@PathVariable("id") String id) {
        Unit unit = unitService.getById(id);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("main_doctor/forms/form_unit");
        modelAndView.addObject("unit", unit);
        return modelAndView;
    }

    @PostMapping(value = "/add_unit", params = "edit")
    public ModelAndView editUnit(@ModelAttribute("unit") Unit unit) {
        unitService.add(unit);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/main_doctor/unit/1");

        return modelAndView;
    }

    @GetMapping("/{id}/delete_unit")
    public ModelAndView deleteUnit(@PathVariable("id") String id) {
        Unit unit = unitService.getById(id);
        unitService.delete(unit);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/main_doctor/unit/1");

        return modelAndView;
    }

    @GetMapping("/{unit_id}/unit_department/{sort_num}")
    public ModelAndView allUnitDepartmentsPage(@PathVariable("sort_num") int sort_num,
                                               @PathVariable("unit_id") String unit_id) {
        Unit unit = unitService.getById(unit_id);
        List<Department> departmentList = unit.getDepartmentList();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("departmentList", departmentList);
        modelAndView.addObject("canAdd", !departmentList.isEmpty());
        modelAndView.addObject("unit", unit);
        modelAndView.setViewName("main_doctor/tables/unit_department");
        return modelAndView;
    }


    @GetMapping("/{unit_id}/add_unit_to_department")
    public ModelAndView addPageDepartmentToUnit(@ModelAttribute("message") String message,
                                                @PathVariable("unit_id") String unit_id) {

        ModelAndView modelAndView = new ModelAndView();
        if (message.equals("y")) {
            modelAndView.addObject("message", message);
        } else {
            modelAndView.addObject("message", null);
        }

        Unit unit = unitService.getById(unit_id);

        modelAndView.addObject("departmentList", firstMinusSecondArraysDepartment(departmentService.allDepartmentWithoutUnit(), unit.getDepartmentList()));
        modelAndView.addObject("unit", unit);
        modelAndView.setViewName("main_doctor/forms/form_department_for_unit");
        return modelAndView;
    }


    @PostMapping("/add_unit_to_department")
    public ModelAndView addDepartmentToUnit(@ModelAttribute("department_id") String department_id,
                                            @ModelAttribute("id_unit") String id_unit) {
        ModelAndView modelAndView = new ModelAndView();

        Unit unit = unitService.getById(id_unit);

        unit.addDepartment(departmentService.getById(department_id));
        unitService.add(unit);
        modelAndView.setViewName("redirect:/main_doctor/" + id_unit + "/unit_department/1");

        return modelAndView;
    }

    @GetMapping("/{id_unit}/{department_id}/delete_department_from_unit")
    public ModelAndView deleteDepartmentFromUnit(@PathVariable("department_id") String department_id,
                                                 @PathVariable("id_unit") String id_unit) {
        ModelAndView modelAndView = new ModelAndView();

        Unit unit = unitService.getById(id_unit);

        unit.removeDepartment(departmentService.getById(department_id));
        unitService.add(unit);
        modelAndView.setViewName("redirect:/main_doctor/" + id_unit + "/unit_department/1");

        return modelAndView;
    }

    //department
    @GetMapping("/department/{sort_num}")
    public ModelAndView allDepartmentsPage(@PathVariable("sort_num") int sort_num) {
        List<Department> departmentList = departmentService.allDepartment();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("departmentList", departmentList);
        modelAndView.setViewName("main_doctor/tables/department");
        return modelAndView;
    }


    @GetMapping("/add_department")
    public ModelAndView addPageDepartment(@ModelAttribute("message") String message) {
        Department department = new Department();

        ModelAndView modelAndView = new ModelAndView();
        if (message.equals("y")) {
            modelAndView.addObject("message", message);
        } else {
            modelAndView.addObject("message", null);
        }

        modelAndView.addObject("department", department);
        modelAndView.setViewName("main_doctor/forms/form_department");
        return modelAndView;
    }


    @PostMapping("/add_department")
    public ModelAndView addDepartment(@ModelAttribute("department") Department department) throws NoSuchAlgorithmException {
        ModelAndView modelAndView = new ModelAndView();
        String id_department = SecureRandom.getInstance("SHA1PRNG").nextInt() + "";
        department.setId(id_department);


        if (departmentService.checkId(department.getId())) {
            departmentService.add(department);
            modelAndView.setViewName("redirect:/main_doctor/department/1");
        } else {
            modelAndView.addObject("message", "y");
            modelAndView.setViewName("redirect:/main_doctor/add_department");
        }

        return modelAndView;
    }


    @GetMapping("/{id}/edit_department")
    public ModelAndView editPageDepartment(@PathVariable("id") String id) {
        Department department = departmentService.getById(id);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("main_doctor/forms/form_department");
        modelAndView.addObject("department", department);
        return modelAndView;
    }

    @PostMapping(value = "/add_department", params = "edit")
    public ModelAndView editDepartment(@ModelAttribute("department") Department department) {
        departmentService.add(department);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/main_doctor/department/1");

        return modelAndView;
    }

    @GetMapping("/{id}/delete_department")
    public ModelAndView deleteDepartment(@PathVariable("id") String id) {
        Department department = departmentService.getById(id);
        departmentService.delete(department);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/main_doctor/department/1");

        return modelAndView;
    }

    @GetMapping("/{department_id}/department_doctor/{sort_num}")
    public ModelAndView allDepartmentDoctorsPage(@PathVariable("sort_num") int sort_num,
                                                 @PathVariable("department_id") String department_id) {
        Department department = departmentService.getById(department_id);
        List<Doctor> doctors = departmentService.getById(department_id).getDoctors();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("doctorList", sort.sortDoctor(doctors, sort_num));
        modelAndView.addObject("canAdd", !department.getStaffingSchemes().isEmpty());
        modelAndView.addObject("department", department);
        modelAndView.setViewName("main_doctor/tables/department_doctor");
        return modelAndView;
    }

    @GetMapping(value = "/{department_id}/department_doctor/1", params = "search")
    public ModelAndView allDepartmentDoctorsWithCurrentTelephone_number(@ModelAttribute("telephone_number") int telephone_number,
                                                                        @PathVariable("department_id") String department_id) {
        Department department = departmentService.getById(department_id);
        List<Doctor> doctors = department.findByTelephone(telephone_number);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("canAdd", !department.getStaffingSchemes().isEmpty());

        modelAndView.addObject("doctorList", sort.sortDoctor(doctors, 1));
        modelAndView.addObject("department", department);
        modelAndView.setViewName("main_doctor/tables/department_doctor");

        return modelAndView;
    }


    @GetMapping("/{department_id}/add_doctor_to_department")
    public ModelAndView addPageDoctorToDepartment(@ModelAttribute("message") String message,
                                                  @PathVariable("department_id") String department_id) {

        ModelAndView modelAndView = new ModelAndView();
        if (message.equals("y")) {
            modelAndView.addObject("message", message);
        } else {
            modelAndView.addObject("message", null);
        }

        Department department = departmentService.getById(department_id);


        List<Doctor> doctors = new ArrayList<>();
        for (Specialization specialization : department.getSpecializationListByScheme()) {
            doctors.addAll(doctorService.doctorBySpecialization(specialization));
        }

        modelAndView.addObject("doctors", firstMinusSecondArraysDoctor(doctors, department.getDoctors()));
        modelAndView.addObject("department", department);
        modelAndView.setViewName("main_doctor/forms/form_doctor_for_department");
        return modelAndView;
    }


    @PostMapping("/add_doctor_to_department")
    public ModelAndView addDoctorToDepartment(@ModelAttribute("doctor_id") Long doctor_id,
                                              @ModelAttribute("id_department") String id_department) {
        ModelAndView modelAndView = new ModelAndView();

        Department department = departmentService.getById(id_department);

        department.addDoctor(doctorService.getById(doctor_id));
        departmentService.add(department);
        modelAndView.setViewName("redirect:/main_doctor/" + id_department + "/department_doctor/1");

        return modelAndView;
    }

    @GetMapping("/{department_id}/{doctor_id}/delete_doctor_from_department")
    public ModelAndView deleteDoctorFromDepartment(@PathVariable("department_id") String department_id,
                                                   @PathVariable("doctor_id") Long doctor_id) {
        ModelAndView modelAndView = new ModelAndView();

        Department department = departmentService.getById(department_id);

        department.removeDoctor(doctorService.getById(doctor_id));
        departmentService.add(department);

        modelAndView.setViewName("redirect:/main_doctor/" + department_id + "/department_doctor/1");

        return modelAndView;
    }

    //staffing_scheme
    @GetMapping("/{department_id}/staffing_scheme/{sort_num}")
    public ModelAndView allStaffingSchemePage(@PathVariable("sort_num") int sort_num,
                                              @PathVariable("department_id") String department_id) {
        Department department = departmentService.getById(department_id);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("department", department);
        modelAndView.addObject("staffingSchemeList", department.getStaffingSchemes());
        modelAndView.setViewName("main_doctor/tables/staffing_scheme");
        return modelAndView;
    }


    @GetMapping("/{department_id}/add_staffing_scheme")
    public ModelAndView addStaffingScheme(@ModelAttribute("message") String message,
                                          @PathVariable("department_id") String department_id) {
        Department department = departmentService.getById(department_id);

        ModelAndView modelAndView = new ModelAndView();
        if (message.equals("y")) {
            modelAndView.addObject("message", message);
        } else {
            modelAndView.addObject("message", null);
        }
        List<Specialization> specializationsList = specializationService.allSpecialization();
        specializationsList.remove(doctorService.getByIdSpecialization(10));

        List<Specialization> specializations = firstMinusSecondArraysSpecialization(specializationsList, department.getSpecializationListByScheme());
        specializations.sort(Comparator.comparing(Specialization::getName));

        modelAndView.addObject("department", department);
        modelAndView.addObject("specializations", specializations);
        modelAndView.addObject("staffingScheme", new StaffingScheme());
        modelAndView.setViewName("main_doctor/forms/form_staffing_scheme");
        return modelAndView;
    }

    @PostMapping("/add_staffing_scheme")
    public ModelAndView addStaffingScheme(@ModelAttribute("staffingScheme") StaffingScheme staffingScheme,
                                          @ModelAttribute("id_department") String id_department,
                                          @ModelAttribute("selected_spec") int selected_spec) throws NoSuchAlgorithmException {
        ModelAndView modelAndView = new ModelAndView();

        Department department = departmentService.getById(id_department);
        staffingScheme.setNumber(SecureRandom.getInstance("SHA1PRNG").nextInt() + "_" + id_department + "_" + selected_spec);

        staffingScheme.setSpecialization(specializationService.getById(selected_spec));
        staffingScheme.setDepartment_s(department);

        staffingScheme.setCountDueToMaxCount(department.findBySpecialization(staffingScheme.getSpecialization()).size());
        department.addStaffingScheme(staffingScheme);

        departmentService.add(department);
        modelAndView.setViewName("redirect:/main_doctor/" + id_department + "/staffing_scheme/1");

        return modelAndView;
    }


    @GetMapping("/{id_department}/{id}/edit_staffing_scheme")
    public ModelAndView editPageStaffingScheme(@PathVariable("id") String id,
                                               @PathVariable("id_department") String id_department) {
        Department department = departmentService.getById(id_department);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("main_doctor/forms/form_staffing_scheme");
        modelAndView.addObject("department", department);
        modelAndView.addObject("specializations", specializationService.allSpecialization());
        modelAndView.addObject("staffingScheme", staffingSchemeService.getById(id));
        return modelAndView;
    }

    @PostMapping(value = "/add_staffing_scheme", params = "edit")
    public ModelAndView editStaffingScheme(@ModelAttribute("staffingScheme") StaffingScheme staffingScheme,
                                           @ModelAttribute("id_department") String id_department,
                                           @ModelAttribute("selected_spec") int selected_spec) {
        Department department = departmentService.getById(id_department);

        staffingScheme.setSpecialization(specializationService.getById(selected_spec));
        staffingScheme.setDepartment_s(departmentService.getById(id_department));
        staffingScheme.setCountDueToMaxCount(department.findBySpecialization(staffingScheme.getSpecialization()).size());

        staffingSchemeService.add(staffingScheme);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/main_doctor/" + id_department + "/staffing_scheme/1");

        return modelAndView;
    }

    @GetMapping("/{id_department}/{id}/delete_staffing_scheme")
    public ModelAndView deleteStaffingScheme(@PathVariable("id") String id,
                                             @PathVariable("id_department") String id_department) {
        Department department = departmentService.getById(id_department);
        department.removeStaffingScheme(id);
        departmentService.add(department);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/main_doctor/" + id_department + "/staffing_scheme/1");

        return modelAndView;
    }


    //disease
    @GetMapping("/disease")
    public ModelAndView allDiseasePage() {

        LocalDate currentdate = LocalDate.now();
        Month month = currentdate.getMonth();

        List<DiseaseStatistic> countDiseases = diseaseService.countDiseaseMonth(month.getValue());
        Long maxCountDisease = countDiseases.size() > 0 ? countDiseases.stream()
                .max(Comparator.comparingLong(DiseaseStatistic::getCount))
                .get().getCount() : 0;

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("countDiseases", countDiseases);
        modelAndView.addObject("date", month.getValue());
        modelAndView.addObject("maxCountDisease", maxCountDisease);
        modelAndView.setViewName("main_doctor/tables/disease_chart");
        return modelAndView;
    }

    @GetMapping(value = "/disease", params = "search")
    public ModelAndView allDiseaseByMonthPage(@ModelAttribute("month") int month) {
        List<DiseaseStatistic> countDiseases = diseaseService.countDiseaseMonth(month);
        Long maxCountDisease = countDiseases.size() > 0 ? countDiseases.stream()
                .max(Comparator.comparingLong(DiseaseStatistic::getCount))
                .get().getCount() : 0;

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("countDiseases", countDiseases);
        modelAndView.addObject("date", month);
        modelAndView.addObject("maxCountDisease", maxCountDisease);
        modelAndView.setViewName("main_doctor/tables/disease_chart");
        return modelAndView;
    }
}
