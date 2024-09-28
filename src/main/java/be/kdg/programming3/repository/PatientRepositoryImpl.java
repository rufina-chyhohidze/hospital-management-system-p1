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
    public void addPatient(Patient patient) {
        patients.add(patient);
    }

    @Override
    public void updatePatient(Patient patient) {
        removePatient(patient.getPatientId());
        patients.add(patient);
    }

    @Override
    public Optional<Patient> findById(String patientId) {
        return patients.stream()
                .filter(p -> p.getPatientId().equals(patientId))
                .findFirst();
    }

    @Override
    public List<Patient> findAll() {
        return new ArrayList<>(patients);
    }

    @Override
    public void removePatient(String patientId) {
        patients.removeIf(patient -> patient.getPatientId().equals(patientId));
    }

}
