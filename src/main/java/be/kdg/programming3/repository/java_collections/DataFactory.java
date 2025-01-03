package be.kdg.programming3.repository.java_collections;

import be.kdg.programming3.domain.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * DataFactory class responsible for seeding initial data into the system.
 */
public class DataFactory {
    // Public static fields for many-to-many entities
    public static List<Doctor> doctors = new ArrayList<>();
    public static List<Patient> patients = new ArrayList<>();

    /**
     * Seeds the doctors and patients lists with initial data.
     */
    public static void seed() {
        // Create Hospitals
        Hospital hospital1 = new Hospital(
                "Saint Mary's Hospital",
                "123 Main St, Anytown",
                List.of(Department.CARDIOLOGY, Department.NEUROLOGY),
                LocalDate.of(1980, 1, 1)
        );

        Hospital hospital2 = new Hospital(
                "General Health Center",
                "456 Elm St, Othertown",
                List.of(Department.PEDIATRICS, Department.SURGERY),
                LocalDate.of(1990, 6, 15)
        );

        Hospital hospital3 = new Hospital(
                "City Hospital",
                "789 Oak St, Sometown",
                List.of(Department.RADIOLOGY, Department.DENTISTRY),
                LocalDate.of(2000, 3, 10)
        );

        Hospital hospital4 = new Hospital(
                "Metro Medical Center",
                "321 Pine St, Anycity",
                List.of(Department.ORTHOPEDICS, Department.NEUROLOGY),
                LocalDate.of(2010, 9, 5)
        );

        // Create Doctors
        Doctor doctor1 = new Doctor(
                "Emily",
                "Clark",
                Department.CARDIOLOGY,
                1001,
                150000.00,
                LocalDate.of(2015, 5, 20),
                Gender.FEMALE
        );

        Doctor doctor2 = new Doctor(
                "Michael",
                "Smith",
                Department.NEUROLOGY,
                1002,
                160000.00,
                LocalDate.of(2012, 8, 15),
                Gender.MALE
        );

        Doctor doctor3 = new Doctor(
                "Sarah",
                "Johnson",
                Department.PEDIATRICS,
                1003,
                140000.00,
                LocalDate.of(2018, 2, 10),
                Gender.FEMALE
        );

        Doctor doctor4 = new Doctor(
                "David",
                "Lee",
                Department.SURGERY,
                1004,
                170000.00,
                LocalDate.of(2010, 11, 25),
                Gender.MALE
        );

        // Assign Doctors to Hospitals
        hospital1.addDoctor(doctor1); // Dr. Emily in Saint Mary's Hospital
        hospital1.addDoctor(doctor2); // Dr. Michael in Saint Mary's Hospital
        hospital2.addDoctor(doctor3); // Dr. Sarah in General Health Center
        hospital2.addDoctor(doctor4); // Dr. David in General Health Center

        // Add Doctors to DataFactory's list
        doctors.add(doctor1);
        doctors.add(doctor2);
        doctors.add(doctor3);
        doctors.add(doctor4);

     // Create Patients
     Patient patient1 = new Patient(
             "John",
             "Doe",
             30,
             Gender.MALE,
             "P1001",
             2500.50,
             LocalDate.of(2023, 9, 1),hospital1
     );

     Patient patient2 = new Patient(
             "Jane",
             "Smith",
             45,
             Gender.FEMALE,
             "P1002",
             5000.75,
             LocalDate.of(2023, 9, 5),hospital2
     );

     Patient patient3 = new Patient(
             "Alice",
             "Brown",
             25,
             Gender.FEMALE,
             "P1003",
             1500.00,
             LocalDate.of(2023, 9, 10),hospital3
     );

     Patient patient4 = new Patient(
             "Bob",
             "Johnson",
             60,
             Gender.MALE,
             "P1004",
             3000.25,
             LocalDate.of(2023, 9, 15),hospital4
     );

        // Assign Patients to Doctors to establish many-to-many relationships
        doctor1.addPatient(patient1); // Dr. Emily treats John
        doctor1.addPatient(patient2); // Dr. Emily treats Jane

        doctor2.addPatient(patient2); // Dr. Michael treats Jane
        doctor2.addPatient(patient3); // Dr. Michael treats Alice

        doctor3.addPatient(patient1); // Dr. Sarah treats John
        doctor3.addPatient(patient4); // Dr. Sarah treats Bob

        doctor4.addPatient(patient4); // Dr. David treats Bob

        // Add Patients to DataFactory's list
        patients.add(patient1);
        patients.add(patient2);
        patients.add(patient3);
        patients.add(patient4);
    }
}