package be.kdg.programming3.repository;

import be.kdg.programming3.domain.Department;
import be.kdg.programming3.domain.Doctor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Profile("old")
public class OldDoctorRepositoryImpl implements DoctorRepository {
    private static final Logger logger = LoggerFactory.getLogger(OldDoctorRepositoryImpl.class);


    private final List<Doctor> doctorList = new ArrayList<>();

    @Override
    public List<Doctor> getAllDoctors() {
        return new ArrayList<>(doctorList);
    }

    @Override
    public List<Doctor> findByDepartment(Department department) {
        logger.info("Getting doctors by department {} ...", department);
        return doctorList.stream().filter(doctor -> doctor.getDepartment().equals(department)).collect(Collectors.toList());
    }

    @Override
    public Doctor findByLicenseNumber(int licenseNumber) {
        logger.info("Getting doctors by license number {} ...", licenseNumber);
        return doctorList.stream().filter(doctor -> doctor.getLicenseNumber() == licenseNumber).findFirst().get();
    }

    @Override
    public void addDoctor(Doctor doctor) {
        logger.info("Adding doctor {} ...", doctor);
        doctorList.add(doctor);
    }

}
