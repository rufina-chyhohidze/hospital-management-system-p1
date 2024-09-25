package be.kdg.programming3.repository;

import be.kdg.programming3.domain.Department;
import be.kdg.programming3.domain.Doctor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DoctorRepository {
    private final List<Doctor> doctors = new ArrayList<>();

    public List<Doctor> findAll() {
        return doctors;
    }

    public List<Doctor> findByDepartment(Department department) {
        return doctors.stream()
                .filter(doctor -> doctor.getDepartment().equals(department))
                .collect(Collectors.toList());
    }
}
