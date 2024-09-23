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

        while (true) {
            printMenu();
            int choice = getUserChoice(0, 4);
            switch (choice) {
                case 0:
                    System.out.println("Exiting the application. See you soon!");
                    System.exit(0);
                case 1:
                    showAllDoctors();
                    break;
                case 3:
                    showAllPatients();
                    break;
                case 4:
                    showPatientsWithFilters();
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    /**
     * method to print out the menu
     */
    private static void printMenu(){
        System.out.println("\nWhat would you like to do?");
        System.out.println("---------------------------");
        System.out.println("0) Quit");
        System.out.println("1) - Show all doctors");
        System.out.println("2) - Show doctors by department");
        System.out.println("3) - Show all patients");
        System.out.println("4) - Show patients with name and/or admission date");
        System.out.println("Choice (0-4): ");
        }
}



