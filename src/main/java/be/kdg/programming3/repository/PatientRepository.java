package be.kdg.programming3.repository;

import be.kdg.programming3.domain.Doctor;
import be.kdg.programming3.domain.Patient;

import java.util.List;

public interface PatientRepository {
    void savePatient(Patient patient);
    Patient findPatientById(String patientId);
    List<Patient> getAllPatients();
    void removePatient(String patientId);
    void assignDoctorToPatient(String patientId, int doctorId);
    List<Patient> getPatientsForDoctor(int doctorId);
     List<Doctor> getDoctorsForPatient(String patientId);
}
