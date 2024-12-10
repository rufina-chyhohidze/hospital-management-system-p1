/*DROP TABLE IF EXISTS PATIENTS CASCADE ;
DROP TABLE IF EXISTS DOCTORS CASCADE ;


CREATE TABLE PATIENTS (
                          patient_id VARCHAR(50) PRIMARY KEY,
                          first_name VARCHAR(50),
                          last_name VARCHAR(50),
                          age INT,
                          gender VARCHAR(10),
                          admission_date DATE,
                          billing_amount DOUBLE
);


CREATE TABLE DOCTORS (
                         license_number INT PRIMARY KEY,
                         first_name VARCHAR(50),
                         last_name VARCHAR(50),
                         department VARCHAR(50),
                         salary DOUBLE,
                         hire_date DATE,
                         gender    VARCHAR(10)
);

DROP TABLE IF EXISTS doctor_patient CASCADE;

CREATE TABLE doctor_patient (
                                doctor_id INT,
                                patient_id VARCHAR(50),
                                PRIMARY KEY (doctor_id, patient_id),
                                FOREIGN KEY (doctor_id) REFERENCES doctors(license_number) ON DELETE CASCADE,
                                FOREIGN KEY (patient_id) REFERENCES patients(patient_id) ON DELETE CASCADE
);

 */
