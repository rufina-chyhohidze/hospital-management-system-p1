package be.kdg.programming3.repository;

import be.kdg.programming3.domain.Department;
import be.kdg.programming3.domain.Doctor;
import be.kdg.programming3.domain.Gender;
import be.kdg.programming3.domain.Patient;
import be.kdg.programming3.presentation.PatientController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Profile("new")
public class DoctorRepositoryImpl implements DoctorRepository {
    private static final Logger logger = LoggerFactory.getLogger(DoctorRepositoryImpl.class);
    private final JdbcTemplate jdbcTemplate;


    @Autowired
    public DoctorRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Doctor> doctorRowMapper = (rs, rowNum) -> {
        Doctor doctor = new Doctor();
        doctor.setLicenseNumber(rs.getInt("license_number"));
        doctor.setFirstName(rs.getString("first_name"));
        doctor.setLastName(rs.getString("last_name"));
        doctor.setDepartment(Department.valueOf(rs.getString("department").toUpperCase()));
        doctor.setSalary(rs.getDouble("salary"));
        doctor.setHireDate(rs.getDate("hire_date").toLocalDate());
        doctor.setGender(rs.getString("gender").equalsIgnoreCase("MALE") ? Gender.MALE : Gender.FEMALE);
        return doctor;
    };

    @Override
    public List<Doctor> getAllDoctors() {
        logger.info("Fetching all doctors...");
        String sql = "SELECT * FROM doctors";
        return jdbcTemplate.query(sql, doctorRowMapper);
    }

    @Override
    public List<Doctor> findByDepartment(Department department) {
        return List.of();
    }

    @Override
    public Doctor findByLicenseNumber(int licenseNumber) {
        logger.info("Fetching doctor by license number: {}", licenseNumber);
        String sql = "SELECT * FROM doctors WHERE license_number = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{licenseNumber}, doctorRowMapper);
    }

    @Override
    public void addDoctor(Doctor doctor) {
        logger.info("Adding doctor: {}", doctor);
        String sql = "INSERT INTO doctors (license_number, first_name, last_name, department, salary, hire_date, gender) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, doctor.getLicenseNumber(), doctor.getFirstName(), doctor.getLastName(),
                doctor.getDepartment().toString(), doctor.getSalary(), doctor.getHireDate(), doctor.getGender().toString());
    }

    // New method to add a relationship between a doctor and a patient
    public void addPatientToDoctor(int licenseNumber, String patientId) {
        logger.info("Adding patient {} to doctor {}", patientId, licenseNumber);
        String sql = "INSERT INTO doctor_patient (DOCTOR_ID, patient_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, licenseNumber, patientId);
    }

    // New method to remove a relationship between a doctor and a patient
    public void removePatientFromDoctor(int licenseNumber, String patientId) {
        logger.info("Removing patient {} from doctor {}", patientId, licenseNumber);
        String sql = "DELETE FROM doctor_patient WHERE DOCTOR_ID = ? AND patient_id = ?";
        jdbcTemplate.update(sql, licenseNumber, patientId);
    }

    // New method to get all patients for a given doctor
    @Override
    public List<Doctor> getDoctorsForPatient(String patientId) {
        logger.info("Fetching doctors for patient with ID: {}", patientId);
        String sql = "SELECT d.* FROM doctors d " +
                "JOIN doctor_patient dp ON d.license_number = dp.DOCTOR_ID " +
                "WHERE dp.patient_id = ?";
        return jdbcTemplate.query(sql, new Object[]{patientId}, (rs, rowNum) -> {
            Doctor doctor = new Doctor();
            doctor.setLicenseNumber(rs.getInt("license_number"));
            doctor.setFirstName(rs.getString("first_name"));
            doctor.setLastName(rs.getString("last_name"));
            doctor.setDepartment(Department.valueOf(rs.getString("department").toUpperCase()));
            doctor.setSalary(rs.getDouble("salary"));
            doctor.setHireDate(rs.getDate("hire_date").toLocalDate());
            doctor.setGender(rs.getString("gender").equalsIgnoreCase("MALE") ? Gender.MALE : Gender.FEMALE);
            return doctor;
        });
    }
    @Override
    public void deleteDoctor(int licenseNumber) {
        String sql = "DELETE FROM doctors WHERE license_number = ?";
        jdbcTemplate.update(sql, licenseNumber);
    }
    @Override
    public void assignPatientToDoctor(int doctorId, String patientId) {
        String sql = "INSERT INTO doctor_patient (doctor_id, patient_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, doctorId, patientId);
    }

}
