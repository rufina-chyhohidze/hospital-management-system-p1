INSERT INTO patients (patient_id, first_name, last_name, age, gender, admission_date, billing_amount)
VALUES
    ('P0001', 'John', 'Doe', 35, 'MALE', '2024-11-01', 500.00),
    ('P0002', 'Jane', 'Smith', 29, 'FEMALE', '2024-11-02', 700.00);

INSERT INTO doctors (license_number, first_name, last_name, department, salary, hire_date, gender, hospital_name)
VALUES
    (1001, 'Alice', 'Johnson', 'CARDIOLOGY', 120000.00, '2022-05-10', 'FEMALE', 'Central Hospital'),
    (1002, 'Bob', 'Brown', 'NEUROLOGY', 130000.00, '2021-06-15', 'MALE', 'City Hospital');