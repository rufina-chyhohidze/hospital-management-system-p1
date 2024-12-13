package be.kdg.programming3.service;

import be.kdg.programming3.domain.Department;
import be.kdg.programming3.domain.Doctor;
import be.kdg.programming3.repository.DoctorRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
        //("doctorServiceImplPostgres")
@Profile("entity")
public class DoctorServiceImplPostgres implements DoctorService {
    private final DoctorRepository doctorRepository;

    public DoctorServiceImplPostgres(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public List<Doctor> getAllDoctors() {
        return doctorRepository.getAllDoctors();
    }

    @Override
    public List<Doctor> getDoctorsByDepartment(Department department) {
        return doctorRepository.findByDepartment(department);
    }

    @Override
    public Doctor findDoctorByLicenseNumber(int licenseNumber) {
        return doctorRepository.findByLicenseNumber(licenseNumber);
    }

    @Override
    @Transactional
    public void addDoctor(Doctor doctor) {
        doctorRepository.addDoctor(doctor);
    }

    @Override
    @Transactional
    public void removeDoctor(int licenseNumber) {
        doctorRepository.deleteDoctor(licenseNumber);
    }

    @Override
    public List<Doctor> getDoctorsForPatient(String patientId) {
        return doctorRepository.getDoctorsForPatient(patientId);
    }

    @Override
    @Transactional
    public void assignPatientToDoctor(int doctorId, String patientId) {
        doctorRepository.assignPatientToDoctor(doctorId, patientId);
    }
}
