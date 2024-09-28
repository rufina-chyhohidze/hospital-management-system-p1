package be.kdg.programming3.repository;

import be.kdg.programming3.domain.Patient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface PatientRepository {
    void addPatient(Patient patient);
    void updatePatient(Patient patient);
    Optional<Patient> findById(String patientId);
    List<Patient> findAll();
    void removePatient(String patientId);
}
