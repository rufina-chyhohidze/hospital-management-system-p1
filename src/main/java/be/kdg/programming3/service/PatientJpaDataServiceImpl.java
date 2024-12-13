package be.kdg.programming3.service;

import be.kdg.programming3.domain.Doctor;
import be.kdg.programming3.domain.Patient;
import be.kdg.programming3.repository.jpadata.DoctorJpaDataRepository;
import be.kdg.programming3.repository.jpadata.PatientJpaDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
        //("patientJpaDataServiceImpl")
@Profile("jpa")
public class PatientJpaDataServiceImpl implements PatientService {
    private final PatientJpaDataRepository patientRepository;
    private final DoctorJpaDataRepository doctorRepository;

    //@Autowired
    public PatientJpaDataServiceImpl(PatientJpaDataRepository patientRepository, DoctorJpaDataRepository doctorRepository) {
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }


    @Override
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    @Override
    public List<Patient> getPatientsByNameOrAdmissionDate(String name, LocalDate admissionDate) {
        return patientRepository.findByNameOrAdmissionDate(name, admissionDate);
    }

    @Override
    public Patient findPatientById(String patientId) {
        return patientRepository.findById(patientId).orElse(null);
    }

    @Override
    public void addPatient(Patient patient) {
        patientRepository.save(patient);
    }

    @Override
    @Transactional
    public void removePatient(String patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        // Break associations
        for (Doctor doctor : patient.getDoctors()) {
            doctor.getPatients().remove(patient);
            doctorRepository.save(doctor); // Save the doctor after modification
        }

        patientRepository.delete(patient);
    }

    @Override
    @Transactional
    public void assignDoctorToPatient(String patientId, int doctorId) {
        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new RuntimeException("Patient not found"));
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new RuntimeException("Doctor not found"));
        patient.getDoctors().add(doctor);
        patientRepository.save(patient);
    }

    @Override
    public List<Patient> getPatientsForDoctor(int doctorId) {
        return patientRepository.findPatientsForDoctor(doctorId);
    }

    @Override
    public List<Doctor> getDoctorsForPatient(String patientId) {
        return doctorRepository.findDoctorsForPatient(patientId);
    }

}
