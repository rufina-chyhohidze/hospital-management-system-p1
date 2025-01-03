package be.kdg.programming3.presentation;

import be.kdg.programming3.domain.Doctor;
import be.kdg.programming3.domain.Patient;
import be.kdg.programming3.service.DoctorService;
import be.kdg.programming3.service.ExportService;
import be.kdg.programming3.service.PatientService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Controller to perform export patients and doctors to a Json file
 * saved files located in root directory (patients.json) (doctors.json)
 */
@Controller
@RequestMapping("/export")
public class ExportController {
    private static final Logger logger = LoggerFactory.getLogger(ExportController.class);

    private final PatientService patientService;
    private final DoctorService doctorService;
    private final ExportService exportService;

    public ExportController(PatientService patientService, DoctorService doctorService, ExportService exportService) {
        this.patientService = patientService;
        this.doctorService = doctorService;
        this.exportService = exportService;
    }

    @GetMapping("/patients")
    public ResponseEntity<String> exportPatients(HttpSession session) {
        List<Patient> patients = patientService.getAllPatients();
        String fileName = "patients.json";
        exportService.exportEntitiesToFile(Patient.class, patients, fileName);
        logger.debug("Patient list exported to {}", fileName);
        logVisit(session,"Performing export of patients...");
        return ResponseEntity.ok("Patients exported to " + fileName);
    }

    @GetMapping("/doctors")
    public ResponseEntity<String> exportDoctors(HttpSession session) {
        List<Doctor> doctors = doctorService.getAllDoctors();
        String fileName = "doctors.json";
        exportService.exportEntitiesToFile(Doctor.class, doctors, fileName);
        logger.debug("Doctor list exported to {}", fileName);
        logVisit(session,"Performing export of doctors...");
        return ResponseEntity.ok("Doctors exported to " + fileName);
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
