package be.kdg.programming3.repository;

import be.kdg.programming3.domain.Department;
import be.kdg.programming3.domain.Doctor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface DoctorRepository {
    List<Doctor> findAll();
    List<Doctor> findByDepartment(Department department);
    Doctor findByLicenseNumber(int licenseNumber);
    void addDoctor(Doctor doctor);
}
