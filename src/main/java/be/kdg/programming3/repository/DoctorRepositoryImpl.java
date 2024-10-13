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

    private final List<Doctor> doctorList = new ArrayList<>();

    @Override
    public List<Doctor> getAllDoctors() {
        return new ArrayList<>(doctorList);
    }

    @Override
    public List<Doctor> findByDepartment(Department department) {
        return doctorList.stream().filter(doctor -> doctor.getDepartment().equals(department)).collect(Collectors.toList());
    }

    @Override
    public Doctor findByLicenseNumber(int licenseNumber) {
        return doctorList.stream().filter(doctor -> doctor.getLicenseNumber() == licenseNumber).findFirst().get();
    }

    @Override
    public void addDoctor(Doctor doctor) {
        doctorList.add(doctor);
    }

}
