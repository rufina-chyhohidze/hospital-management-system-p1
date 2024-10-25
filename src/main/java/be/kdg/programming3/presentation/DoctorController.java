package be.kdg.programming3.presentation;

import be.kdg.programming3.domain.Department;
import be.kdg.programming3.domain.Doctor;
import be.kdg.programming3.domain.Gender;
import be.kdg.programming3.domain.Patient;
import be.kdg.programming3.presentation.viewmodels.DoctorForm;
import be.kdg.programming3.service.DoctorService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/doctors")
public class DoctorController {
    private static final Logger logger = LoggerFactory.getLogger(DoctorController.class);
    private final DoctorService doctorService;

    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }
    @GetMapping
    public String getAllDoctors(Model model){
        logger.info("Fetching all doctors...");
        model.addAttribute("doctors", doctorService.getAllDoctors());
        return "doctors";
    }

    @GetMapping("/add")
    public String showAddDoctorForm(Model model) {
        logger.info("Processing doctor's form...");
        model.addAttribute("doctorForm", new DoctorForm());
        return "adddoctor"; //
    }
    @GetMapping("/{licenseNumber}")
    public String getDoctorDetails(@PathVariable int licenseNumber, Model model) {
        Doctor doctor = doctorService.findDoctorByLicenseNumber(licenseNumber);
        if (doctor != null) {
            model.addAttribute("doctor",doctor);
            return "doctorDetails";
        }
        return "redirect:/doctors";
    }

    @PostMapping("/add")
    public String addDoctor(@ModelAttribute("doctorForm")@Valid DoctorForm doctorForm, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()) {
            logger.warn("Validation errors: {}", bindingResult.getAllErrors());
            return "adddoctor"; //it returns to the form if validation fails
        }
        // Convert PatientForm to Patient entity and add to service
        Doctor doctor = new Doctor();
        doctor.setFirstName(doctorForm.getFirstName());
        doctor.setLastName(doctorForm.getLastName());
        doctor.setLicenseNumber(doctorForm.getLicenseNumber());
        doctor.setSalary(doctorForm.getSalary());
        doctor.setDepartment(Department.valueOf(doctorForm.getDepartment().toUpperCase()));
        doctor.setHireDate(doctorForm.getHireDate());
        doctor.setGender(Gender.valueOf(doctorForm.getGender().toUpperCase()));;

        doctorService.addDoctor(doctor);
        logger.info("Successfully added a new patient: {}", doctorForm.toString());
        return "redirect:/doctors";
    }

}
