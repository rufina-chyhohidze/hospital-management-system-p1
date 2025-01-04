package be.kdg.programming3.service.jdbc;

import be.kdg.programming3.domain.Doctor;
import be.kdg.programming3.domain.Patient;
import be.kdg.programming3.repository.PatientRepository;
import be.kdg.programming3.service.PatientService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * * entity ,we use it in services and repositories(we need it for use a concrete implementation with
 *  * #entity manager(jpa v1)
 */
@Service
        //("patientServiceImplPostgres")
@Profile("entity")
public class PatientServiceImplPostgres implements PatientService {
    private final PatientRepository patientRepository;

    public PatientServiceImplPostgres(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public List<Patient> getAllPatients() {
        return patientRepository.getAllPatients();
    }

    @Override
    public List<Patient> getPatientsByNameOrAdmissionDate(String name, LocalDate admissionDate) {
        // Implement as a query in PatientRepository if needed
        return patientRepository.getAllPatients().stream()
                .filter(patient -> patient.getFirstName().equalsIgnoreCase(name) ||
                        patient.getAdmissionDate().equals(admissionDate))
                .toList();
    }

    @Override
    public Patient findPatientById(String patientId) {
        return patientRepository.findPatientById(patientId);
    }

    @Override
    @Transactional
    public void addPatient(Patient patient) {
        patientRepository.savePatient(patient);
    }

    @Override
    @Transactional
    public void removePatient(String patientId) {
        patientRepository.removePatient(patientId);
    }

    @Override
    @Transactional
    public void assignDoctorToPatient(String patientId, int doctorId) {
        patientRepository.assignDoctorToPatient(patientId, doctorId);
    }

    @Override
    public List<Patient> getPatientsForDoctor(int doctorId) {
        return patientRepository.getPatientsForDoctor(doctorId);
    }

    @Override
    public List<Doctor> getDoctorsForPatient(String patientId) {
        return patientRepository.getDoctorsForPatient(patientId);
    }
}
