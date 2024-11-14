package be.kdg.programming3.repository;

import be.kdg.programming3.domain.Gender;
import be.kdg.programming3.domain.Patient;
import be.kdg.programming3.presentation.PatientController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PatientRepositoryImpl implements PatientRepository {
    private static final Logger logger = LoggerFactory.getLogger(PatientRepositoryImpl.class);
    private final JdbcTemplate jdbcTemplate;

    @Autowired
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
        logger.info("Saving patient {}", patient);
        String sql = "INSERT INTO patients (patient_id, first_name, last_name, age, gender, admission_date, billing_amount) VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, patient.getPatientId(), patient.getFirstName(), patient.getLastName(), patient.getAge(), patient.getGender().toString(), patient.getAdmissionDate(), patient.getBillingAmount());
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
}
