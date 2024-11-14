package be.kdg.programming3.presentation;

import be.kdg.programming3.domain.Department;
import be.kdg.programming3.domain.Doctor;
import be.kdg.programming3.domain.Gender;
import be.kdg.programming3.domain.Patient;
import be.kdg.programming3.presentation.viewmodels.DoctorForm;
import be.kdg.programming3.service.DoctorService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;


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
    @GetMapping("/{licenseNumber}")
    public String getDoctorDetails(@PathVariable int licenseNumber, Model model, HttpSession session) {
        Doctor doctor = doctorService.findDoctorByLicenseNumber(licenseNumber);
        logVisit(session,"Visited Doctor Details for doctor: "+licenseNumber);
        if (doctor != null) {
            model.addAttribute("doctor",doctor);
            return "doctorDetails";
        }
        return "redirect:/doctors";
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
