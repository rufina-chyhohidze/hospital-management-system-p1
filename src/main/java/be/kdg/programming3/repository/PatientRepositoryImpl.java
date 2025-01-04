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

/**
 * Uses JDBC templates and H2 database.
 */

@Repository
@Profile("jdbc")
public class PatientRepositoryImpl implements PatientRepository {
    private static final Logger logger = LoggerFactory.getLogger(PatientRepositoryImpl.class);
    private final JdbcTemplate jdbcTemplate;

    public PatientRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Patient> patientRowMapper = (rs, rowNum) -> {
        Patient patient = new Patient();
        patient.setPatientId(rs.getString("patient_id"));
        patient.setFirstName(rs.getString("first_name"));
        patient.setLastName(rs.getString("last_name"));
        patient.setAge(rs.getInt("age"));
        patient.setGender(Gender.valueOf(rs.getString("gender").toUpperCase()));
        patient.setAdmissionDate(rs.getDate("admission_date").toLocalDate());
        patient.setBillingAmount(rs.getDouble("billing_amount"));
        return patient;
    };

    @Override
    public Patient findPatientById(String patientId) {
        logger.info("Finding patient by ID: {}", patientId);
        String sql = "SELECT * FROM patients WHERE patient_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{patientId}, patientRowMapper);
    }

    @Override
    public void savePatient(Patient patient) {
        String sql = "INSERT INTO patients (patient_id, first_name, last_name, age, gender, admission_date, billing_amount) VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, patient.getPatientId(), patient.getFirstName(), patient.getLastName(), patient.getAge(), patient.getGender().toString(), patient.getAdmissionDate(), patient.getBillingAmount());

        // Insert relationships in doctor_patient table
        for (Doctor doctor : patient.getDoctors()) {
            String relationshipSql = "INSERT INTO doctor_patient (doctor_id, patient_id) VALUES (?, ?)";
            jdbcTemplate.update(relationshipSql, doctor.getLicenseNumber(), patient.getPatientId());
        }
    }

    @Override
    public List<Patient> getAllPatients() {
        logger.info("Fetching all patients");
        String sql = "SELECT * FROM patients";
        return jdbcTemplate.query(sql, patientRowMapper);
    }

    @Override
    public void removePatient(String patientId) {
        logger.info("Removing patient with ID: {}", patientId);
        String sql = "DELETE FROM patients WHERE patient_id = ?";
        jdbcTemplate.update(sql, patientId);
    }

    // New method to get all doctors for a given patient
    public List<Doctor> getDoctorsForPatient(String patientId) {
        String sql = "SELECT d.* FROM doctors d " +
                "JOIN doctor_patient dp ON d.license_number = dp.DOCTOR_ID " +
                "WHERE dp.patient_id = ?";
        List<Doctor> listDoc = jdbcTemplate.query(sql, new Object[]{patientId}, (rs, rowNum) -> {
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
        logger.debug(listDoc.toString());
        return listDoc;
    }
    @Override
    public void assignDoctorToPatient(String patientId, int doctorId) {
        String sql = "INSERT INTO doctor_patient (doctor_id, patient_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, doctorId, patientId);
    }


    @Override
    public List<Patient> getPatientsForDoctor(int doctorId) {
        String sql = "SELECT p.* FROM patients p " +
                "JOIN doctor_patient dp ON p.patient_id = dp.patient_id " +
                "WHERE dp.doctor_id = ?";
        return jdbcTemplate.query(sql, new Object[]{doctorId}, (rs, rowNum) -> {
            Patient patient = new Patient();
            patient.setPatientId(rs.getString("patient_id"));
            patient.setFirstName(rs.getString("first_name"));
            patient.setLastName(rs.getString("last_name"));
            patient.setAge(rs.getInt("age"));
            patient.setGender(Gender.valueOf(rs.getString("gender").toUpperCase()));
            patient.setAdmissionDate(rs.getDate("admission_date").toLocalDate());
            patient.setBillingAmount(rs.getDouble("billing_amount"));
            return patient;
        });
    }

}
