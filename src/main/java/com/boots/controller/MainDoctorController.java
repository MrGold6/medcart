package com.boots.controller;

import com.boots.entity.Department;
import com.boots.entity.Doctor;
import com.boots.entity.Specialization;
import com.boots.entity.StaffingScheme;
import com.boots.repository.DepartmentRepository;
import com.boots.service.DepartmentService;
import com.boots.service.DoctorService;
import com.boots.service.PatientService;
import com.boots.service.SpecializationService;
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

import java.util.List;

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


    protected Sort sort = new Sort();

    //doctor
    @RequestMapping(value = "/doctor/{sort_num}", method = RequestMethod.GET)
    public ModelAndView allDoctorsPage(@PathVariable("sort_num") int sort_num) {
        List<Doctor> doctors = doctorService.allDoctors();

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

    @GetMapping("/{id_doctor}/doctor_info")
    public ModelAndView PageDoctorInfo(@PathVariable("id_doctor") Long id_doctor,
                                       @ModelAttribute("message") String message) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("main_doctor/tables/doctor_info");
        modelAndView.addObject("doctor", doctorService.getById(id_doctor));
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
    public ModelAndView addDepartment(@ModelAttribute("department") Department department) {
        ModelAndView modelAndView = new ModelAndView();

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

        modelAndView.addObject("doctors", doctorService.allDoctors());
        modelAndView.addObject("department", departmentService.getById(department_id));
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

        modelAndView.addObject("department", department);
        modelAndView.addObject("specializations", specializationService.allSpecialization());
        modelAndView.addObject("staffingScheme", new StaffingScheme());
        modelAndView.setViewName("main_doctor/forms/form_staffing_scheme");
        return modelAndView;
    }
//TODO: not works, problems with list?
    @PostMapping("/add_staffing_scheme")
    public ModelAndView addStaffingScheme(@ModelAttribute("staffingScheme") StaffingScheme staffingScheme,
                                          @ModelAttribute("id_department") String id_department,
                                          @ModelAttribute("selected_spec") int selected_spec) {
        ModelAndView modelAndView = new ModelAndView();
        StaffingScheme staffingScheme1 = staffingScheme;
        Department department = departmentService.getById(id_department);

        staffingScheme1.setSpecialization(specializationService.getById(selected_spec));
        staffingScheme1.setDepartment_s(department);
        department.addStaffingScheme(staffingScheme1);

        departmentService.add(department);
        modelAndView.setViewName("redirect:/main_doctor/" + id_department + "/staffing_scheme/1");

        return modelAndView;
    }

    /*

    @GetMapping("/{id}/staffing_scheme")
    public ModelAndView editPageStaffingScheme(@PathVariable("id") String id) {
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

     */
}
