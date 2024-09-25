package be.kdg.programming3;

import be.kdg.programming3.domain.Department;
import be.kdg.programming3.domain.Doctor;
import be.kdg.programming3.domain.Patient;
import be.kdg.programming3.presentation.Menu;
import be.kdg.programming3.repository.DataFactory;
import be.kdg.programming3.repository.DoctorRepository;
import be.kdg.programming3.repository.PatientRepository;
import be.kdg.programming3.service.DoctorService;
import be.kdg.programming3.service.PatientService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.print();  // calls the Menu in presentation method
    }
}




