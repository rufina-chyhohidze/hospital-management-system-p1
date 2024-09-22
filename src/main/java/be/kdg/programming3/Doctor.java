package be.kdg.programming3;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Doctor (Many-to-Many with Patient) A Doctor can have many Patients. Each Patient can have many Doctors assigned.
 * Hospital (One-to-Many with Doctor) A Hospital can employ many Doctors. Each Doctor works in only one Hospital.
 */

public class Doctor {
    private String firstName;
    private String lastName;
    private Department department; //using enum
    private int licenseNumber;
    private double salary;
    private LocalDate hireDate;

    public Doctor(String firstName, String lastName, Department department, int licenseNumber,double salary, LocalDate hireDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.licenseNumber = licenseNumber;
        this.salary = salary;
        this.hireDate = hireDate;
        this.patients = new HashSet<>();
    }
    public LocalDate getHireDate() {
        return hireDate;
    }

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

    public void setDepartment(Department department) {
        this.department = department;
    }

    public double getSalary() {
        return salary;
    }

    //many doctors <-> many patients
    private Set<Patient> patients;

    public Doctor(){
        this.patients = new HashSet<>();
    }

    /**
     * method to add a patient to the doctor's list
     * @param patient
     */
    public void addPatient(Patient patient){
        if(!patients.contains(patient)){
            patients.add(patient);
            patient.addDoctor(this); //bidirectional addition
        }
    }

    /**
     * method to remove a patient from the list
     * @param patient
     */
    public void removePatient(Patient patient){
      if(patients.contains(patient)){
          patients.remove(patient);
          patient.removeDoctor(this); ////bidirectional removal
      }
    }

    public Set<Patient> getPatients() {
        return patients;
    }
    @Override
    public String toString() {
       return  "Doctor{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", department=" + department +
                ", licenseNumber=" + licenseNumber +
                ", salary=" + salary +
                ", hireDate=" + hireDate +
                ", patients=" + patients.size() + " patients" +
                '}';
    }
}

