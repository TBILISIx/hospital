-- DML (Data Manipulation Language) script  for hospital_db

USE hospital_db;

-- Table medical_clinics

INSERT INTO medical_clinics (name, address, total_rooms, occupied_rooms) VALUES
('Tbilisi Medical Center', '42 Rustaveli Ave, Tbilisi', 95, 61),
('Batumi City Clinic', '17 Chavchavadze St, Batumi', 60, 29);

-- Table departments

INSERT INTO departments (name, location, medical_clinics_id) VALUES
('Cardiology', 'Block A, Floor 2', 1),
('Emergency Medicine', 'Block B, Floor 1', 1),
('Orthopedics', 'Block C, Floor 3', 2),
('Neurology', 'Block A, Floor 4', 2);

-- Table rooms

INSERT INTO rooms (room_number, type, capacity, occupied, departments_id) VALUES
('A-201', 'GENERAL', 2, 1, 1),
('A-202', 'PRIVATE', 1, 0, 1),
('B-101', 'ICU', 1, 1, 2),
('B-102', 'RECOVERY', 3, 0, 2),
('C-301', 'SURGERY', 1, 0, 3),
('A-401', 'GENERAL', 2, 1, 4);

-- Table patients

INSERT INTO patients (first_name, last_name, date_of_birth, phone_number, insured) VALUES
('Nino', 'Gelashvili', '1987-04-12', '+995 599 12 34 56', 1),
('Giorgi', 'Makharadze', '1974-09-03', '+995 577 98 76 54', 0),
('Tamar', 'Kvaratskhelia', '1993-02-28', '+995 591 45 67 89', 1),
('Davit', 'Javakhishvili', '1968-11-17', '+995 555 32 10 98', 1);

-- Table medical_records

INSERT INTO medical_records (created_date, blood_type, notes, patients_id) VALUES
('2023-03-15', 'A_POSITIVE', 'History of high blood pressure.', 1),
('2022-07-20', 'O_NEGATIVE', 'No significant past medical history.', 2),
('2024-01-10', 'B_POSITIVE', 'Asthma diagnosed in childhood.', 3),
('2021-06-05', 'AB_NEGATIVE', 'Type 2 diabetes, managed with medication.', 4);

-- Table allergies

INSERT INTO allergies (name, medical_records_id) VALUES
('Penicillin', 1),
('Pollen', 1),
('Latex', 2),
('Ibuprofen', 3),
('Shellfish', 4),
('Sulfonamides', 4);

-- Table doctors

INSERT INTO doctors (first_name, last_name, specialization, available, departments_id) VALUES
('Levan',  'Beridze', 'Cardiologist',    1, 1),
('Mariam', 'Tskhovrebashvili', 'Emergency Physician', 1, 2),
('Zurab',  'Anteladze',  'Orthopedic Surgeon',  0, 3),
('Eka',    'Surmava',   'Neurologist',    1, 4);

-- Table staff

INSERT INTO staff (first_name, last_name, role, hire_date, active, departments_id) VALUES
('Salome', 'Kobakhidze', 'NURSE', '2019-04-08', 1, 1),
('Irakli', 'Gogichaishvili', 'RECEPTIONIST', '2021-02-14', 1, 2),
('Khatuna', 'Maisuradze', 'TECHNICIAN', '2020-07-23', 1, 3),
('Beka', 'Lortkipanidze', 'NURSE', '2018-10-31', 1, 4);

-- Table appointments

INSERT INTO appointments (scheduled_at, status, notes, doctors_id, patients_id) VALUES
('2025-06-10 09:00:00', 'SCHEDULED', 'Routine cardiac check-up.',  1, 1),
('2025-06-11 14:30:00', 'DONE', 'Presented with chest pain.',  2, 2),
('2025-06-12 10:00:00', 'SCHEDULED', 'Follow-up after knee surgery.',  3, 3),
('2025-06-13 11:00:00', 'CANCELLED', 'Patient called to reschedule.',  4, 4);

-- Table admissions

INSERT INTO admissions (admitted_at, discharged_at, reason, rooms_id, patients_id) VALUES
('2025-05-01 08:00:00', '2025-05-05 12:00:00', 'Acute myocardial infarction.',  3, 1),
('2025-05-10 16:00:00', NULL, 'Severe asthma exacerbation.',   1, 3),
('2025-04-20 10:30:00', '2025-04-25 09:00:00', 'Hip fracture following a fall.',  5, 4),
('2025-05-15 22:00:00', NULL,   'Ischemic stroke, under observation.', 6, 2);

-- Table payments

INSERT INTO payments (issued_date, total_amount, paid_amount, paid, admissions_id) VALUES
('2025-05-05', 12500.00, 12500.00, 1, 1),
('2025-05-10',  8750.00, 0.00, 0, 2),
('2025-04-25',  5300.00, 5300.00, 1, 3),
('2025-05-15', 15000.00, 3000.00, 0, 4);

-- Table prescriptions

INSERT INTO prescriptions (issued_date, instructions, doctors_id, patients_id) VALUES
('2025-05-05', 'Take with food. Avoid grapefruit.', 1, 1),
('2025-05-12', 'Use inhaler every 6 hours as needed.', 2, 3),
('2025-04-25', 'Take once daily in the morning.', 3, 4),
('2025-05-16', 'Administer via IV. Strict bed rest.', 4, 2);

-- Table prescription_medications

INSERT INTO prescription_medications (medication_name, dosage, frequency, prescriptions_id) VALUES
('Atorvastatin', '20mg',     'Once daily', 1),
('Metoprolol', '50mg',     'Twice daily', 1),
('Albuterol', '90mcg',    'Every 6 hours', 2),
('Prednisolone', '10mg',     'Once daily', 2),
('Metformin', '500mg',    'Twice daily', 3),
('Aspirin', '81mg',     'Once daily',  3),
('Alteplase', '0.9mg/kg', 'Single dose', 4),
('Heparin', '5000 IU',  'Every 8 hours', 4);
