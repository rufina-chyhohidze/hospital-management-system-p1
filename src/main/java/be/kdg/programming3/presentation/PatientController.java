package be.kdg.programming3.presentation;

import be.kdg.programming3.domain.Gender;
import be.kdg.programming3.domain.Patient;
import be.kdg.programming3.presentation.viewmodels.PatientForm;
import be.kdg.programming3.service.PatientService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.LinkedList;
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
    public String getAllPatients(Model model, HttpSession session) {
        logger.info("Fetching all patients...");
        trackPageVisit(session,"/patients");
        List<Patient> patients = patientService.getAllPatients();
        model.addAttribute("patients", patients);
        return "patients"; // returns the view called patients.html
    }
    @GetMapping ("/add")
    public String addPatientForm(Model model,HttpSession session) {
        model.addAttribute("patientForm", new PatientForm());
        logger.info("Processing patient's form...");
        trackPageVisit(session,"/patients/add");
        return "addpatient"; // returns the form to add a patient
    }

    @GetMapping("/{patientId}")
    public String getPatientDetails(@PathVariable String patientId, Model model,HttpSession session) {
        Patient patient = patientService.findPatientById(patientId);
        trackPageVisit(session,"/patients/"+patientId);
        if (patient != null) {
            model.addAttribute("patient", patient);
            return "patientDetails";
        }
        return "redirect:/patients";
    }

    @PostMapping("/add")
    public String addPatient(@ModelAttribute("patientForm")@Valid PatientForm patientForm, BindingResult bindingResult, Model model,HttpSession session) {
        if(bindingResult.hasErrors()) {
            logger.warn("Validation errors: {}", bindingResult.getAllErrors());
            return "addpatient"; //it returns to the form if validation fails
        }
        // Convert PatientForm to Patient entity and add to service
        Patient patient = new Patient();
        patient.setFirstName(patientForm.getFirstName());
        patient.setLastName(patientForm.getLastName());
        patient.setAge(patientForm.getAge());
        patient.setPatientId(patientForm.getPatientId());
        // Convert gender String to Gender enum using Gender.valueOf
        patient.setGender(Gender.valueOf(patientForm.getGender().toUpperCase()));
        patient.setAdmissionDate(patientForm.getAdmissionDate());
        patient.setBillingAmount(patientForm.getBillingAmount());

        patientService.addPatient(patient);
        logger.info("Successfully added a new patient: {}", patientForm.toString());
        trackPageVisit(session,"/patients/add (submission)");
        return "redirect:/patients";
    }
    private void trackPageVisit(HttpSession session, String pageUrl) {
        List<String> visitHistory = (List<String>) session.getAttribute("visitHistory");
        if (visitHistory == null) {
            logger.debug("Initializing new session visit history");
            visitHistory = new LinkedList<>();
        }

        String visitEntry = "Visited: " + pageUrl + " at " + LocalDateTime.now();
        visitHistory.add(visitEntry);
        session.setAttribute("visitHistory", visitHistory);
        logger.debug("Page visit tracked: {}", visitEntry);
    }

    @GetMapping("/session-history")
    public String showSessionHistory(HttpSession session, Model model) {
        logger.info("Accessed session history page");

        List<String> visitHistory = (List<String>) session.getAttribute("visitHistory");
        if (visitHistory == null) {
            logger.debug("No visit history found in session, initializing empty list");
            visitHistory = new LinkedList<>();
        }

        model.addAttribute("visitHistory", visitHistory);
        logger.debug("Session history loaded with {} entries", visitHistory.size());
        return "session-history"; // returns the session-history.html view
    }
//
}
