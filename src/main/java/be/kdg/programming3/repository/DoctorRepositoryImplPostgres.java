package be.kdg.programming3.repository;

import be.kdg.programming3.domain.Department;
import be.kdg.programming3.domain.Doctor;
import be.kdg.programming3.domain.Patient;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Profile("post")
public class DoctorRepositoryImplPostgres implements DoctorRepository {
    private  final Logger logger = LoggerFactory.getLogger(this.getClass());
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Doctor> getAllDoctors() {
        return em.createQuery("SELECT d FROM Doctor d", Doctor.class).getResultList();
    }

    @Override
    public List<Doctor> findByDepartment(Department department) {
        return em.createQuery("SELECT d FROM Doctor d WHERE d.department = :department", Doctor.class)
                .setParameter("department", department)
                .getResultList();
    }

    @Override
    public Doctor findByLicenseNumber(int licenseNumber) {
        return em.find(Doctor.class, licenseNumber);
    }

    @Override
    @Transactional
    public void addDoctor(Doctor doctor) {
        if (!em.contains(doctor)) {
            em.merge(doctor); // Merge handles both new and detached entities
        } else {
            em.persist(doctor); // Persist only if it's not detached
        }
    }

    @Override
    @Transactional
    public void deleteDoctor(int licenseNumber) {
        Doctor doctor = findByLicenseNumber(licenseNumber);
        if (doctor != null) {
            em.remove(doctor);
        }
    }

    @Override
    public List<Doctor> getDoctorsForPatient(String patientId) {
        return em.createQuery(
                        "SELECT d FROM Doctor d JOIN d.patients p WHERE p.patientId = :patientId", Doctor.class)
                .setParameter("patientId", patientId)  // Use the patientId directly as it's a String
                .getResultList();
    }

    @Override
    @Transactional
    public void assignPatientToDoctor(int doctorId, String patientId) {
        Doctor doctor = em.find(Doctor.class, doctorId);
        Patient patient = em.find(Patient.class, patientId);

        if (doctor == null || patient == null) {
            logger.error("Doctor or Patient not found! Doctor ID: {}, Patient ID: {}", doctorId, patientId);
            throw new EntityNotFoundException("Entities not found!");
        }

//        System.err.println("\n\n\nADDING THIS PATIENT: " + patient+"\n\n\n");
        doctor.getPatients().add(patient);
//        System.err.println("\n\n\npatients of doctor:"+doctor.getPatients()+" ADDED\n\n\n");
        patient.getDoctors().add(doctor);

        logger.debug("Assigning patient {} to doctor {}", patientId, doctorId);

        // Use merge to update entities in the persistence context
        em.merge(doctor);
//        em.merge(patient);
//        em.flush();

        logger.debug("Assignment successful!");
    }


}
