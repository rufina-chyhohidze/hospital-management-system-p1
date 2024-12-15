package be.kdg.programming3.presentation;

import be.kdg.programming3.domain.Doctor;
import be.kdg.programming3.domain.Gender;
import be.kdg.programming3.domain.Patient;
import be.kdg.programming3.presentation.viewmodels.PatientForm;
import be.kdg.programming3.service.DoctorService;
import be.kdg.programming3.service.PatientService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("/patients")
public class PatientController {
    private static final Logger logger = LoggerFactory.getLogger(PatientController.class);
    private final PatientService patientService;
    private final DoctorService doctorService;

    //@Autowired
    public PatientController( PatientService patientService,DoctorService doctorService) {
        this.patientService = patientService;
        this.doctorService = doctorService;
    }

    @GetMapping
    public String getAllPatients(Model model, HttpSession session) {
        logger.info("Fetching all patients...");
       logVisit(session,"Getting all patients...");
        List<Patient> patients = patientService.getAllPatients();
        model.addAttribute("patients", patients);
        return "patients"; // returns the view called patients.html
    }

    @GetMapping ("/add")
    public String addPatientForm(Model model,HttpSession session) {
        model.addAttribute("patientForm", new PatientForm());
        logger.info("Processing patient's form...");
        logVisit(session,"Inside the patient's form...");
        return "addpatient"; // returns the form to add a patient
    }

    @GetMapping("/{patientId}")
    public String getPatientDetails(@PathVariable String patientId, Model model,HttpSession session) {
        try {
            Patient patient = patientService.findPatientById(patientId);
            List<Doctor> doctors = doctorService.getAllDoctors();
            List<Doctor> assignedDoctors = patientService.getDoctorsForPatient(patientId);
            logVisit(session,"Getting the details for patient with ID:" + " "+patientId +"...");

            model.addAttribute("patient", patient);
            model.addAttribute("allDoctors", doctors);
            model.addAttribute("assignedDoctors", assignedDoctors);
            return "patientDetails";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @RequestMapping("/delete/{patientId}")
    public String deletePatient(@PathVariable String patientId,HttpSession session) {
        logger.info("Deleting patient: " + patientId + "....");
        patientService.removePatient(patientId);
        logger.info("Patient deleted: " + patientId + " !");
        logVisit(session,"Patient with ID: " + patientId + " deleted.");
        return "redirect:/patients";
    }

    @PostMapping("/add")
    public String addPatient(@ModelAttribute("patientForm") @Valid PatientForm patientForm,
                             BindingResult bindingResult,
                             Model model,
                             HttpSession session) {
        if (bindingResult.hasErrors()) {
            logger.warn("Validation errors occurred: {}", bindingResult.getAllErrors());
            // Fetch doctors again in case of form errors
            List<Doctor> doctors = doctorService.getAllDoctors();
            model.addAttribute("doctors", doctors);
            logVisit(session,"Inside the patient's form...");
            return "addpatient";
        }

        // Convert PatientForm to Patient entity
        Patient patient = new Patient();
        patient.setPatientId(patientForm.getPatientId());
        patient.setFirstName(patientForm.getFirstName());
        patient.setLastName(patientForm.getLastName());
        patient.setAge(patientForm.getAge());
        patient.setGender(Gender.valueOf(patientForm.getGender().toUpperCase()));
        patient.setAdmissionDate(patientForm.getAdmissionDate());
        patient.setBillingAmount(patientForm.getBillingAmount());

        // Assign patient to a doctor
        if (patientForm.getDoctorId() != null) {
            Doctor assignedDoctor = doctorService.findDoctorByLicenseNumber(patientForm.getDoctorId());
            if (assignedDoctor != null) {
                patient.getDoctors().add(assignedDoctor);
            }
        }

        patientService.addPatient(patient);
        logger.info("Patient added successfully: {}", patient);
        logVisit(session, "Added Patient with ID: " + patientForm.toString());
        return "redirect:/patients";
    }
        @PostMapping("/{patientId}/assign-doctor")
        public String assignDoctorToPatient(@PathVariable String patientId,
                                            @RequestParam String doctorId,HttpSession session) {
            logger.info("Received Patient ID: " + patientId);
            logger.info("Received Doctor ID: " + doctorId);

            if (doctorId == null || doctorId.isEmpty()) {
                throw new IllegalArgumentException("Doctor ID is empty");
            }
            logVisit(session,"Assigned doctor to Patient with ID: " + patientId);
            patientService.assignDoctorToPatient(patientId, Integer.parseInt(doctorId));
            return "redirect:/patients/" + patientId;
        }

        //to be able to search for a patient by name or admission date.
    @GetMapping("/search")
    public String searchPatients(@RequestParam(required = false) String name,
                                 @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate admissionDate,
                                 Model model) {
        List<Patient> patients = patientService.getPatientsByNameOrAdmissionDate(name, admissionDate);
        model.addAttribute("patients", patients);
        return "patients"; // Reuse the patients page to display the results
    }

    public void logVisit(HttpSession session, String pageName) {
        List<Map<String, String>> visitHistory = (List<Map<String, String>>) session.getAttribute("visitHistory");
        if (visitHistory == null) {
            visitHistory = new ArrayList<>();
            session.setAttribute("visitHistory", visitHistory);
        }
        Map<String, String> visitEntry = new HashMap<>();
        visitEntry.put("page", pageName);
        visitEntry.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        visitHistory.add(visitEntry);
    }
}
