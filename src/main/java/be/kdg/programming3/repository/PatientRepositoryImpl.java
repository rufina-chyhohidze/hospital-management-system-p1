package be.kdg.programming3.repository;

import be.kdg.programming3.domain.Patient;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PatientRepositoryImpl implements PatientRepository {
    private final List<Patient> patients = new ArrayList<>();


    @Override
    public Patient findPatientById(String patientId) {
        return patients.stream()
                .filter(patient -> patient.getPatientId().equals(patientId))
                .findFirst()
                .orElse(null);
    }


    @Override
    public void savePatient(Patient patient) {
        patients.add(patient);

    }


    @Override
    public List<Patient> getAllPatients() {
        return new ArrayList<>(patients);
    }

    @Override
    public void removePatient(String patientId) {
        patients.removeIf(patient -> patient.getPatientId().equals(patientId));
    }

}
