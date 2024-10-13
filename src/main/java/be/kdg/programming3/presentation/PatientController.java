package be.kdg.programming3.presentation;

import be.kdg.programming3.domain.Patient;
import be.kdg.programming3.service.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/patients")
public class PatientController {
    private static final Logger logger = LoggerFactory.getLogger(PatientController.class);
    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public String getAllPatients(Model model) {
        logger.debug("Fetching all patients...");
        List<Patient> patients = patientService.getAllPatients();
        model.addAttribute("patients", patients);
        return "patients"; // returns the view called patients.html
    }
    @GetMapping ("/add")
    public String addPatientForm(Model model) {
        model.addAttribute("patient", new Patient());
        return "addpatient"; // returns the form to add a patient
    }
    @PostMapping("/add")
    public String addPatient(@ModelAttribute("patient") Patient patient) {
        logger.debug("Adding new patient...{}",patient.toString());
        patientService.addPatient(patient);
        return "redirect:/patients";
    }
}
