package be.kdg.programming3;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * Doctor (Many-to-Many with Patient) A Doctor can have many Patients. Each Patient can have many Doctors assigned.
 * Hospital (One-to-Many with Doctor) A Hospital can employ many Doctors. Each Doctor works in only one Hospital.
 */
public class Patient {
    private String firstName;
    private String lastName;
    private int age;
    private Gender gender;
    private String patientId;
    private double billingAmount;
    private LocalDate admissionDate;


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

    public double getBillingAmount() {
        return billingAmount;
    }

    public LocalDate getAdmissionDate() {
        return admissionDate;
    }

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

     // doctors <-> patients
    private Set<Doctor> doctors;

    public Patient(){
        this.doctors = new HashSet<>();
    }

    /**
     * method that adda doctor to a patient's list ensuring bidirectional relationship
     * b.relationship - refers to a two-way association between two classes, where each class maintains a reference to the other.
     * @param doctor
     */
    public void addDoctor(Doctor doctor){
        if (!doctors.contains(doctor)){
            doctors.add(doctor);
            doctor.addPatient(this);
        }
    }

    /**
     * method to remove a doctor from a patient's list
     * @param doctor
     */
    public void removeDoctor(Doctor doctor){
        if(doctors.contains(doctor)){
            doctors.remove(doctor);
            doctor.removePatient(this);
        }
    }

    public Set<Doctor> getDoctors() {
        return doctors;
    }

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
}
