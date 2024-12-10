package be.kdg.programming3.presentation.console;

import be.kdg.programming3.domain.*;
import be.kdg.programming3.presentation.PatientController;
import be.kdg.programming3.repository.DataFactory;
import be.kdg.programming3.service.DoctorService;
import be.kdg.programming3.service.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

@Component
public class Menu {
    private static final Scanner scanner = new Scanner(System.in);

    private final DoctorService doctorService;
    private final PatientService patientService;
    /*
    * This ensures loose coupling between the layers
    *  as the Menu class only depends on the interfaces
    *  (DoctorService, PatientService), not on the actual implementations.
    *
    * TO USE WITH doctorServiceImplPostgres(h2) remove a qualifier
    * */
    @Autowired
    public Menu(@Qualifier("doctorServiceImplPostgres") DoctorService doctorService,@Qualifier("patientServiceImplPostgres") PatientService patientService) {
        this.doctorService = doctorService;
        this.patientService = patientService;
    }

    public void print() {
        DataFactory.seed();

        while (true) {
            printMenu();

            int choice = getUserChoice(0,7);
            switch (choice) {
                case 0:
                    System.out.println("Exiting the application. Goodbye!");
                    System.exit(0);
                case 1:
                    showAllDoctors();
                    break;
                case 2:
                    showDoctorsByDepartment();
                    break;
                case 3:
                    showAllPatients();
                    break;
                case 4:
                    showPatientsWithFilters();
                    break;
                case 5:addPatient();
                    break;
                case 6:findPatientById();
                    break;
                case 7:addDoctor();
                    break;
                default:
                    System.out.println("Invalid choice. Please select again.");
            }
        }
    }
    /**
     * print the main menu and make a choice!
     */
    private static void printMenu() {
        System.out.println("\nWhat would you like to do?");
        System.out.println("==========================");
        System.out.println("0) Quit");
        System.out.println("1) Show all doctors");
        System.out.println("2) Show doctors by department");
        System.out.println("3) Show all patients");
        System.out.println("4) Show patients with name and/or admission date");
        System.out.println("5) Add a patient");
        System.out.println("6) Find a patient by ID");
        System.out.println("7) Add a doctor");
        System.out.print("Choice (0-7): ");

    }

    /**
     * Gets the user's menu choice within the specified range.
     * @param min Minimum valid choice
     * @param max Maximum valid choice
     * @return The user's choice as an integer
     */
    private static int getUserChoice(int min, int max) {

        int choice = -1;
        while (true) {
            try {
                if(scanner.hasNextInt()) {
                    String input = scanner.nextLine();
                    choice = Integer.parseInt(input);

                    //choice = scanner.nextInt();when i remove two previous lines
                    // and put this one i cant type name for searching, its immid asking for an addmission date

                if (choice >= min && choice <= max) {
                    break;
                } else {
                    System.out.print("Please enter a valid choice (" + min + "-" + max + "): ");
                }
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number (" + min + "-" + max + "): ");
            }
        }
        return choice;
    }
    /*================================================================================================*/
                                    //DOCTORS METHODS

    /**
     *add doctor to a certain department
     */
    public void addDoctor(){
        System.out.println("Enter the name of the doctor to add: ");
        String firstName = scanner.nextLine().trim();
        System.out.println("Enter Doctor's last name: ");
        String lastName = scanner.nextLine();
        int licenseNumber = 0;
        boolean validInput = false;

        while (!validInput) {
            System.out.print("Enter Doctor's license number: ");
            String licenseNumberInput = scanner.nextLine().trim();

            try {
                licenseNumber = Integer.parseInt(licenseNumberInput);
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid numeric license number.");
            }
        }

        // make a choice of departments to add a new doctor to one.
        System.out.println("Available departments: ");
        Department[] departments = Department.values();
        for (int i = 0; i < departments.length; i++) {
            System.out.println((i + 1) + ")" + departments[i].name());
        }
        System.out.println("Select department by number (1-" + departments.length + "): ");
        int departmentChoice = getUserChoice(1, departments.length);
        Department department = departments[departmentChoice - 1];

        double salary =0.0;
        validInput = false;
        while (!validInput) {
            System.out.print("Enter Doctor's salary: ");
            String salaryInput = scanner.nextLine().trim();
            try{
                salary = Double.parseDouble(salaryInput);
                validInput = true;
            }catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid numeric salary.");
            }
        }

        LocalDate hireDate = null;
        validInput = false;
        while (!validInput) {
            System.out.print("Enter Doctor's hire date (YYYY-MM-DD): ");
            String hireDateInput = scanner.nextLine().trim();
            try{
                hireDate = LocalDate.parse(hireDateInput, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                validInput = true;
            }catch (DateTimeParseException e) {
                System.out.println("Invalid input. Please enter a valid date (YYYY-MM-DD): ");
            }
        }
        System.out.println("Available genders: ");
        Gender[] genders = Gender.values(); // Assuming you have an enum for Gender
        for (int i = 0; i < genders.length; i++) {
            System.out.println((i + 1) + ") " + genders[i].name());
        }
        System.out.println("Select gender by number (1-" + genders.length + "): ");
        int genderChoice = getUserChoice(1, genders.length);
        Gender gender = genders[genderChoice - 1];


        Doctor newDoctor = new Doctor(firstName, lastName, department, licenseNumber, salary, hireDate, gender);
        doctorService.addDoctor(newDoctor);

        System.out.println("Doctor: " + firstName + " " + lastName + " with license number: " + licenseNumber + " has been added.");
    }

    /**
     * Displays all doctors.
     */
    private void showAllDoctors() {
        List<Doctor> allDoctors = doctorService.getAllDoctors();

        System.out.println("\nAll Doctors");
        System.out.println("===========");
        if (allDoctors.isEmpty()) {
            System.out.println("No doctors found.");
        } else {
            allDoctors.forEach(System.out::println);
        }
    }

    /**
     * Displays doctors filtered by department.
     */
    private void showDoctorsByDepartment() {
        System.out.println("\nDepartments:");
        Department[] departments = Department.values();
        for (int i = 0; i < departments.length; i++) {
            System.out.println((i + 1) + ") " + departments[i].name());
        }
        System.out.print("Select a department by number (1-" + departments.length + "): ");
        int choice = getUserChoice(1, departments.length);
        Department selectedDepartment = departments[choice - 1];

        List<Doctor> doctorsInDepartment = doctorService.getDoctorsByDepartment(selectedDepartment);

        System.out.println("\nDoctors in " + selectedDepartment.name() + " Department");
        System.out.println("===============================================");

        if (doctorsInDepartment.isEmpty()) {
            System.out.println("No doctors found in this department.");
        } else {
            doctorsInDepartment.forEach(System.out::println);  // Using toString() method from Doctor class
        }
    }

/*====================================================================================================*/
                            //PATIENTS METHODS
    /**
     * Displays all patients.
     */
    private void showAllPatients() {
        System.out.println("\nAll Patients");
        System.out.println("============");
        List<Patient> patients = patientService.getAllPatients();
        if(patients.isEmpty()) {
            System.out.println("No patients found.");
        }else{
            for (Patient patient : patients) {
                System.out.println(patient);
            }
        }
    }

    /**
     * Displays patients filtered by name and/or admission date.
     *  Filtering logic stays in the service layer
     *  Menu class interacts only with the user
     */
    private void showPatientsWithFilters() {
        System.out.print("\nEnter (part of) a name or leave blank: ");
        String nameInput = scanner.nextLine().trim().toLowerCase();

        System.out.print("Enter an admission date (yyyy-MM-dd) or leave blank: ");
        String dateInput = scanner.nextLine().trim();

        LocalDate admissionDate = null;
        if (!dateInput.isEmpty()) {
            try {
                admissionDate = LocalDate.parse(dateInput, DateTimeFormatter.ISO_LOCAL_DATE);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd.");
                return;
            }
        }
        List<Patient> filteredPatients = patientService.getPatientsByNameOrAdmissionDate(nameInput,admissionDate);


        System.out.println("\nFiltered Patients");
        System.out.println("==================");
        if (filteredPatients.isEmpty()) {
            System.out.println("No patients match the given criteria.");
        } else {
            // Print filtered patient information
            filteredPatients.forEach(patient -> {
                System.out.println(patient);
            });
        }
    }
    /**
     * method to add a patient
     */
    private void addPatient() {
        System.out.println("Enter patient's first name: ");
        String firstName = scanner.nextLine();

        System.out.println("Enter patient's last name: ");
        String lastName = scanner.nextLine();

        System.out.println("Enter patient's age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        System.out.println("Enter patient's gender (MALE/FEMALE): ");
        Gender gender = Gender.valueOf(scanner.nextLine().toUpperCase());

        System.out.println("Enter patient's ID: ");
        String patientId = scanner.nextLine();

        System.out.println("Enter patient's billing amount: ");
        double billingAmount = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline character

        System.out.println("Enter patient's admission date (yyyy-MM-dd): ");
        String dateInput = scanner.nextLine();
        LocalDate admissionDate;
        try {
            admissionDate = LocalDate.parse(dateInput, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use yyyy-MM-dd.");
            return;
        }

        // Create a new Patient object using the correct constructor
        Hospital hospital = new Hospital();
        Patient newPatient = new Patient(firstName, lastName, age, gender, patientId, billingAmount, admissionDate,hospital);
        patientService.addPatient(newPatient);
        System.out.println("Patient added successfully!");
    }

    /**
     * search a patient by ID
     */
    private void findPatientById(){
        System.out.println("Enter patient's ID: ");
        String patientId = scanner.nextLine().trim();

        //making call to a service to find the patient by ID
        Patient patient = patientService.findPatientById(patientId);

        if(patient == null) {
            System.out.println("Patient with ID: " +patientId + " not found.");
        }else {
            System.out.println(patient);
        }
    }
}
