package be.kdg.programming3;
/**
 * Doctor (Many-to-Many with Patient) A Doctor can have many Patients. Each Patient can have many Doctors assigned.
 * Hospital (One-to-Many with Doctor) A Hospital can employ many Doctors. Each Doctor works in only one Hospital.
 */
public class Patient {
    private String firstName;
    private String lastName;
    private int age;
    private String gender;
    private String patientId;

    public Patient(String firstName, String lastName, int age, String gender, String patientId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.patientId = patientId;
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

    public String getGender() {
        return gender;
    }

    public String getPatientId() {
        return patientId;
    }
}
