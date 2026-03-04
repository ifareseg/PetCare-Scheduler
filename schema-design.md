# Smart Clinic Database Schema Design

This document describes the database design for the Smart Clinic Management System using both MySQL (relational database) and MongoDB (NoSQL database).

---

# MySQL Database Design

Relational databases are used for structured data with clear relationships such as patients, doctors, and appointments.

## Table: patients

- id: INT, Primary Key, AUTO_INCREMENT
- first_name: VARCHAR(100), NOT NULL
- last_name: VARCHAR(100), NOT NULL
- email: VARCHAR(150), UNIQUE, NOT NULL
- phone: VARCHAR(20), NOT NULL
- date_of_birth: DATE
- created_at: DATETIME, DEFAULT CURRENT_TIMESTAMP

Notes:
- Each patient must have a unique email.
- Patient information is used for appointments and medical records.

---

## Table: doctors

- id: INT, Primary Key, AUTO_INCREMENT
- first_name: VARCHAR(100), NOT NULL
- last_name: VARCHAR(100), NOT NULL
- specialization: VARCHAR(150), NOT NULL
- email: VARCHAR(150), UNIQUE
- phone: VARCHAR(20)
- created_at: DATETIME, DEFAULT CURRENT_TIMESTAMP

Notes:
- Doctors can have multiple appointments.
- Specialization helps patients search for appropriate doctors.

---

## Table: appointments

- id: INT, Primary Key, AUTO_INCREMENT
- patient_id: INT, Foreign Key → patients(id)
- doctor_id: INT, Foreign Key → doctors(id)
- appointment_time: DATETIME, NOT NULL
- status: INT (0 = Scheduled, 1 = Completed, 2 = Cancelled)
- created_at: DATETIME, DEFAULT CURRENT_TIMESTAMP

Notes:
- A patient can have multiple appointments.
- A doctor can have multiple appointments.
- Appointment time should not overlap for the same doctor.

---

## Table: admin

- id: INT, Primary Key, AUTO_INCREMENT
- username: VARCHAR(100), UNIQUE, NOT NULL
- password: VARCHAR(255), NOT NULL
- email: VARCHAR(150)
- created_at: DATETIME, DEFAULT CURRENT_TIMESTAMP

Notes:
- Admin users manage doctors and system settings.

---

## Table: clinic_locations

- id: INT, Primary Key, AUTO_INCREMENT
- clinic_name: VARCHAR(150), NOT NULL
- address: VARCHAR(255)
- city: VARCHAR(100)
- phone: VARCHAR(20)

Notes:
- Useful for clinics with multiple branches.

---

# MongoDB Collection Design

MongoDB will store flexible or semi-structured data such as prescriptions, doctor notes, or patient feedback.

## Collection: prescriptions

```json
{
  "_id": "ObjectId('64abc123456')",
  "patientId": 101,
  "doctorId": 12,
  "appointmentId": 51,
  "medications": [
    {
      "name": "Paracetamol",
      "dosage": "500mg",
      "instructions": "Take one tablet every 6 hours"
    },
    {
      "name": "Vitamin C",
      "dosage": "1000mg",
      "instructions": "Take once daily"
    }
  ],
  "doctorNotes": "Patient should rest and drink plenty of fluids.",
  "pharmacy": {
    "name": "City Pharmacy",
    "location": "Downtown"
  },
  "createdAt": "2024-01-10T10:30:00Z"
}
