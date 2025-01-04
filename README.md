# Hospital Management System

### Author: Chyhohidze Rufina (ACS202)

---

## Overview
This project is a **Hospital Management System** that helps manage hospital operations such as 
handling patient records, assigning doctors, and exporting data.
The focus is on providing basic functionality for adding, searching, and managing doctors and patients while maintaining relationships between entities.

### Project implementation review:
- Implemented 3 entities (Patient, Doctor, Hospital) with its attributes, toString + DataFactory to fill up the lists of the entities.
- The very first version of the application shows the menu in the console, where you can operate.
- The application follows 3-layered architecture which are: Presentation layer(contains the view code and separated from the view logic), Business layer(services + domain),Data access layer(contains the data classes(repositories)).
- The application uses loose coupling between the layers by having interfaces and uses dependency injection to connect the different classes.
- The web application has 6 pages, which are (Home page, Patients, Doctors, Add Doctor, Add Patient, Check session history) each of them has its own role and functionality.
- Logger is being used in all classes that are providing meaningful log message to console.
- Bootstrap styling, fragments for navbar and footer, client-side validation, support of French language is being applied.
- ViewModel for DoctorForm and PatientForm, converter for enums, session history is being shown on the separate page.
- Implemented the Repository of 2 main entities using Spring JDBC (JdbcTemplates) in combination with a H2DB (in memory) which uses schema.sql and data.sql to load initial data.
- Uses profiles(4 implementations) to be able to switch between the old implementation (using Java Collections) and the new implementations (using JDBC,JPA).
- Application(is CRUD), meaning you can perform Creation of Doctors and Patients, Read available data, Update and Delete functionality.
- method queries to main entity’s repository, custom query method (using @Query annotation).
- Two custom error pages(DoctorNotFoundException, PatientNotFoundException) which are being used at the Controller level.
- 2 buttons to export entities on patients and doctors page in Json format, they're being saved in root directory in patients.json and doctors, json files and are available for save and viewing.
- - - Seamless and easy user experience by using Hospital Management System is being guaranteed!

### Not implemented, postponed for later release:
- picture uploading for a doctor's and patient's form, and showing it in doctorDetails and patientDetails page.
---

## Domain Explanation

### Entities
1. **Hospital**  
   Represents the hospital with attributes like:
    - Name
    - Address
    - Departments

2. **Doctor**  
   Doctors work at a hospital and can treat multiple patients. Key attributes:
    - First Name & Last Name
    - License Number (Unique Identifier)
    - Department (Specialization)
    - Salary and Hire Date
    - Associated Hospital and Patients

3. **Patient**  
   Patients receive treatment from one or more doctors. Attributes include:
    - Name (First & Last)
    - Patient ID (Unique Identifier)
    - Age & Gender
    - Admission Date
    - Billing Amount

### Relationships
- A **Hospital** can have multiple doctors (one-to-many).
- A **Doctor** can treat many patients, and a **Patient** can have multiple doctors (many-to-many).
---

## Profiles
 - 0ld : The very first implementation with Java Collections so no data being stored in database.
 - H2  : Using Spring JDBC (JdbcTemplates) in combination with a H2DB (in memory).
 - Jdbc: Divided with using Spring JDBC (JdbcTemplates) in combination with a H2DB (in memory).
 - Post: Post is divided on 2, first part is database connection(application.properties to choose a
   database)
   #2nd part - entity , we use it in services and repositories (we need it for use a concrete implementation with
   #entity manager(jpa v1), uses postgres database.
 - Jpa: uses Jpa and postgres database. For JpaData Repository it's uses this profile + Entity.
---

## Database Configuration(h2)
- **Database**: H2
- **Database** URL: jdbc:h2:file:./db/testdb
- **Username**: sa
- **Password**: password 

## Postgres 
- **Database URL**: jdbc:postgresql://localhost:5432/postgres
- **Username**: postgres
- **Password**: Student_1234

## Export of two main entities in Json format (patients and doctors)
 - export is being saved in the root directory and has a names patients.json and doctors.json

### Setting Up
1. Create H2 database using this URL: jdbc:h2:file:./db/testdb OR postgres database with this URL:jdbc:postgresql://localhost:5432/postgres
2. Update `application.properties` in the project if your database credentials differ.
3. The application will handle table creation automatically on the first run.

---

## How to Run
### Prerequisites
- JDK 17 or higher
- Postgres database, H2 database 
- Maven or Gradle installed

### Steps
1. Clone the repository:
   ```bash
   git clone https://gitlab.com/RufinaChyhohidze/individual_project_programming3_chyhohidze_rufina.git
Open the project in your favorite IDE (e.g., IntelliJ IDEA, Eclipse).
Configure your database settings in application.properties.
Run the main class:
IndividualProjectProgramming3ChyhohidzeRufinaApplication
Access the application in your browser at:
http://localhost:8080


