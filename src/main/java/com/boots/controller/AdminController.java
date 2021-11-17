package com.boots.controller;

import com.boots.entity.*;
import com.boots.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
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
    protected MedicineCatalogService medicineCatalogService;

    @Autowired
    protected DiseaseService diseaseService;

    @Autowired
    protected SpecializationService specializationService;



    //functions
    public List<MedicineCatalog> sortMedicineCatalog(List<MedicineCatalog> medicineCatalog, int i) {
        switch (i) {
            case 1: {
                medicineCatalog.sort(Comparator.comparing(MedicineCatalog::getATX));
                break;
            }
            case 2: {
                medicineCatalog.sort(Comparator.comparing(MedicineCatalog::getName));
                break;
            }

        }

        return medicineCatalog;
    }

    public List<Disease> sortDisease(List<Disease> diseases, int i) {
        switch (i) {
            case 1: {
                diseases.sort(Comparator.comparing(Disease::getICD_10));
                break;
            }
            //sortSpecialization
            case 2: {
                diseases.sort(Comparator.comparing(Disease::getName));
                break;
            }

        }

        return diseases;
    }

    public List<Specialization> sortSpecialization(List<Specialization> specializations, int i) {
        switch (i) {
            case 1: {
                specializations.sort(Comparator.comparing(Specialization::getId));
                break;
            }
            //sortSpecialization
            case 2: {
                specializations.sort(Comparator.comparing(Specialization::getName));
                break;
            }

        }

        return specializations;
    }


    //sortRole
    public List<Role> sortRole(List<Role> roles, int i) {
        switch (i) {
            case 1: {
                roles.sort(Comparator.comparing(Role::getId));
                break;
            }
            //sortSpecialization
            case 2: {
                roles.sort(Comparator.comparing(Role::getName));
                break;
            }
        }

        return roles;
    }
    //patient
    @RequestMapping(value = "/patients", method = RequestMethod.GET)
    public ModelAndView allPatientsPage() {
        List<Patient> patients = patientService.allPatients();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("patientsList", patients);
        modelAndView.setViewName("admin/tables/patients");
        return modelAndView;
    }
    @GetMapping("/{id_patient}/set_user_for_patient")
    public ModelAndView addPageSetUserPatient(@PathVariable("id_patient") Long id_patient) {
        Patient patient = patientService.getById(id_patient);
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("patient", patient);
        modelAndView.addObject("users", patient.getUsers(userService.allUsers(), 3));
        modelAndView.setViewName("admin/forms/form_user_for_patient");

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

        modelAndView.setViewName("redirect:/admin/patients");

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
                                    @ModelAttribute("rh") String rh) {
        patient.setSex(Integer.parseInt(sex));
        patient.setBlood_type(Integer.parseInt(Blood_type));
        patient.setRh(rh);
        patientService.add(patient);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/patients");

        return modelAndView;
    }


    @RequestMapping(value = "/{id_patient}/delete_patient", method = RequestMethod.GET)
    public ModelAndView deletePatient(@PathVariable("id_patient") Long id_patient) {
        patientService.deletePatient(id_patient);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/patients");

        return modelAndView;
    }

    //medicineCatalog
    @RequestMapping(value = "/medicineCatalog/{sort_num}", method = RequestMethod.GET)
    public ModelAndView allMedicineCatalogPage(@PathVariable("sort_num") int sort_num) {
        List<MedicineCatalog> medicineCatalogList = patientService.allMedicines();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("medicineCatalogList", sortMedicineCatalog(medicineCatalogList, sort_num));
        modelAndView.setViewName("admin/tables/medicineCatalog");
        return modelAndView;
    }

    @RequestMapping(value = "/add_medicineCatalog", method = RequestMethod.GET)
    public ModelAndView addPageMedicine() {
        MedicineCatalog medicine = new MedicineCatalog();

        ModelAndView modelAndView = new ModelAndView();
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
            modelAndView.addObject("message","y");
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
        modelAndView.addObject("diseaseList", sortDisease(diseaseList, sort_num));
        modelAndView.setViewName("admin/tables/disease");
        return modelAndView;
    }

    @RequestMapping(value = "/add_disease", method = RequestMethod.GET)
    public ModelAndView addPageDisease() {
        Disease disease = new Disease();

        ModelAndView modelAndView = new ModelAndView();
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
        }
        else {
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

    //specialization
    @RequestMapping(value = "/specialization/{sort_num}", method = RequestMethod.GET)
    public ModelAndView allSpecializationPage(@PathVariable("sort_num") int sort_num) {
        List<Specialization> specializationList = specializationService.allSpecialization();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("specializationList", sortSpecialization(specializationList, sort_num));
        modelAndView.setViewName("admin/tables/specialization");
        return modelAndView;
    }

    @RequestMapping(value = "/add_specialization", method = RequestMethod.GET)
    public ModelAndView addPageSpecialization() {
        Specialization specialization = new Specialization();

        ModelAndView modelAndView = new ModelAndView();
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
        }
        else {
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
        ModelAndView modelAndView = new ModelAndView();
        List<User> userList = userService.allUsers();
        userList.sort(Comparator.comparing(User::getUsername));
        modelAndView.addObject("userList", userList);
       modelAndView.setViewName("admin/tables/user");
        return modelAndView;
    }

    @GetMapping("/add_user")
    public ModelAndView addPageUser() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", new User());
        modelAndView.addObject("roles", roleService.allRole());
        modelAndView.setViewName("admin/forms/form_user");

        return modelAndView;
    }

    @PostMapping("/add_user")
    public ModelAndView addUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                                @ModelAttribute("selected_role") int role_id) {

        ModelAndView modelAndView = new ModelAndView();

        user.setId((userService.allUsers().size()+1)+"_"+user.getUsername());
        user.setSelected(false);

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("admin/forms/form_user");
        }
        else if (!user.getPassword().equals(user.getPasswordConfirm())){
            modelAndView.addObject("passwordError", "Пароли не совпадают");
            modelAndView.setViewName("admin/forms/form_user");
        }
        else if (!userService.saveUser(user, role_id)){
            modelAndView.addObject("usernameError", "Пользователь с таким именем уже существует");
            modelAndView.setViewName("admin/forms/form_user");
        }
        else
        {
            modelAndView.setViewName("redirect:/admin/user/1");

        }

        return modelAndView;
    }


    @RequestMapping(value = "/change_password/{id}", method = RequestMethod.GET)
    public ModelAndView editPageUserPassword(@PathVariable("id") String id) {
        User user = userService.findUserById(id);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/forms/form_user_password");
        modelAndView.addObject("user", user);
        Optional<Role> role =  user.getRoles().stream().findFirst();
        modelAndView.addObject("my_role_id", role.orElse(new Role()).getId());

        return modelAndView;
    }

    @RequestMapping(value = "/change_password", method = RequestMethod.POST, params = "edit")
    public ModelAndView editUserPassword(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                                         @ModelAttribute("my_role_id") int my_role_id) {
        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("admin/forms/form_user_password");
        }
        else if (!user.getPassword().equals(user.getPasswordConfirm())){
            modelAndView.addObject("passwordError", "Пароли не совпадают");
            modelAndView.setViewName("admin/forms/form_user_password");
        }
        userService.editUserPassword(user, my_role_id);
        modelAndView.setViewName("redirect:/admin/"+user.getUsername()+"/edit_user");



        return modelAndView;
    }

    @RequestMapping(value = "/{username}/edit_user", method = RequestMethod.GET)
    public ModelAndView editPageUser(@PathVariable("username") String  username) {
        User user = (User) userService.loadUserByUsername(username);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/forms/form_user");
        modelAndView.addObject("user", user);
        modelAndView.addObject("roles", roleService.allRole());

        Optional<Role> role =   user.getRoles().stream().findFirst();
        modelAndView.addObject("my_role", role.orElse(new Role()));

        return modelAndView;
    }

    @RequestMapping(value = "/add_user", method = RequestMethod.POST, params = "edit")
    public ModelAndView editUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                                 @ModelAttribute("selected_role") int role_id) {
        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("admin/forms/form_user");
        }
        else if (!user.getPassword().equals(user.getPasswordConfirm())){
            modelAndView.addObject("passwordError", "Пароли не совпадают");
            modelAndView.setViewName("admin/forms/form_user");
        }
        userService.editUser(user, role_id);
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
        modelAndView.addObject("roleList", sortRole(roleService.allRole(), sort_num));
        modelAndView.setViewName("admin/tables/role");
        return modelAndView;
    }

    @RequestMapping(value = "/add_role", method = RequestMethod.GET)
    public ModelAndView addPageRole() {
        Role role = new Role();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("role", role);
        modelAndView.setViewName("admin/forms/form_role");
        return modelAndView;
    }

    @RequestMapping(value = "/add_role", method = RequestMethod.POST)
    public ModelAndView addRole(@ModelAttribute("role") Role role) {
        ModelAndView modelAndView = new ModelAndView();

        if (roleService.checkId(role.getId())) {
            roleService.add(role);
            modelAndView.setViewName("redirect:/admin/role/1");
        }
        else {
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

    //visits



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
