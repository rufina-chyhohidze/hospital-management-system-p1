package be.kdg.programming3.repository;

import be.kdg.programming3.domain.Patient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface PatientRepository {
    //void addPatient(Patient patient);
    void savePatient(Patient patient);
    Patient findPatientById(String patientId);
    List<Patient> getAllPatients();
    void removePatient(String patientId);
}
