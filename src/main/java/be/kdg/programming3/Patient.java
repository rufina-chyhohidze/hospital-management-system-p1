package be.kdg.programming3;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Patient (Many-to-Many with Doctor)
 * A Patient can have many Doctors assigned. Each Doctor can have many Patients.
 */
public class Patient {
    private String firstName;
    private String lastName;
    private int age;
    private Gender gender;
    private String patientId;
    private double billingAmount;
    private LocalDate admissionDate;
    private Set<Doctor> doctors; //relationship link to doctors that shows many to many r.

    public Patient(String firstName, String lastName, int age, Gender gender, String patientId, double billingAmount, LocalDate admissionDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.patientId = patientId;
        this.billingAmount = billingAmount;
        this.admissionDate = admissionDate;
        this.doctors = new HashSet<>();
    }

    /**
     * Default Constructor
     */
    public Patient() {
        this.doctors = new HashSet<>();
    }

    // Getters and Setters
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPatientId() {
        return patientId;
    }

    public double getBillingAmount() {
        return billingAmount;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public LocalDate getAdmissionDate() {
        return admissionDate;
    }

    public Set<Doctor> getDoctors() {
        return new HashSet<>(doctors); // Return a copy to maintain encapsulation
    }

    // Many-to-Many Relationship Methods

    /**
     * Method that adds a doctor to a patient's list ensuring bidirectional relationship
     * @param doctor The Doctor to be added
     */
    public void addDoctor(Doctor doctor){
        if(doctor == null){
            throw new IllegalArgumentException("Doctor cannot be null.");
        }
        if (!doctors.contains(doctor)){
            doctors.add(doctor);
            doctor.addPatient(this); // Bidirectional addition
        }
    }

    /**
     * Method to remove a doctor from a patient's list
     * @param doctor The Doctor to be removed
     */
    public void removeDoctor(Doctor doctor){
        if(doctors.contains(doctor)){
            doctors.remove(doctor);
            doctor.removePatient(this); // Bidirectional removal
        }
    }

    // toString() Method
    @Override
    public String toString() {
        return "Patient{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                ", patientId='" + patientId + '\'' +
                ", billingAmount=" + billingAmount +
                ", admissionDate=" + admissionDate +
                ", doctors=" + doctors.size() + " doctors" +
                '}';
    }

    /**
     * Overriding equals method based on patientId.
     *
     * @param o Object to compare
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Patient)) return false;
        Patient patient = (Patient) o;
        return Objects.equals(patientId, patient.patientId);
    }

    /**
     * Overriding hashCode method based on patientId.
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(patientId);
    }
}