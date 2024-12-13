package be.kdg.programming3.service;

import be.kdg.programming3.domain.Doctor;
import be.kdg.programming3.domain.Patient;
import be.kdg.programming3.repository.DataFactory;
import be.kdg.programming3.repository.DoctorRepository;
import be.kdg.programming3.repository.PatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Profile({"old","jdbc"})
public class PatientServiceImpl implements PatientService {
    private Logger logger = LoggerFactory.getLogger(PatientServiceImpl.class);

    private PatientRepository patientRepository;

    public PatientServiceImpl(PatientRepository patientRepository) {
        logger.info("Creating patient repository...");
        this.patientRepository = patientRepository;
    }

    @Override
    public List<Patient> getAllPatients() {
        return patientRepository.getAllPatients();
    }

    @Override
    public List<Patient> getPatientsByNameOrAdmissionDate(String name, LocalDate admissionDate) {
        return patientRepository.getAllPatients().stream()
                .filter(patient -> {
                    boolean matchesName = name == null || name.isEmpty() ||
                            patient.getFirstName().toLowerCase().contains(name.toLowerCase()) ||
                            patient.getLastName().toLowerCase().contains(name.toLowerCase());
                    boolean matchesDate = admissionDate == null ||
                            patient.getAdmissionDate().equals(admissionDate);
                    return matchesName && matchesDate;
                })
                .collect(Collectors.toList());
    }


    @Override
    public Patient findPatientById(String patientId) {
        logger.info("Getting patient by id {}...", patientId);
        return patientRepository.findPatientById(patientId);
    }

    @Override
    public void addPatient(Patient patient) {
        patientRepository.savePatient(patient);
    }

    @Override
    public void removePatient(String patientId) {
        logger.info("Removing patient {}...", patientId);
        patientRepository.removePatient(patientId);
    }
    @Override
    public void assignDoctorToPatient(String patientId, int doctorId) {
        patientRepository.assignDoctorToPatient(patientId, doctorId);
    }
    @Override
    public List<Patient> getPatientsForDoctor(int doctorId) {
        return patientRepository.getPatientsForDoctor(doctorId);
    }
    public List<Doctor> getDoctorsForPatient(String patientId) {

        List<Doctor> listDoc =  patientRepository.getDoctorsForPatient(patientId);
        logger.debug(listDoc.toString());
        return listDoc;
    }
}
