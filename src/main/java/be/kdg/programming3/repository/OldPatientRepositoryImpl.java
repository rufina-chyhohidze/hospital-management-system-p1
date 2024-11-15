package be.kdg.programming3.repository;

import be.kdg.programming3.domain.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Profile("old")
@Repository
public class OldPatientRepositoryImpl implements PatientRepository {
    private final List<Patient> patients = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(OldPatientRepositoryImpl.class);


    @Override
    public Patient findPatientById(String patientId) {
        return patients.stream()
                .filter(patient -> patient.getPatientId().equals(patientId))
                .findFirst()
                .orElse(null);
    }


    @Override
    public void savePatient(Patient patient) {
        logger.info("Saving patient {}", patient);
        patients.add(patient);
    }


    @Override
    public List<Patient> getAllPatients() {
        return new ArrayList<>(patients);
    }

    @Override
    public void removePatient(String patientId) {
        logger.info("Removing patient {}", patientId);
        patients.removeIf(patient -> patient.getPatientId().equals(patientId));
    }

}
