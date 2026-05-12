-- queries.sql  –  MySql(CRUD) Create, Read, Update, Delete

USE hospital_db;

-- 10 Update Statements


-- 1. Doctor - unavailable
UPDATE doctors
SET    available = 0
WHERE  id = 1;

-- 2. Discharge of a patient
UPDATE admissions
SET    discharged_at = '2025-06-01 10:00:00'
WHERE  id = 2
  AND  discharged_at IS NULL;

-- 3. Mark a payment as fully paid
UPDATE payments
SET    paid_amount = total_amount,
       paid        = 1
WHERE  id = 2;

-- 4. Update a patient phone number
UPDATE patients
SET    phone_number = '+995 591 00 11 22'
WHERE  id = 3;

-- 5. Change an appointment status to DONE
UPDATE appointments
SET    status = 'DONE',
       notes  = 'Visit completed. No issues found.'
WHERE  id = 1;

-- 6. Update a room to occupied = false after patient is discharged
UPDATE rooms
SET    occupied = 0
WHERE  id = 1;

-- 7. Add departments rooms capacity
UPDATE rooms
SET    capacity = 4
WHERE  id = 4;

-- 8. Update the notes on a medical record after a new diagnosis
UPDATE medical_records
SET    notes = 'High blood pressure confirmed. Metoprolol prescribed. Follow-up in 3 months.'
WHERE  id = 1;

-- 9. Change dosage of a prescription medication
UPDATE prescription_medications
SET    dosage = '40mg'
WHERE  medication_name = 'Atorvastatin'
  AND  prescriptions_id = 1;

-- 10. Make staff member unavailable maybe they left hospital or smthing
UPDATE staff
SET    active = 0
WHERE  id = 3;



-- 10 Delete Statements


-- 1. Delete a cancelled appointment
DELETE FROM appointments
WHERE  id = 4
  AND  status = 'CANCELLED';

-- 2. Remove a certain allergy from a medical record
DELETE FROM allergies
WHERE  name = 'Pollen'
  AND  medical_records_id = 1;

-- 3. Delete a prescription medication 
DELETE FROM prescription_medications
WHERE  medication_name = 'Prednisolone'
  AND  prescriptions_id = 2;

-- 4. Delete a prescription that has no medications left
DELETE FROM prescriptions
WHERE  id NOT IN (
    SELECT DISTINCT prescriptions_id
    FROM prescription_medications
);

-- 5. Remove a payment record that is not paid as if its not considered as payment
DELETE FROM payments
WHERE  paid = 0
  AND  paid_amount = 0.00
  AND  id = 2;

-- 6. Delete all admissions that were discharged more than a year ago
DELETE FROM payments
WHERE admissions_id IN (
    SELECT id FROM admissions
    WHERE discharged_at IS NOT NULL
      AND discharged_at < NOW() - INTERVAL 1 YEAR
);

DELETE FROM admissions
WHERE discharged_at IS NOT NULL
  AND discharged_at < NOW() - INTERVAL 1 YEAR;

-- 7. Remove an inactive staff member record
DELETE FROM staff
WHERE  active = 0
  AND  id = 3;

-- 8. Delete a room that is no longer in use (not occupied, no admissions)
DELETE FROM rooms
WHERE  occupied = 0
  AND  id NOT IN (
      SELECT DISTINCT rooms_id
      FROM admissions
  )
  AND  id = 4;

-- 9. Delete a department that has no doctors, staff, or rooms assigned
DELETE FROM departments
WHERE id NOT IN (SELECT DISTINCT departments_id FROM doctors)
  AND id NOT IN (SELECT DISTINCT departments_id FROM staff)
  AND id NOT IN (SELECT DISTINCT departments_id FROM rooms);

-- 10. Delete a patient record that has no appointments, admissions, or medical records
--     (if theres any maybe this will be custom test for mistakenly created patients)
DELETE FROM patients
WHERE id NOT IN (SELECT DISTINCT patients_id FROM appointments)
  AND id NOT IN (SELECT DISTINCT patients_id FROM admissions)
  AND id NOT IN (SELECT DISTINCT patients_id FROM medical_records)
  AND id NOT IN (SELECT DISTINCT patients_id FROM prescriptions);


-- ONE BIG JOIN - Hospital Database

SELECT
    -- Medical Clinic
    mc.id AS clinic_id,
    mc.name AS clinic_name,
    mc.address AS clinic_address,
    mc.total_rooms,
    mc.occupied_rooms,

    -- Department
    dep.id AS department_id,
    dep.name AS department_name,
    dep.location AS department_location,

    -- Room
    rm.id AS room_id,
    rm.room_number,
    rm.type AS room_type,
    rm.capacity,
    rm.occupied,

    -- Doctor
    doc.id AS doctor_id,
    doc.first_name AS doctor_first_name,
    doc.last_name AS doctor_last_name,
    doc.specialization,
    doc.available,

    -- Staff
    stf.id AS staff_id,
    stf.first_name AS staff_first_name,
    stf.last_name AS staff_last_name,
    stf.role,
    stf.hire_date,
    stf.active,

    -- Patient
    p.id AS patient_id,
    p.first_name AS patient_first_name,
    p.last_name AS patient_last_name,
    p.date_of_birth,
    p.phone_number,
    p.insured,

    -- Medical Record
    mr.id AS medical_record_id,
    mr.created_date,
    mr.blood_type,
    mr.notes,

    -- Allergy
    al.id AS allergy_id,
    al.name AS allergy_name,

    -- Admission
    adm.id AS admission_id,
    adm.admitted_at,
    adm.discharged_at,
    adm.reason,

    -- Payment
    pay.id AS payment_id,
    pay.issued_date,
    pay.total_amount,
    pay.paid_amount,
    pay.paid,

    -- Appointment
    apt.id AS appointment_id,
    apt.scheduled_at,
    apt.status,
    apt.notes,

    -- Prescription
    pr.id AS prescription_id,
    pr.issued_date,
    pr.instructions,

    -- Medication (prescriped medication)
    prm.id AS medication_id,
    prm.medication_name,
    prm.dosage,
    prm.frequency
	
-- INNER JOIN ("Match rows using a key and glue them together into result rows")

-- LEFT JOIN (“Keep EVERYTHING from the LEFT table (departments), even if no match exists on the right (rooms)” - if no match left collumns stay as is but null is written to write)

-- RIGHT JOIN ( same as left but opposite - "A RIGHT JOIN returns all rows from the right table and only matching rows from the left table; if there’s no match, the left side shows NULLs").

-- UNION ("A UNION combines the results of two or more SELECT queries into a single result set, removing duplicate rows by default."
--  Each SELECT must have the same number of columns
--		Columns must have compatible data types
--			Duplicate rows are removed (by default)

FROM medical_clinics mc
INNER JOIN departments dep
    ON dep.medical_clinics_id = mc.id

LEFT JOIN rooms rm
    ON rm.departments_id = dep.id

LEFT JOIN doctors doc
    ON doc.departments_id = dep.id

LEFT JOIN staff stf
    ON stf.departments_id = dep.id

LEFT JOIN admissions adm
    ON adm.rooms_id = rm.id

LEFT JOIN patients p
    ON p.id = adm.patients_id

LEFT JOIN medical_records mr
    ON mr.patients_id = p.id

LEFT JOIN allergies al
    ON al.medical_records_id = mr.id

LEFT JOIN payments pay
    ON pay.admissions_id = adm.id

LEFT JOIN appointments apt
    ON apt.patients_id = p.id
   AND apt.doctors_id = doc.id

LEFT JOIN prescriptions pr
    ON pr.patients_id = p.id
   AND pr.doctors_id = doc.id

LEFT JOIN prescription_medications prm
    ON prm.prescriptions_id = pr.id

ORDER BY mc.id, dep.id, p.id;



-- JOIN STATEMENTS (INNER, LEFT, RIGHT, FULL)


-- 1. INNER JOIN – patients with at least one appointment

SELECT
    p.id AS patient_id,	
    CONCAT(p.first_name, ' ', p.last_name) AS patient_name,
	
    CONCAT(doc.first_name, ' ', doc.last_name) AS doctor_name,
	
    doc.specialization,
    apt.scheduled_at,
    apt.status
	
FROM patients p
INNER JOIN appointments apt 
    ON apt.patients_id = p.id
INNER JOIN doctors doc 
    ON doc.id = apt.doctors_id
ORDER BY apt.scheduled_at;

-- 2. LEFT JOIN – all patients with admission and without admission

SELECT
    p.id AS patient_id,
    CONCAT(p.first_name, ' ', p.last_name) AS patient_name,
    p.insured,

    adm.id AS admission_id,
    adm.admitted_at,
    adm.reason
	
FROM patients p
LEFT JOIN admissions adm 
    ON adm.patients_id = p.id
	
ORDER BY p.id;


-- 3. RIGHT JOIN – all doctors and with and without their prescriptions 

SELECT
    CONCAT(doc.first_name, ' ', doc.last_name) AS doctor_name,
    doc.specialization,

    pr.id AS prescription_id,
    pr.issued_date,
    pr.instructions
	
FROM prescriptions pr
RIGHT JOIN doctors doc 
    ON doc.id = pr.doctors_id
	
ORDER BY doc.id;


-- 4. LEFT JOIN – all rooms with their admission and payment status

SELECT
    rm.room_number,
    rm.type AS room_type,
    rm.occupied,

    adm.id AS admission_id,
    adm.reason,

    pay.total_amount,
    pay.paid AS is_paid
	
FROM rooms rm
LEFT JOIN admissions adm 
    ON adm.rooms_id = rm.id
LEFT JOIN payments pay 
    ON pay.admissions_id = adm.id
	
ORDER BY rm.id;

-- 5. FULL JOIN - all departments with all staff, showing departments without staff and staff without a department
SELECT
    dep.id AS department_id,
    dep.name AS department_name,

    stf.id AS staff_id,
    CONCAT(stf.first_name, ' ', stf.last_name) AS staff_name,
    stf.role
	
FROM departments dep
LEFT JOIN staff stf 
    ON stf.departments_id = dep.id

UNION

SELECT
    dep.id AS department_id,
    dep.name AS department_name,

    stf.id AS staff_id,
    CONCAT(stf.first_name, ' ', stf.last_name) AS staff_name,
    stf.role
FROM departments dep
RIGHT JOIN staff stf
    ON stf.departments_id = dep.id

ORDER BY department_id;



-- Aggregating queries with GROUP BY (no Having)


-- 1. PATIENT INSURANCE SUMMARY
SELECT insured, COUNT(*) AS total_patients
FROM patients
GROUP BY insured;

-- 2. APPOINTMENT STATUS OVERVIEW
SELECT status, COUNT(*) AS total_appointments
FROM appointments
GROUP BY status;

-- 3. PAYMENT PERFORMANCE SUMMARY
SELECT
 paid,
	COUNT(*) AS total_invoices,
    SUM(total_amount) AS sum_total_amount,
    SUM(paid_amount) AS sum_paid_amount
FROM payments
GROUP BY paid;

-- 4. DOCTORS PER DEPARTMENT
SELECT
    dep.name AS department_name,
    COUNT(doc.id) AS doctor_count
FROM departments dep
LEFT JOIN doctors doc ON doc.departments_id = dep.id
GROUP BY dep.id, dep.name
ORDER BY doctor_count DESC;

-- ROOM CAPACITY ANALYSIS
SELECT
    type AS room_type,
    COUNT(*) AS room_count,
    AVG(capacity) AS avg_capacity,
    SUM(capacity) AS total_capacity
FROM rooms
GROUP BY type;

-- ALLERGY DISTRIBUTION BY BLOOD TYPE
SELECT
    medrec.blood_type,
    COUNT(al.id) AS allergy_count
FROM medical_records medrec
LEFT JOIN allergies al ON al.medical_records_id = medrec.id
GROUP BY medrec.blood_type
ORDER BY allergy_count DESC;

-- PRESCRIPTIONS PER DOCTOR
SELECT
    CONCAT(doc.first_name, ' ', doc.last_name) AS doctor_name,
    doc.specialization,
    COUNT(pr.id) AS prescriptions_issued
FROM doctors doc
LEFT JOIN prescriptions pr ON pr.doctors_id = doc.id
GROUP BY doc.id, doc.first_name, doc.last_name, doc.specialization
ORDER BY prescriptions_issued DESC;



-- Aggregating queries with GROUP BY & HAVING


-- DEPARTMENTS WITH MORE THAN 1 DOCTOR
SELECT
    dep.name AS department_name,
    COUNT(doc.id) AS doctor_count
FROM departments dep
INNER JOIN doctors doc ON doc.departments_id = dep.id
GROUP BY dep.id, dep.name
HAVING COUNT(doc.id) > 1;


-- PATIENTS WITH MORE THAN 1 APPOINTMENT
SELECT
    CONCAT(p.first_name, ' ', p.last_name) AS patient_name,
    COUNT(apt.id) AS appointment_count
FROM patients p
INNER JOIN appointments apt ON apt.patients_id = p.id
GROUP BY p.id, p.first_name, p.last_name
HAVING COUNT(apt.id) > 1;


-- DOCTORS WITH MORE THAN 1 PRESCRIPTION
SELECT
    CONCAT(doc.first_name, ' ', doc.last_name) AS doctor_name,
    COUNT(pr.id) AS prescription_count
FROM doctors doc
INNER JOIN prescriptions pr ON pr.doctors_id = doc.id
GROUP BY doc.id, doc.first_name, doc.last_name
HAVING COUNT(pr.id) > 1;


-- MEDICAL RECORDS WITH 2 OR MORE ALLERGIES
SELECT
    medrec.id AS medical_record_id,
    CONCAT(p.first_name, ' ', p.last_name) AS patient_name,
    medrec.blood_type,
    COUNT(al.id) AS allergy_count
FROM medical_records medrec
INNER JOIN allergies al ON al.medical_records_id = medrec.id
INNER JOIN patients p ON p.id = medrec.patients_id
GROUP BY medrec.id, p.first_name, p.last_name, medrec.blood_type
HAVING COUNT(al.id) >= 2;


-- CLINICS WITH MORE THAN 50% ROOM OCCUPANCY
SELECT
    mc.name AS clinic_name,
    mc.total_rooms,
    mc.occupied_rooms,
    ROUND(mc.occupied_rooms * 100.0 / mc.total_rooms, 1) AS occupancy_pct
FROM medical_clinics mc
GROUP BY mc.id, mc.name, mc.total_rooms, mc.occupied_rooms
HAVING (mc.occupied_rooms * 100.0 / mc.total_rooms) > 50;


-- ADMISSIONS WITH OUTSTANDING BALANCE > 5000
SELECT
    adm.id AS admission_id,
    adm.reason,
    SUM(pay.total_amount) AS total_billed,
    SUM(pay.paid_amount) AS total_paid,
    SUM(pay.total_amount - pay.paid_amount) AS outstanding_balance
FROM admissions adm
INNER JOIN payments pay ON pay.admissions_id = adm.id
GROUP BY adm.id, adm.reason
HAVING SUM(pay.total_amount - pay.paid_amount) > 5000;


-- DEPARTMENTS WITH LOW AVERAGE ROOM CAPACITY (< 3)
SELECT
    d.name AS department_name,
    COUNT(r.id) AS room_count,
    AVG(r.capacity) AS avg_room_capacity
FROM departments d
INNER JOIN rooms r ON r.departments_id = d.id
GROUP BY d.id, d.name
HAVING AVG(r.capacity) < 3;