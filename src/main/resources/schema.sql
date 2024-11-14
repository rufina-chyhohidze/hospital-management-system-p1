DROP TABLE IF EXISTS PATIENTS;
DROP TABLE IF EXISTS DOCTORS;

DROP TABLE IF EXISTS PATIENTS;
CREATE TABLE PATIENTS (
                          patient_id VARCHAR(50) PRIMARY KEY,
                          first_name VARCHAR(50),
                          last_name VARCHAR(50),
                          age INT,
                          gender VARCHAR(10),
                          admission_date DATE,
                          billing_amount DOUBLE
);

DROP TABLE IF EXISTS DOCTORS;
CREATE TABLE DOCTORS (
                         license_number INT PRIMARY KEY,
                         first_name VARCHAR(50),
                         last_name VARCHAR(50),
                         department VARCHAR(50),
                         salary DOUBLE,
                         hire_date DATE,
                         gender VARCHAR(10),
                         hospital_name VARCHAR2(100)

);