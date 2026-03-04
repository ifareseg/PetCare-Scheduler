# User Story Template

**Title:** Admin Login

_As an admin, I want to log into the portal using my username and password so that I can securely manage the platform._

**Acceptance Criteria:**
1. Admin enters username and password.
2. System validates credentials.
3. Admin is redirected to the admin dashboard.

**Priority:** High  
**Story Points:** 3  

**Notes:**
- Use secure authentication.


---

**Title:** Admin Logout

_As an admin, I want to log out of the portal so that I can protect system access._

**Acceptance Criteria:**
1. Admin clicks logout.
2. Session is terminated.
3. Admin is redirected to login page.

**Priority:** Medium  
**Story Points:** 2  

**Notes:**
- Ensure session security.


---

**Title:** Add Doctor Profile

_As an admin, I want to add doctors to the portal so that patients can book appointments._

**Acceptance Criteria:**
1. Admin enters doctor details.
2. Doctor information is stored in the database.
3. Doctor appears in the doctor list.

**Priority:** High  
**Story Points:** 4  

**Notes:**
- Validate required fields.


---

**Title:** Delete Doctor Profile

_As an admin, I want to delete a doctor profile from the portal so that outdated profiles are removed._

**Acceptance Criteria:**
1. Admin selects a doctor profile.
2. Admin confirms deletion.
3. Doctor profile is removed from the system.

**Priority:** Medium  
**Story Points:** 3  

**Notes:**
- Prevent accidental deletion.


---

**Title:** Generate Monthly Appointment Report

_As an admin, I want to run a stored procedure to view the number of appointments per month so that I can track system usage._

**Acceptance Criteria:**
1. Admin runs stored procedure in MySQL.
2. System returns monthly appointment count.
3. Results are displayed in the admin dashboard.

**Priority:** Medium  
**Story Points:** 4  

**Notes:**
- Ensure accurate database reporting.



# Patient User Stories

**Title:** View Doctors Without Login

_As a patient, I want to view the list of doctors without logging in so that I can explore options._

**Acceptance Criteria:**
1. Patient opens the portal.
2. System displays available doctors.
3. No login required.

**Priority:** Medium  
**Story Points:** 2  

**Notes:**
- Display doctor specialization.


---

**Title:** Patient Sign Up

_As a patient, I want to sign up using my email and password so that I can book appointments._

**Acceptance Criteria:**
1. Patient enters email and password.
2. System validates input.
3. Account is created successfully.

**Priority:** High  
**Story Points:** 4  

**Notes:**
- Email must be unique.


---

**Title:** Patient Login

_As a patient, I want to log into the portal so that I can manage my bookings._

**Acceptance Criteria:**
1. Patient enters login credentials.
2. System verifies credentials.
3. Patient dashboard opens.

**Priority:** High  
**Story Points:** 3  

**Notes:**
- Use secure authentication.


---

**Title:** Book Appointment

_As a patient, I want to book an appointment with a doctor so that I can receive medical consultation._

**Acceptance Criteria:**
1. Patient selects doctor.
2. Patient chooses available time slot.
3. Appointment is confirmed.

**Priority:** High  
**Story Points:** 5  

**Notes:**
- Prevent double booking.


---

**Title:** View Upcoming Appointments

_As a patient, I want to view my upcoming appointments so that I can prepare accordingly._

**Acceptance Criteria:**
1. Patient logs into the portal.
2. System displays upcoming appointments.
3. Appointment details are visible.

**Priority:** Medium  
**Story Points:** 3  

**Notes:**
- Include date and doctor details.



# Doctor User Stories

**Title:** Doctor Login

_As a doctor, I want to log into the portal so that I can manage my appointments._

**Acceptance Criteria:**
1. Doctor enters login credentials.
2. System validates credentials.
3. Doctor dashboard opens.

**Priority:** High  
**Story Points:** 3  

**Notes:**
- Secure authentication required.


---

**Title:** Doctor Logout

_As a doctor, I want to log out of the portal so that my account remains secure._

**Acceptance Criteria:**
1. Doctor clicks logout.
2. Session is terminated.
3. Doctor is redirected to login page.

**Priority:** Medium  
**Story Points:** 2  

**Notes:**
- Protect session data.


---

**Title:** View Appointment Calendar

_As a doctor, I want to view my appointment calendar so that I can stay organized._

**Acceptance Criteria:**
1. Doctor logs into dashboard.
2. System displays calendar view.
3. Upcoming appointments are visible.

**Priority:** High  
**Story Points:** 4  

**Notes:**
- Calendar should update dynamically.


---

**Title:** Mark Unavailability

_As a doctor, I want to mark my unavailable time slots so that patients only book available times._

**Acceptance Criteria:**
1. Doctor selects unavailable time.
2. System blocks that slot.
3. Patients cannot book during that time.

**Priority:** Medium  
**Story Points:** 3  

**Notes:**
- Avoid scheduling conflicts.


---

**Title:** Update Doctor Profile

_As a doctor, I want to update my specialization and contact information so that patients have up-to-date details._

**Acceptance Criteria:**
1. Doctor edits profile information.
2. Changes are saved in the database.
3. Updated information is visible to patients.

**Priority:** Medium  
**Story Points:** 3  

**Notes:**
- Validate input fields.