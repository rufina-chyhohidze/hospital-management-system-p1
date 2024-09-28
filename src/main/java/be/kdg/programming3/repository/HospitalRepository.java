package be.kdg.programming3.repository;

import be.kdg.programming3.domain.Hospital;

import java.util.List;
import java.util.Optional;

public interface HospitalRepository {
    void addHospital(Hospital hospital);

    Optional<Hospital> findByName(String hospitalName);

    List<Hospital> findAll();

    void removeHospital(String hospitalName);
}
