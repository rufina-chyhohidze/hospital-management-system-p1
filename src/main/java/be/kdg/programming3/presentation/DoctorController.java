package be.kdg.programming3.presentation;

import be.kdg.programming3.domain.Department;
import be.kdg.programming3.domain.Doctor;
import be.kdg.programming3.domain.Gender;
import be.kdg.programming3.domain.Patient;
import be.kdg.programming3.presentation.viewmodels.DoctorForm;
import be.kdg.programming3.service.DoctorService;
import be.kdg.programming3.service.PatientService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;


@Controller
@RequestMapping("/doctors")
public class DoctorController {
    private static final Logger logger = LoggerFactory.getLogger(DoctorController.class);
    private final DoctorService doctorService;
    private final PatientService patientService;

    /**
     *
     * @param doctorService - qualifier can be removed, to use doctorServiceImpl(h2)
     * @param patientService - now qualifier is used for postgres implementation
     */
    @Autowired
    public DoctorController(@Qualifier("doctorServiceImplPostgres") DoctorService doctorService, @Qualifier("patientServiceImplPostgres") PatientService patientService) {
        this.doctorService = doctorService;
        this.patientService = patientService;
    }
    @GetMapping
    public String getAllDoctors(Model model, HttpSession session) {
        logger.info("Fetching all doctors...");
        logVisit(session,"Visited All Doctors page");
        model.addAttribute("doctors", doctorService.getAllDoctors());
        return "doctors";
    }

    @GetMapping("/add")
    public String showAddDoctorForm(Model model, HttpSession session) {
        logger.info("Processing doctor's form...");
        logVisit(session,"Visited Doctor's Form");
        model.addAttribute("doctorForm", new DoctorForm());
        return "adddoctor"; //
    }

    @GetMapping("/{doctorId}")
    public String getDoctorDetails(@PathVariable int doctorId, Model model) {
        Doctor doctor = doctorService.findDoctorByLicenseNumber(doctorId);
        List<Patient> patients = patientService.getPatientsForDoctor(doctorId); // fetch related patients
        List<Patient> allPatients = patientService.getAllPatients();

        model.addAttribute("doctor", doctor);
        model.addAttribute("assignedPatients", patients);
        model.addAttribute("allPatients", allPatients);
        return "doctorDetails";
    }


    @PostMapping("/add")
    public String addDoctor(@ModelAttribute("doctorForm")@Valid DoctorForm doctorForm, BindingResult bindingResult, Model model,HttpSession session) {
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
        logger.info("Successfully added a new doctor: {}", doctorForm.toString());
        logVisit(session,"Doctor addition session...");
        return "redirect:/doctors";
    }

    @PostMapping("/delete/{licenseNumber}")
    public String deleteDoctor(@PathVariable int licenseNumber, RedirectAttributes redirectAttributes) {
        try {
            doctorService.removeDoctor(licenseNumber);
            redirectAttributes.addFlashAttribute("successMessage", "Doctor deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting doctor: " + e.getMessage());
        }
        return "redirect:/doctors";
    }
    @PostMapping("/{doctorId}/assign-patient")
    public String assignPatientToDoctor(@PathVariable int doctorId, @RequestParam String patientId) {
        doctorService.assignPatientToDoctor(doctorId, patientId);
        return "redirect:/doctors/" + doctorId;
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
