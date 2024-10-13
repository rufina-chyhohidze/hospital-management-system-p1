package be.kdg.programming3.presentation;

import be.kdg.programming3.domain.Doctor;
import be.kdg.programming3.service.DoctorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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
        logger.debug("Fetching all doctors...");
        model.addAttribute("doctors", doctorService.getAllDoctors());
        return "doctors";
    }

    @GetMapping("/add")
    public String showAddDoctorForm(Model model) {
        model.addAttribute("doctor", new Doctor());
        return "adddoctor"; //
    }
    @PostMapping("/add")
    public String addDoctor(@ModelAttribute("doctor") Doctor doctor){
        logger.debug("Adding new doctor...{}", doctor);
        doctorService.addDoctor(doctor);
        return "redirect:/doctors";
    }
}
