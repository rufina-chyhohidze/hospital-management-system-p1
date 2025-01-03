package be.kdg.programming3.repository.postgres;

import be.kdg.programming3.domain.Doctor;
import be.kdg.programming3.domain.Patient;
import be.kdg.programming3.repository.PatientRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Profile("entity")
public class PatientRepositoryImplPostgres implements PatientRepository {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void savePatient(Patient patient) {
        if (em.contains(patient)) {
            em.merge(patient);  // Merge if already in persistence context
        } else {
            em.persist(patient);  // Persist if it's a new entity
        }
    }

    @Override
    public Patient findPatientById(String patientId) {
        try {
            return em.createQuery(
                            "SELECT p FROM Patient p WHERE p.patientId = :patientId", Patient.class)
                    .setParameter("patientId", patientId)
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new EntityNotFoundException("Patient not found with ID: " + patientId);
        }
    }

    @Override
    public List<Patient> getAllPatients() {
        return em.createQuery("SELECT p FROM Patient p", Patient.class).getResultList();
    }

    @Override
    @Transactional
    public void removePatient(String patientId) {
        Patient patient = findPatientById(patientId);
        if (patient != null) {
            logger.info("Removing patient with ID: " + patientId);

            // Unlink patient from all associated doctors
            for (Doctor doctor : patient.getDoctors()) {
                doctor.getPatients().remove(patient); // Remove patient from doctor's list
            }
            patient.getDoctors().clear(); // Clear the patient's doctor list

            // Now safely remove the patient
            em.remove(patient);
            logger.info("Patient with ID: " + patientId + " successfully removed");
        } else {
            logger.warn("Patient with ID: " + patientId + " not found");
        }
    }

    @Override
    @Transactional
    public void assignDoctorToPatient(String patientId, int doctorId) {
        Patient patient = em.find(Patient.class, patientId);
        Doctor doctor = em.find(Doctor.class, doctorId);

        if (doctor != null && patient != null) {
            doctor.getPatients().add(patient);
            patient.getDoctors().add(doctor);
            logger.debug("Starting merge...");
            em.merge(doctor);
            em.merge(patient);
            //em.flush();
            logger.debug("Finished merge...");
        } else {
            throw new EntityNotFoundException("Doctor or Patient not found. Patient ID: " + patientId + ", Doctor ID: " + doctorId);
        }
    }


    @Override
    public List<Patient> getPatientsForDoctor(int doctorId) {
        return em.createQuery(
                        "SELECT p FROM Patient p JOIN p.doctors d WHERE d.licenseNumber = :doctorId", Patient.class)
                .setParameter("doctorId", doctorId)
                .getResultList();
    }

    @Override
    public List<Doctor> getDoctorsForPatient(String patientId) {
        return em.createQuery(
                        "SELECT d FROM Doctor d JOIN d.patients p WHERE p.id = :patientId", Doctor.class)
                .setParameter("patientId", patientId)
                .getResultList();
    }
}
