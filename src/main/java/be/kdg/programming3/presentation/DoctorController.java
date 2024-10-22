package be.kdg.programming3.presentation;

import be.kdg.programming3.domain.Doctor;
import be.kdg.programming3.service.DoctorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        model.addAttribute("doctor", new Doctor());
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
    public String addDoctor(@ModelAttribute("doctor") Doctor doctor){
        logger.info("Adding new doctor...{}", doctor);
        doctorService.addDoctor(doctor);
        return "redirect:/doctors";
    }

}
