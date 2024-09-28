package be.kdg.programming3.repository;

import be.kdg.programming3.domain.Department;
import be.kdg.programming3.domain.Doctor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class DoctorRepositoryImpl implements DoctorRepository {
    private final List<Doctor> doctorList;

    public DoctorRepositoryImpl() {
        this.doctorList = DataFactory.doctors; // Example of loading initial data
    }
    @Override
    public List<Doctor> findAll() {
        return DataFactory.doctors; // In-memory data source for now
    }

    @Override
    public List<Doctor> findByDepartment(Department department) {
        return DataFactory.doctors.stream()
                .filter(doctor -> doctor.getDepartment() == department)
                .collect(Collectors.toList());
    }

    @Override
    public Doctor findByLicenseNumber(int licenseNumber) {
        return DataFactory.doctors.stream()
                .filter(doctor -> doctor.getLicenseNumber() == licenseNumber)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void addDoctor(Doctor doctor) {
        doctorList.add(doctor);
    }

}
