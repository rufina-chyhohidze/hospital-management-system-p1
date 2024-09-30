package be.kdg.programming3.service;

import be.kdg.programming3.domain.Patient;
import be.kdg.programming3.repository.DataFactory;
import be.kdg.programming3.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public List<Patient> getAllPatients() {
        return DataFactory.patients;
    }

    @Override
    public List<Patient> getPatientsByNameOrAdmissionDate(String name, LocalDate admissionDate) {
        return DataFactory.patients.stream()
                .filter(patient -> {
                    boolean matchesName = name == null || name.isEmpty() ||
                            patient.getFirstName().toLowerCase().contains(name) ||
                            patient.getLastName().toLowerCase().contains(name);
                    boolean matchesDate = admissionDate == null ||
                            patient.getAdmissionDate().equals(admissionDate);
                    return matchesName && matchesDate;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Patient findPatientById(String patientId) {
        return DataFactory.patients.stream()
                .filter(patient -> patient.getPatientId().equals(patientId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void addPatient(Patient patient) {
        DataFactory.patients.add(patient); // In-memory list
    }

    @Override
    public void removePatient(String patientId) {
        patientRepository.removePatient(patientId);
    }



}
