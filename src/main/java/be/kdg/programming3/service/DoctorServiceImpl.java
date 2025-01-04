package be.kdg.programming3.service;

import be.kdg.programming3.domain.Department;
import be.kdg.programming3.domain.Doctor;
 import be.kdg.programming3.repository.DoctorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Being used for jdbc and very first implementations using list
 */
@Service
@Profile({"old","jdbc"})
public class DoctorServiceImpl implements DoctorService {
    private Logger logger = LoggerFactory.getLogger(DoctorServiceImpl.class);
    private final DoctorRepository doctorRepository;

    public DoctorServiceImpl(DoctorRepository doctorRepository) {
        logger.info("Creating doctor's repository...");
        this.doctorRepository = doctorRepository;
    }

    @Override
    public void removeDoctor(int licenseNumber) {
        doctorRepository.deleteDoctor(licenseNumber);
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
    public void addDoctor(Doctor doctor) {
        doctorRepository.addDoctor(doctor);
    }
    @Override
    public List<Doctor> getDoctorsForPatient(String patientId) {
        return doctorRepository.getDoctorsForPatient(patientId);
    }
    @Override
    public void assignPatientToDoctor(int doctorId, String patientId) {
        doctorRepository.assignPatientToDoctor(doctorId, patientId);
    }
}
