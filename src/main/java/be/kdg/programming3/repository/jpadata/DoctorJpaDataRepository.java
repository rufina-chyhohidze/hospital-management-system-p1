package be.kdg.programming3.repository.jpadata;

import be.kdg.programming3.domain.Department;
import be.kdg.programming3.domain.Doctor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Profile("jpa")
@Repository
public interface DoctorJpaDataRepository extends JpaRepository<Doctor, Integer> {
    List<Doctor> findByDepartment(Department department);

    @Query("SELECT d FROM Doctor d JOIN d.patients p WHERE p.patientId = :patientId")
    List<Doctor> findDoctorsForPatient(@Param("patientId") String patientId);
}
