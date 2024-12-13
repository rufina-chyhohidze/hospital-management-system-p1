package be.kdg.programming3.service;

import be.kdg.programming3.domain.Department;
import be.kdg.programming3.domain.Doctor;
import be.kdg.programming3.domain.Patient;
import be.kdg.programming3.repository.jpadata.DoctorJpaDataRepository;
import be.kdg.programming3.repository.jpadata.PatientJpaDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
        //("doctorJpaDataServiceImpl")
@Profile("jpa")
public class DoctorJpaDataServiceImpl implements DoctorService{
    private final DoctorJpaDataRepository doctorRepository;
    private final PatientJpaDataRepository patientRepository;


    //@Autowired
    public DoctorJpaDataServiceImpl(DoctorJpaDataRepository doctorRepository, PatientJpaDataRepository patientRepository) {
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    @Override
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    @Override
    public List<Doctor> getDoctorsByDepartment(Department department) {
        return doctorRepository.findByDepartment(department);
    }

    @Override
    public Doctor findDoctorByLicenseNumber(int licenseNumber) {
        return doctorRepository.findById(licenseNumber).orElse(null);
    }

    @Override
    public void addDoctor(Doctor doctor) {
        doctorRepository.save(doctor);
    }

    @Override
    public void removeDoctor(int licenseNumber) {
        doctorRepository.deleteById(licenseNumber);
    }

    @Override
    public List<Doctor> getDoctorsForPatient(String patientId) {
        return doctorRepository.findDoctorsForPatient(patientId);
    }

    @Override
    @Transactional
    public void assignPatientToDoctor(int doctorId, String patientId) {
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new RuntimeException("Doctor not found"));
        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new RuntimeException("Patient not found"));
        doctor.getPatients().add(patient);
        doctorRepository.save(doctor);
    }

}
