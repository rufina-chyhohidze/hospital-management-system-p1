package be.kdg.programming3.repository;

import be.kdg.programming3.domain.Hospital;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class HospitalRepositoryImpl implements HospitalRepository {
    private final List<Hospital> hospitals = new ArrayList<>();

    @Override
    public void addHospital(Hospital hospital) {
        hospitals.add(hospital);
    }

    @Override
    public Optional<Hospital> findByName(String hospitalName) {
        return hospitals.stream()
                .filter(h -> h.getHospitalName().equalsIgnoreCase(hospitalName))
                .findFirst();
    }

    @Override
    public List<Hospital> findAll() {
        return new ArrayList<>(hospitals);
    }

    @Override
    public void removeHospital(String hospitalName) {
        hospitals.removeIf(hospital -> hospital.getHospitalName().equalsIgnoreCase(hospitalName));
    }
}

