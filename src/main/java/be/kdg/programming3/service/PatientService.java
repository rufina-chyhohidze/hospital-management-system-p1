package be.kdg.programming3.service;

import be.kdg.programming3.domain.Doctor;
import be.kdg.programming3.domain.Patient;

import java.time.LocalDate;
import java.util.List;

public interface PatientService {
    List<Patient> getAllPatients();

    List<Patient> getPatientsByNameOrAdmissionDate(String name, LocalDate admissionDate);

    Patient findPatientById(String patientId);

    void addPatient(Patient patient);

    void removePatient(String patientId);

    void assignDoctorToPatient(String patientId, int doctorId);
    List<Patient> getPatientsForDoctor(int doctorId);
    List<Doctor> getDoctorsForPatient(String patientId);
}
