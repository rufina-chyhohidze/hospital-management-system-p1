package be.kdg.programming3;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Doctor (Many-to-Many with Patient) A Doctor can have many Patients. Each Patient can have many Doctors assigned.
 * Hospital (One-to-Many with Doctor) A Hospital can employ many Doctors. Each Doctor works in only one Hospital.
 */

public class Hospital {
    private String hospitalName;
    private String hospitalAddress;
    private String[] departments;
    private LocalDate establishedDate;

    public Hospital(String hospitalName, String hospitalAddress, String[] departments, LocalDate establishedDate) {
        this.hospitalName = hospitalName;
        this.hospitalAddress = hospitalAddress;
        this.departments = departments;
        this.establishedDate = establishedDate;
    }

    public LocalDate getEstablishedDate() {
        return establishedDate;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public String getHospitalAddress() {
        return hospitalAddress;
    }

    public String[] getDepartments() {
        return departments;
    }

    /**
     * each doctor belongs to one department within that hospital.
     */

    //hospital ->many doctors
    public List<Doctor> doctors;

    public Hospital(){
        this.doctors = new ArrayList<>();
    }

    /**
     * method to add a doctor to the hospital
     * @param doctor
     */
    public void addDoctor(Doctor doctor){
        if(!doctors.contains(doctor)){
            doctors.add(doctor);
        }
    }

    /**
     * method to remove a doctor from the hospital
     * @param doctor
     */
    public void removeDoctor(Doctor doctor){
        doctors.remove(doctor);
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }
    @Override
    public String toString() {
        return  "Hospital{" +
                "name='" + hospitalName + '\'' +
                ", address='" + hospitalAddress + '\'' +
                ", departments=" + String.join(", ", departments) +
                ", established=" + establishedDate +
                ", doctors=" + doctors.size() + " doctors" +
                '}';
    }
}

