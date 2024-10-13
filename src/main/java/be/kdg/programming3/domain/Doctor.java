package be.kdg.programming3.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Doctor (Many-to-Many with Patient)
 * A Doctor can have many Patients. Each Patient can have many Doctors assigned.
 * Hospital (One-to-Many with Doctor)
 * A Hospital can employ many Doctors. Each Doctor works in only one Hospital.
 */
public class Doctor {
    private String firstName;
    private String lastName;
    private Department department; // Using enum
    private int licenseNumber;
    private double salary;
    private LocalDate hireDate;
    private Gender gender;
    private Hospital hospital; // Reference to Hospital
    private Set<Patient> patients; //relationship link to patients


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setLicenseNumber(int licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setPatients(Set<Patient> patients) {
        this.patients = patients;
    }

    public Doctor(String firstName, String lastName, Department department, int licenseNumber, double salary, LocalDate hireDate, Gender gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.licenseNumber = licenseNumber;
        this.salary = salary;
        this.hireDate = hireDate;
        this.gender = gender;
        this.patients = new HashSet<>();
    }

    /**
     * Default Constructor
     */
    public Doctor() {
        this.patients = new HashSet<>();
    }

    //constructor for addition a doctor
    public Doctor(String firstName, String lastName, int licenseNumber, Department department) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.licenseNumber = licenseNumber;
        this.department = department;
        this.patients = new HashSet<>(); //for patient initialisation
    }

    // Getters and Setters
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Department getDepartment() {
        return department;
    }

    public int getLicenseNumber() {
        return licenseNumber;
    }

    public double getSalary() {
        return salary;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public Gender getGender() {
        return gender;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public Set<Patient> getPatients() {
        return new HashSet<>(patients); // Return a copy to maintain encapsulation
    }

    // Many-to-Many Relationship Methods

    /**
     * static method to add a patient to the doctor's list
     * for usage in DataFACTORY
     * @param patient The Patient to be added
     */
    public void addPatient(Patient patient) {
        if (patient == null) {
            throw new IllegalArgumentException("Patient cannot be null.");
        }
        if (!patients.contains(patient)) {
            patients.add(patient);
            patient.addDoctor(this); // bidirectional addition
        }
    }

    /**
     * static method to remove a patient from the doctor's list
     * to use in DataFactory
     * @param patient The Patient to be removed
     */
    public void removePatient(Patient patient) {
        if (patients.contains(patient)) {
            patients.remove(patient);
            patient.removeDoctor(this); // bidirectional removal
        }
    }

    // toString() Method
    @Override
    public String toString() {
        return "Doctor{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", department=" + department +
                ", licenseNumber=" + licenseNumber +
                ", salary=" + salary +
                ", hireDate=" + hireDate +
                ", gender=" + gender +
                ", hospital=" + (hospital != null ? hospital.getHospitalName() : "No Hospital Assigned") +
                ", patients=" + patients.size() + " patients" +
                '}';
    }

    /**
     * Overriding equals method based on licenseNumber.
     *
     * @param o Object to compare
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Doctor)) return false;
        Doctor doctor = (Doctor) o;
        return licenseNumber == doctor.licenseNumber;
    }

    /**
     * Overriding hashCode method based on licenseNumber.
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(licenseNumber);
    }
}