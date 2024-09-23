package be.kdg.programming3;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * The console application shows a menu that has at least the following features:
 * an option to close the application
 * an option to show all data of the first entity
 * an option to show all data of the first entity that satisfies 1 mandatory criterium
 * an option to show all data of the second entity
 * an option to show all data of the second entity that satisfies 2 optional criteria
 *  optional means: it can be left empty
 * The criteria should filter on 3 different datatypes. Filtering is not always on an exact equality.
 * The selected data is shown using the toString() methods.
 */

public class StartApplication {
    private static final Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        DataFactory.seed();

        // Display the menu repeatedly until the user chooses to quit
        while (true) {
            printMenu();
            int choice = getUserChoice(0, 4); // Adjusted to 4 based on menu options

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
                default:
                    System.out.println("Invalid choice. Please select again.");
            }
        }
    }

    /**
     * Prints the main menu.
     */
    private static void printMenu() {
        System.out.println("\nWhat would you like to do?");
        System.out.println("==========================");
        System.out.println("0) Quit");
        System.out.println("1) Show all doctors");
        System.out.println("2) Show doctors by department");
        System.out.println("3) Show all patients");
        System.out.println("4) Show patients with name and/or admission date");
        System.out.print("Choice (0-4): ");
    }

    /**
     * Gets the user's menu choice within the specified range.
     *
     * @param min Minimum valid choice
     * @param max Maximum valid choice
     * @return The user's choice as an integer
     */
    private static int getUserChoice(int min, int max) {
        int choice = -1;
        while (true) {
            String input = scanner.nextLine();
            try {
                choice = Integer.parseInt(input);
                if (choice >= min && choice <= max) {
                    break;
                } else {
                    System.out.print("Please enter a valid choice (" + min + "-" + max + "): ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number (" + min + "-" + max + "): ");
            }
        }
        return choice;
    }

    /**
     * Displays all doctors.
     */
    private static void showAllDoctors() {
        System.out.println("\nAll Doctors");
        System.out.println("===========");
        for (Doctor doctor : DataFactory.doctors) {
            System.out.println(doctor);
        }
    }

    /**
     * Displays doctors filtered by department.
     */
    private static void showDoctorsByDepartment() {
        System.out.println("\nDepartments:");
        Department[] departments = Department.values();
        for (int i = 0; i < departments.length; i++) {
            System.out.println((i + 1) + ") " + departments[i].name());
        }
        System.out.print("Select a department by number (1-" + departments.length + "): ");
        int choice = getUserChoice(1, departments.length);
        Department selectedDepartment = departments[choice - 1];

        System.out.println("\nDoctors in " + selectedDepartment.name() + " Department");
        System.out.println("===============================================");
        boolean found = false;
        for (Doctor doctor : DataFactory.doctors) {
            if (doctor.getDepartment() == selectedDepartment) {
                System.out.println(doctor);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No doctors found in this department.");
        }
    }

    /**
     * Displays all patients.
     */
    private static void showAllPatients() {
        System.out.println("\nAll Patients");
        System.out.println("============");
        for (Patient patient : DataFactory.patients) {
            System.out.println(patient);
        }
    }

    /**
     * Displays patients filtered by name and/or admission date.
     */
    private static void showPatientsWithFilters() {
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

        System.out.println("\nFiltered Patients");
        System.out.println("==================");
        boolean found = false;
        for (Patient patient : DataFactory.patients) {
            boolean matchesName = nameInput.isEmpty() || patient.getFirstName().toLowerCase().contains(nameInput) || patient.getLastName().toLowerCase().contains(nameInput);
            boolean matchesDate = (admissionDate == null) || patient.getAdmissionDate().equals(admissionDate);

            if (matchesName && matchesDate) {
                System.out.println(patient);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No patients match the given criteria.");
        }
    }
}




