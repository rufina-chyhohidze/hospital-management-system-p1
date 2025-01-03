# Hospital Management System

### Author: Chyhohidze Rufina (ACS202)

---

## Overview
This project is a **Hospital Management System** that helps manage hospital operations such as 
handling patient records, assigning doctors, and exporting data.
The focus is on providing basic functionality for adding, searching, and managing doctors and patients while maintaining relationships between entities.

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
 - Jdbc: Devided with using Spring JDBC (JdbcTemplates) in combination with a H2DB (in memory).
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

