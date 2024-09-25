package be.kdg.programming3.repository;

import be.kdg.programming3.domain.Patient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PatientRepository {
    private final List<Patient> patients = new ArrayList<>();

    public List<Patient> findAll() {
        return patients;
    }

    public Optional<Patient> findById(String patientId) {
        return patients.stream()
                .filter(patient -> patient.getPatientId().equals(patientId))
                .findFirst();
    }
}
