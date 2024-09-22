package be.kdg.programming3;
/**
 * Doctor (Many-to-Many with Patient) A Doctor can have many Patients. Each Patient can have many Doctors assigned.
 * Hospital (One-to-Many with Doctor) A Hospital can employ many Doctors. Each Doctor works in only one Hospital.
 */

public class Doctor {
    private String firstName;
    private String lastName;
    private String department;
    private int licenseNumber;

    public Doctor(String firstName, String lastName, String department, int licenseNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.licenseNumber = licenseNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDepartment() {
        return department;
    }

    public int getLicenseNumber() {
        return licenseNumber;
    }
}
