package be.kdg.programming3.service;

import be.kdg.programming3.domain.Department;
import be.kdg.programming3.domain.Doctor;

import java.util.List;

public interface DoctorService {
    List<Doctor> getAllDoctors();

    List<Doctor> getDoctorsByDepartment(Department department);

    Doctor findDoctorByLicenseNumber(int licenseNumber);

    void addDoctor(Doctor doctor);
}
